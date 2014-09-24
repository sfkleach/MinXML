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

import com.steelypip.powerups.common.IfNull;
import com.steelypip.powerups.io.StringPrintWriter;

/**
 * This class has to be final so that other classes can specialise the culprit method.
 *
 */
public final class Culprit {
	
	private final String desc;
	private final Object arg;

	Culprit( final String desc1, final Object arg1 ) {
		this.desc = IfNull.ifNull( desc1, "" );
		this.arg = arg1;
	}
	
	private static String convertToString( final Object x ) {
		return "" + x;
	}

	private String keepShort( final Object x, final int mx_len, final int filemxlen ) {
		final int mxlen = mx_len > 5 ? mx_len : 5;
		final String s = convertToString( x );
		if ( s.length() > mxlen ) {
			if ( s.charAt( 0 ) == '/' && s.length() < filemxlen ) {
				return s;
			} else {
				return s.substring( 0, mxlen - 4 ) + " ...";
			}
		} else {
			return s;
		}
	}

	static final int maxlen = 256;
	static final int filemaxlen = 256;

	private String keepShort( Object x ) {
		return keepShort( x, maxlen, filemaxlen );
	}

	static final int min_pad_width = 8;
	
	void output( final PrintWriter pw ) {
		final String d = this.desc.toUpperCase();
		pw.print( d );
		for ( int i = d.length(); i < min_pad_width; i++ ) {
			pw.print( " " );
		}
		pw.print( " : " );
		pw.println( this.keepShort( this.arg ) );
	}
	
	String asString() {
		final StringPrintWriter pw = new StringPrintWriter();
		this.output( pw );
		return pw.toString();		
	}

	public String getKey() {
		return this.desc;
	}

	public boolean hasKey( final String interned ) {
		return this.desc.equals( interned ); 
	}

	public Object getValue() {
		return this.arg;
	}

	public String getValueString() {
		return this.arg != null ? this.arg.toString() : "";
	}

}

