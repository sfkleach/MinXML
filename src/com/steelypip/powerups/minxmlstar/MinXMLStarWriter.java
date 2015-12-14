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
package com.steelypip.powerups.minxmlstar;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.Indenter;
import com.steelypip.powerups.common.IndenterFactory;
import com.steelypip.powerups.common.NullIndenter;

/**
 * Prints out a MinXMLStar object to a {@link java.io.PrintWriter}. It can
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
public class MinXMLStarWriter {

	final PrintWriter pw;
	final Indenter indenter;
	final ElementTheme.Selector theme_selector;
	
	public MinXMLStarWriter( PrintWriter pw, IndenterFactory indenter_factory, ElementTheme.Selector.Factory selector_factory ) {
		this.pw = pw;
		this.indenter = indenter_factory.newIndenter( pw );
		this.theme_selector = selector_factory.newInstance( this );
	}
	
	private static NullIndenter.Factory DEFAULT_INDENT_FACTORY = new NullIndenter.Factory();
	private static ElementTheme.Selector.Factory DEFAULT_THEME_SELECTOR_FACTORY = new XmlElementTheme.Selector.Factory();
	
	public MinXMLStarWriter( PrintWriter pw ) {
		this( pw, DEFAULT_INDENT_FACTORY, DEFAULT_THEME_SELECTOR_FACTORY );
	}
	
	public MinXMLStarWriter( Writer w ) {
		this( new PrintWriter( w ) );
	}
	
	PrintWriter getPrintWriter() {
		return this.pw;
	}
	
	
	public void print( MinXMLStar x ) {
		final ElementTheme theme = this.theme_selector.select( x );
		
		final String name = x.getName();
		final boolean has_any_attributes = x.hasAnyAttributes();
		final boolean has_any_links = x.hasAnyLinks();
		
		this.indenter.indent();
		theme.doStartElement( name, has_any_attributes, has_any_links );
		theme.doName( name );
		
		theme.doStartAttributes( has_any_attributes, has_any_links );
		for ( @NonNull String key : x.keysToSet() ) {
			theme.doStartAttributeGroup( key );
			final List< @NonNull String > values = x.valuesToList( key );
			int n = 0;
			for ( @NonNull String value : values ) {
				n += 1;
				theme.doAttribute( key, value,  n == 1, n == values.size() );
			}
			theme.doEndAttributeGroup( key );
		}
		theme.doEndAttributes( has_any_attributes, has_any_links );
		
		theme.doStartLinks( has_any_attributes, has_any_links );
		for ( @NonNull String field : x.fieldsToSet() ) {
			final @NonNull List< @NonNull MinXMLStar > children = x.childrenToList( field );
			theme.doStartLinkGroup( field );
			int n = 0;
			for ( @NonNull MinXMLStar child : children) {
				n += 1;
				theme.doLink( field, child, n == 1, n == children.size() );
			}
			theme.doEndLinkGroup( field );
		}
		theme.doEndLinks( has_any_attributes, has_any_links ); 
		
		theme.doEndElement( name, has_any_attributes, has_any_links );
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

}
