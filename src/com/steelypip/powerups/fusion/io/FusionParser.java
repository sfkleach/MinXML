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
package com.steelypip.powerups.fusion.io;

import java.io.Reader;
import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;
import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.FusionBuilder;
import com.steelypip.powerups.minxson.Lookup;

/**
 * This class wraps various types of input stream to
 * create a MinXML parser. An optional MinXMLBuilder can
 * be supplied, which gives control over the MinXML
 * implementation that is actually constructed.
 * 
 * MinXML elements are read off the stream using
 * readElement. Alternatively you can simply iterate 
 * over the parser.
 */
public class FusionParser extends LevelTracker implements Iterable< Fusion > {
	
	private final CharRepeater cucharin;
	
	private boolean pending_end_tag = false;
	private FusionBuilder builder = null;
	private String tag_name = null;	
	
	public FusionParser( CharRepeater rep, FusionBuilder builder ) {
		this.builder = builder;
		this.cucharin = rep;
	}

	/**
	 * Constructs a parser from a {@link java.io.Reader} and a
	 * MinXMLBuilder. 
	 * @param reader the input source
	 * @param builder used to construct the MinXML objects
	 */
	public FusionParser( Reader reader, FusionBuilder builder ) {
		this.builder = builder;
		this.cucharin = new ReaderCharRepeater( reader );
	}

	public FusionParser( final Reader rep ) {
		this.builder = new JSONFusionBuilder();
		this.cucharin = new ReaderCharRepeater( rep );
	}

	private char nextChar() {
		return this.cucharin.nextChar();
	}
	
	private boolean tryReadChar( final char ch_want ) {
		final boolean read = this.cucharin.isNextChar( ch_want );		
		if ( read ) {
			this.cucharin.skipChar();
		}
		return read;
	}
		
	private char peekChar() {
		return this.cucharin.peekChar();
	}
	
	private char peekChar( char ch ) {
		return this.cucharin.peekChar( ch );
	}
	
	/**
	 * Reads ahead on the input stream to determine if there are more 
	 * MinXML expressions.
	 * @return true if there are, else false
	 */
	public boolean hasNext() {
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
		while ( this.cucharin.nextChar( not_stop_char ) != stop_char ) {
		}		
	}
	
	private void eatComment( final char ch ) {
		if ( ch == '!' ) {
			if ( this.cucharin.isNextChar( '-' ) ) {
				this.cucharin.skipChar();
				this.mustReadChar( '-' );
				int count_minuses = 0;
				for (;;) {
					final char nch = this.nextChar();
					if ( nch == '-' ) {
						count_minuses += 1;
					} else if ( nch == '>' && count_minuses >= 2 ) {
						break;
					} else {
						if ( count_minuses >= 2 ) {
							throw new Alert( "Invalid XML comment" ).hint( "Detected -- within the body of comment" ).culprit( "Character following --", (int)nch );
						}
						count_minuses = 0;
					}
				}
			} else {
				for (;;) {
					final char nch = this.nextChar();
					if ( nch == '>' ) break;
					if ( nch == '<' ) this.eatComment( this.nextChar() );
				}
			}
		} else {
			this.eatUpTo( '>' );
		}
	}
	
	private void eatWhiteSpace() {
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( ! Character.isWhitespace( ch ) ) {
				this.cucharin.pushChar( ch );
				return;
			}
		}
	}
	
	private void eatWhiteSpaceIncludingOneComma() {
		boolean seen_comma = false;
		while ( this.cucharin.hasNextChar() ) {
			final char ch = this.cucharin.nextChar();
			if ( Character.isWhitespace( ch ) ) {
				//	Continue.
			} else if ( ! seen_comma && ch == ',' ) {
				seen_comma = true;
			} else {
				this.cucharin.pushChar( ch );
				return;
			}
		}
	}
	
	private static boolean is_name_char( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '-' || ch == '.';
	}
	
	@SuppressWarnings("null")
	private @NonNull String readName() {
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
		Character c = Lookup.lookup( symbol );
		if ( c != null ) {
			return c;
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
	
	@SuppressWarnings("null")
	private @NonNull String readAttributeValue() {
		final StringBuilder attr = new StringBuilder();
		final char q = this.nextChar();
		if ( q != '"' && q != '\'' ) throw new Alert( "Attribute value not quoted" ).culprit( "Character", q );
		for (;;) {
			char ch = this.nextChar();
			if ( ch == q ) break;
			if ( ch == '&' ) {
				attr.append( this.readEscape() );
			} else {
				if ( ch == '<' ) {
					throw new Alert( "Forbidden character in attribute value" ).hint( "Use an entity reference" ).culprit( "Character", ch );
				}
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
			final boolean repeat_ok = this.tryReadChar( '+' );
			this.mustReadChar( '=' );
			this.eatWhiteSpace();
			final String value = this.readAttributeValue();
			this.builder.add( key, value, repeat_ok );
		}
	}
	

	/**
	 * This is the core routine of the algorithm, which consumes a single tag from the
	 * input stream. Standalong tags are expanded internally into separate open and close
	 * tags.
	 * @return true if it read a tag, false at end of stream.
	 */
	private boolean readNextTag( boolean allow_field, String field, boolean accept_repeat_field ) {
		
		if ( this.pending_end_tag ) {
			this.builder.endTag( this.tag_name );
			this.pending_end_tag = false;
			this.popElement();
			return true;
		}
			
		this.eatWhiteSpaceIncludingOneComma();
		
		if ( !this.cucharin.hasNextChar() ) {
			return false;
		}
		
		final char pch = this.peekChar( '\0' );
		if ( Character.isLetter( pch ) ) {
			return readLabelledTagOrSymbol();
		} else {
			return readUnlabelledTag( field, accept_repeat_field );
		}
	}
	
	private boolean readUnlabelledTag( String field, boolean accept_repeat_field ) {
		final char pch = this.peekChar( '\0' );
		if ( pch == '<' || pch == '[' || pch == '{' ) {
			return readCoreTag( field, accept_repeat_field );
		} else if ( Character.isDigit( pch ) || pch == '+' || pch == '-' ) {
			return readNumber( field );
		} else if ( pch == '"' ) {
			return readString( field );
		} else if ( pch == ']' ) {
			this.mustReadChar( ']' );
			this.popArray();
			this.builder.endArray( "" );
			return true;
		} else if ( pch == '}' ) {
			this.mustReadChar( '}' );
			this.popObject();
			this.builder.endObject( "" );
			return true;
		} else if ( Character.isLetter( pch ) ) {
			return this.handleIdentifier( this.readName() );
		} else {
			throw new Alert( "Unexpected character when read tag or constant" ).culprit( "Character", pch );
		}
	}

	private boolean readString( final String field ) {
		final char quote_char = this.nextChar();
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		while( ! done ) {
			final char ch = this.nextChar();
			switch ( ch ) {
				case '"':
				case '\'':
					if ( ch == quote_char ) {
						done = true;
					} else {
						sofar.append( ch );
					}
					break;
				case '\\':
					this.readEscapeChar( sofar );
					break;
				default:
					sofar.append( ch );
					break;
			}
		}
		this.builder.addChild( field, sofar.toString() );
		return true;
	}
	
	void readEscapeChar( final StringBuilder sofar ) {
		final char ch = this.nextChar();
		switch ( ch ) {
			case '\'':
			case '"':
			case '/':
			case '\\':
				sofar.append(  ch  );
				break;
			case 'n':
				sofar.append( '\n' );
				break;
			case 'r':
				sofar.append( '\r' );
				break;
			case 't':
				sofar.append( '\t' );
				break;
			case 'f':
				sofar.append( '\f' );
				break;
			case 'b':
				sofar.append( '\b' );
				break;
			case '&':
				sofar.append( this.readEscape() );
				break;
			default:
				sofar.append( ch );
				break;
		}
	}
	
	private boolean readNumber( final String field ) {
		boolean is_floating_point = false;
		StringBuilder b = new StringBuilder();
		boolean done = false;
		do {
			final char ch = this.peekChar( '\0' );
			switch ( ch ) {
				case '-':
				case '+':
					break;
				case '.':
					is_floating_point = true;
					break;
				default:
					if ( ! Character.isDigit( ch ) ) {
						done = true;
					}
					break;
			}
			if ( done ) break;
			b.append( ch );
			this.cucharin.skipChar();
		} while ( ! done );
		
		final String s = b.toString();
		try {
			if ( is_floating_point ) {
				this.builder.addChild( field, Double.parseDouble( s ) );
			} else {
				this.builder.addChild( field, Long.parseLong( s ) );
			}
		} catch ( NumberFormatException e ) {
			throw new Alert( "Malformed number" ).culprit( "Bad number", s );
		}

		return true;
	}
	
	private boolean readLabelledTagOrSymbol() {
		String field = this.readName();
		boolean accept_repeat_field = false;
		this.eatWhiteSpace();
		boolean plus = this.tryReadChar( '+' );
		boolean colon = this.tryReadChar( ':' );
		if ( colon ) {
			if ( plus ) {
				accept_repeat_field = true;
			}
			this.eatWhiteSpace();
			return readUnlabelledTag( field, accept_repeat_field );
		} else if ( plus ) {
			//	:+ is not allowed - we want to raise the alarm.
			this.mustReadChar( ':' ); 	//	This will throw an exception (the one we want).
			throw Alert.unreachable();	//	So compiler doesn't complain.
		} else {
			return handleIdentifier( field );
		}
	}

	private boolean handleIdentifier( String identifier ) {
		switch ( identifier ) {
		case "null":
			this.builder.addNull();
			return true;
		case "true":
		case "false":
			this.builder.addChild( Boolean.parseBoolean( identifier ) );
			return true;
		default:
			throw new Alert( "Unrecognised identifier" ).culprit( "Identifier", identifier );
		}
	}
	
	private boolean readCoreTag( String field, boolean accept_repeat_field ) {
		
		if ( this.tryReadChar( '[' ) ) {
			this.pushArray();
			this.builder.startArray( field );
			return true;
		} else if ( this.tryReadChar( '{' ) ) {
			this.pushObject();
			this.builder.startObject( field );
			return true;
		}

		this.mustReadChar( '<' );		
		char ch = this.nextChar();
		if ( ch == '/' ) {
			this.eatWhiteSpace();
			if ( this.tryReadChar( '>' ) ) {
				this.builder.endTag();
			} else {
				final String end_tag = this.readName();
				this.processAttributes();
				this.eatWhiteSpace();
				this.mustReadChar( '>' );
				this.builder.endTag( end_tag );
			}
			this.popElement();
			return true;
		} else if ( ch == '!' || ch == '?' ) {
			this.eatComment( ch  );
			return this.readNextTag( false, field, accept_repeat_field );
		} else {
			this.cucharin.pushChar( ch );
		}
		
		this.eatWhiteSpace();
		this.tag_name = this.readName();
		
		this.builder.startTag( field, this.tag_name, accept_repeat_field );
		this.processAttributes();
//		this.builder.startTagClose( this.tag_name );
		
		this.eatWhiteSpace();
				
		ch = nextChar();
		if ( ch == '/' ) {
			this.mustReadChar( '>' );
			this.pending_end_tag = true;
			this.pushElement();
			return true;
		} else if ( ch == '>' ) {
			this.pushElement();
			return true;
		} else {
			throw new Alert( "Invalid continuation" );
		}
				
	}
	
	/**
	 * Read an element off the input stream or null if the stream is
	 * exhausted.
	 * @return the next element
	 */
	public Fusion readElement() {
		while ( this.readNextTag( true, "", true ) ) {
			if ( this.isAtTopLevel() ) break;
		}
		if ( ! this.isAtTopLevel() ) {
			throw new Alert( "Unmatched tags due to encountering end of input" );
		}
		return builder.build();
	}
	
	/**
	 * Returns an iterator that reads elements off sequentially
	 * from this parser.
	 */
	public Iterator< Fusion > iterator() {
		return new Iterator< Fusion >() {

			@Override
			public boolean hasNext() {
				return FusionParser.this.hasNext();
			}

			@Override
			public Fusion next() {
				return FusionParser.this.readElement();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
		};
	}

}
