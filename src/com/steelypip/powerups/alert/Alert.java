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
package com.steelypip.powerups.alert;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

import com.steelypip.powerups.io.StringPrintWriter;

public class Alert extends RuntimeException implements Iterable< Culprit > {

	private static final long serialVersionUID = -7054658959511420366L;
	private LinkedList< Culprit > culprit_list = new LinkedList< Culprit >();

	public Alert() {
		super();
	}

	public Alert( final String message, final Throwable cause ) {
		super( message, cause );
	}

	public Alert( final String message ) {
		super( message );
	}

	public Alert( final Throwable cause ) {
		super( cause );
	}
	
	private void add( final Culprit culprit ) {
		this.culprit_list.add( culprit );
	}

	public Iterator< Culprit > iterator() {
		return this.culprit_list.iterator(); 
	}
	
	public Culprit get( final String key ) {
		if ( key == null ) return null;
		for ( Culprit c : this.culprit_list ) {
			if ( key.equals(  c.getKey() ) ) {
				return c;
			}
		}
		return null;
	}
	
	public boolean hasKeyValue( String k, Object v ) {
		final Object x = this.get( k );
		return x == v || x != null && x.equals( v );
	}
	
	public boolean hasKey( String k ) {
		return this.get( k ) != null;
	}

	public Alert culprit( final String desc, final Object arg ) {
		this.add( new Culprit( desc, arg ) );
		return this;
	}

	public Alert culprit( final String desc, final int arg ) {
		return this.culprit( desc, new Integer( arg ) );
	}

	public Alert culprit( final String desc, final char ch ) {
		return this.culprit( desc, new Character( ch ) );
	}

	public Alert culprit( final Iterable< Culprit > list ) {
		for ( Culprit c : list ) {
			this.add( c );
		}
		return this;
	}

	public void reportTo( final PrintWriter pw ) {
		pw.print( "ALERT : " ); 
		pw.println( this.getMessage() );
		for ( Culprit c : this.culprit_list ) {
			c.output( pw );
		}
		pw.println( "" );
		pw.flush();
	}

	public void report() {
		this.reportTo( new PrintWriter( System.err ) );
	}
	
	public String toString() {
		final StringPrintWriter pw = new StringPrintWriter();
		this.reportTo( pw );
		return pw.toString();
	}

	//-- Special Keys -------------------------------------

	public Alert hint( final String msg ) {
		return this.culprit( "hint", msg );
	}
	
	public Alert code( final String string ) {
		return this.culprit( "code", string );
	}
	
	public Alert note( final String string ) {
		return this.culprit( "note", string );
	}
	
	public String getCodeString() {
		final Culprit c = this.get( "code" );
		return c == null ? null : (String)c.getValue();
	}

	//	---- This section just deals with the statics ----

	public static Alert unreachable() {
		return unreachable( (Throwable)null );
	}

	public static Alert unreachable( final Throwable t ) {
		return unreachable( "Internal error", t );
	}

	public static Alert unreachable( final String msg ) {
		return unreachable( msg, null );
	}

	public static Alert unreachable( final String msg, final Throwable t ) {
		return new Alert( msg, t ).note( "A supposedly unreachable condition has been detected" );
	}

	public static Alert unimplemented() {
		return unimplemented( "Unimplemented" );
	}

	public static Alert unimplemented( final String msg ) {
		final Alert alert = new Alert( msg ).note( "An unimplemented feature has been reached" );
		alert.culprit( "message", msg );
		return alert;
	}

	public static Alert internalError() {
		return new Alert( "Internal Error" );
	}

	public static Alert internalError( final String msg ) {
		return new Alert( "Internal Error" ).note( "This is an internal error" );
	}


}