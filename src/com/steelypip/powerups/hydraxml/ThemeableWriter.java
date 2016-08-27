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
package com.steelypip.powerups.hydraxml;

import java.io.PrintWriter;
import java.io.Writer;

import com.steelypip.powerups.common.Indenter;
import com.steelypip.powerups.common.IndenterFactory;
import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.fusion.Fusion;

/**
 * Prints out an object to a {@link java.io.PrintWriter}. It can
 * be a simple-all-on-one-line or an elaborate pretty-printer depending
 * on the Indenter, and look like XML or JSON depending on the Theme that
 * is supplied.
 * 
 * The base theme is XML and for 0 children it looks like
 * 		< NAME KEY (=|+=) VALUE ... /> 
 * and for 1+ children
 * 		< NAME KEY=VALUE ... > [FIELD (=|+=)] CHILD ... </ NAME >
 * If the field name is omitted it is assumed to be "" and the
 * binding assumed to be +=. 
 * 
 * The JSON theme differs on "array" and "object" elements. When
 * when NAME is "array" and there are no attibutes, prefer
 * 		[ CHILD, ... ]
 * and when the NAME is "object" and there are no attributes and all
 * fields are single-valued, prefer
 * 		{ FIELD: CHILD, ... }
 * Note that in this case the field will be double-quoted.
 * 
 * The JSON theme also differs on literals:
 * 	When the name is "constant" and the only attributes are "type" whose
 *  value is "integer", "value" and optionally "radix", print as a literal
 *  integer - including transinteger values +/- infinity and nullity.
 *  When the name is "constant" and the only attributes are "type" whose
 *  value is "float", "value" and optionally "radix", print as a literal
 *  floating point number - including transreal values +/- infinity and nullity.
 * 	When the name is "constant" and the only attributes are "type" whose
 * 	value is "string" and "value", print as a double-quoted literal string. 
 * 	When the name is "constant" and the only attributes are "type" whose
 * 	value is "boolean" and "value", print as a boolean.
 *  When the name is "constant" and the only attributes are "type" whose
 *  value is "null" and optionally "value" whose value must be null, print
 *  as null.
 *  
 *  The FUSION theme differs from the XML by using the following format
 *  	< NAME KEY=VALUES ... FIELD:CHILDREN ... />
 *  where
 *  	VALUES ::= VALUE | '(' VALUE+ ')'
 *  	CHILDREN ::= CHILD | '(' CHILD+ ')'
 *  however it shares the literal printing with the JSON theme.
 */
public class ThemeableWriter< T > {

	final PrintWriter pw;
	Indenter indenter;
	Theme< T > theme;
	
	public ThemeableWriter( PrintWriter pw, IndenterFactory indenter_factory, Theme< T > theme ) {
		this.pw = pw;
		this.indenter = indenter_factory.newIndenter( pw );
		this.theme = theme;
	}
	
	private static NullIndenter.Factory DEFAULT_INDENT_FACTORY = new NullIndenter.Factory();
		
	public ThemeableWriter( PrintWriter pw, Theme< T > theme ) {
		this( pw, DEFAULT_INDENT_FACTORY, theme );
	}
	
	public ThemeableWriter( Writer w, Theme< T > theme ) {
		this( new PrintWriter( w ), theme );
	}
	
	PrintWriter getPrintWriter() {
		return this.pw;
	}
	
	public Indenter getIndenter() {
		return indenter;
	}
	
	public Theme< T > getTheme() {
		return theme;
	}

	public void setTheme( Theme< T > theme ) {
		this.theme = theme;
	}

	public void setIndenterFactory( IndenterFactory indenterf ) {
		this.indenter = indenterf.newIndenter( this.pw );
	}

	public void print( T x ) {
		if ( ! this.theme.tryRender( this, x ) ) {
			throw new IllegalStateException( "Canont render this item" );
		}
	}
	
	void renderString( final String v ) {
		for ( int n = 0; n < v.length(); n++ ) {
			final char ch = v.charAt( n );
			if ( ch == '"' ) {
				pw.print( "&quot;" );
			} else if ( ch == '\'' ) {
				pw.print(  "&apos;" );
			} else if ( ch == '<' ) {
				pw.print( "&lt;" );
			} else if ( ch == '>' ) {
				pw.print( "&gt;" );
			} else if ( ch == '&' ) {
				pw.print( "&amp;" );
			} else if ( ' ' <= ch && ch <= '~' ) {
				pw.print( ch );
			} else {
				pw.print( "&#" );
				pw.print( (int)ch );
				pw.print( ';' );
			}
		}
	}

	public void flush() {
		pw.flush();
	}

	public void print( char c ) {
		pw.print( c );
	}

	public void print( String s ) {
		pw.print( s );
	}

	public void print( long s ) {
		pw.print( s );
	}

	public void print( double s ) {
		pw.print( s );
	}

	public void print( boolean s ) {
		pw.print( s );
	}

}
