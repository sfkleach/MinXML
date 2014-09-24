/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
package com.steelypip.powerups.minxml;

import java.io.Reader;
import java.util.Iterator;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;

public class MinXMLParser implements Iterable< MinXML > {
	
	private int level = 0;
	private final CharRepeater cucharin;
	
	private boolean pending_end_tag = false;
	private MinXMLBuilder parent = null;
	private String tag_name = null;	
	
	public MinXMLParser( CharRepeater rep, MinXMLBuilder parent ) {
		this.parent = parent;
		this.cucharin = rep;
	}

	public MinXMLParser( Reader rep, MinXMLBuilder parent ) {
		this.parent = parent;
		this.cucharin = new ReaderCharRepeater( rep );
	}

	public MinXMLParser( final Reader rep ) {
		this.parent = new FlexiMinXMLBuilder();
		this.cucharin = new ReaderCharRepeater( rep );
	}

	private char nextChar() {
		return this.cucharin.nextChar();
	}
		
	private char peekChar() {
		return this.cucharin.peekChar();
	}
	
	private boolean hasNext() {
		this.eatWhiteSpace();
		return this.cucharin.peekChar( '\0' ) == '<';
	}
	
	private void mustReadChar( final char ch_want ) {
		if ( this.cucharin.isNextChar( ch_want ) ) {
			this.cucharin.skipChar();
		} else {
			if ( this.cucharin.hasNextChar() ) {
				throw new Alert( "Unexpected character" ).culprit( "Wanted", "" + ch_want ).culprit( "Received", "" + this.cucharin.peekChar() );
			} else {
				throw new Alert( "Unexpected end of stream" );
			}			
		}
	}
	
	private void eatUpTo( final char stop_char ) {
		final char not_stop_char = ( stop_char != '\0' ? '\0' : '_' );
		while ( this.cucharin.nextChar( not_stop_char ) != '>' ) {
		}		
	}
	
	private void eatComment() {
		while ( this.nextChar() != '>' ) {
		}
	}
	
	private void eatWhiteSpace() {
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( ch == '#' && this.level == 0 ) {
				//	EOL comment.
				this.eatUpTo( '\n' );
			} else if ( ! Character.isWhitespace( ch ) ) {
				this.cucharin.pushChar( ch );
				return;
			}
		}
	}
	
	private static boolean is_name_char( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	private String readName() {
		final StringBuilder name = new StringBuilder();
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( is_name_char( ch ) ) {
				name.append( ch );
			} else {
				this.cucharin.pushChar( ch );
				break;
			}
		}
		return name.toString();
	}
	
	private String readEscapeContent() {
		final StringBuilder esc = new StringBuilder();
		for (;;) {
			final char ch = this.nextChar();
			if ( ch == ';' ) break;
			esc.append( ch );
			if ( esc.length() > 4 ) {
				throw new Alert( "Malformed escape" ).culprit( "Sequence", esc );
			}
		}
		return esc.toString();
	}
	
	private char entityLookup( final String symbol ) {
		if ( "lt".equals( symbol ) ) {
			return '<';
		} else if ( "gt".equals( symbol ) ) {
			return '>';
		} else if ( "amp".equals(  symbol  ) ) {
			return '&';
		} else if ( "quot".equals( symbol ) ) {
			return '"';
		} else if ( "apos".equals(  symbol ) ) {
			return '\'';
		} else {
			throw new Alert( "Unexpected escape sequence after &" ).culprit( "Sequence", symbol );
		}		
	}
	
	private char readEscape() {
		final String esc = this.readEscapeContent();
		if ( esc.length() >= 2 && esc.charAt( 0 ) == '#' ) {
			try {
				final int n = Integer.parseInt( esc.toString().substring( 1 ) );
				return (char)n;
			} catch ( NumberFormatException e ) {
				throw new Alert( "Unexpected numeric sequence after &#", e ).culprit( "Sequence", esc );
			}
		} else {
			return this.entityLookup( esc );
		}
		
	}
	
	private String readAttributeValue() {
		final StringBuilder attr = new StringBuilder();
		final char q = this.nextChar();
		if ( q != '"' && q != '\'' ) throw new Alert( "Attribute value not quoted" ).culprit( "Character", q );
		for (;;) {
			char ch = this.nextChar();
			if ( ch == q ) break;
			if ( ch == '&' ) {
				attr.append( this.readEscape() );
			} else {
				attr.append( ch );
			}
		}
		return attr.toString();
	}	
	
	
	private void processAttributes() {
		for (;;) {
			this.eatWhiteSpace();
			char c = peekChar();
			if ( c == '/' || c == '>' ) break;
			final String key = this.readName();
			
			this.eatWhiteSpace();
			this.mustReadChar( '=' );
			this.eatWhiteSpace();
			final String value = this.readAttributeValue();
			this.parent.put( key, value );
		}
	}
	

	
	private void read() {
		
		if ( this.pending_end_tag ) {
			this.parent.endTag( this.tag_name );
			this.pending_end_tag = false;
			this.level -= 1;
			return;
		}
			
		this.eatWhiteSpace();
		
		if ( !this.cucharin.hasNextChar() ) {
			return;
		}
		
		this.mustReadChar( '<' );
			
		char ch = this.nextChar();
		
		if ( ch == '/' ) {
			final String end_tag = this.readName();
			this.eatWhiteSpace();
			this.mustReadChar( '>' );
			this.parent.endTag( end_tag );
			this.level -= 1;
			return;
		} else if ( ch == '!' || ch == '?' ) {
			this.eatComment();
			this.read();
			return;
		} else {
			this.cucharin.pushChar( ch );
		}
		
		this.tag_name = this.readName();
		
		this.parent.startTagOpen( this.tag_name );
		this.processAttributes();
		this.parent.startTagClose( this.tag_name );
		
		this.eatWhiteSpace();
				
		ch = nextChar();
		if ( ch == '/' ) {
			this.mustReadChar( '>' );
			this.pending_end_tag = true;
			this.level += 1;
			return;
		} else if ( ch == '>' ) {
			this.level += 1;
			return;
		} else {
			throw new Alert( "Invalid continuation" );
		}
				
	}
	
	public MinXML readElement() { 
		for (;;) {
			this.read();
			if ( this.level == 0 ) break;
		}
		return parent.build();
	}
	
	public Iterator< MinXML > iterator() {
		return new Iterator< MinXML >() {

			@Override
			public boolean hasNext() {
				return MinXMLParser.this.hasNext();
			}

			@Override
			public MinXML next() {
				return MinXMLParser.this.readElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}

}