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
package com.steelypip.powerups.io;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class StringPrintWriter extends PrintWriter {
	
	public StringPrintWriter() {
		super( new CharArrayWriter(), false );
	}

	public void reset() {
		getCharArrayWriter().reset();
	}

	public int size() {
		return getCharArrayWriter().size();
	}

	public char[] toCharArray() {
		return getCharArrayWriter().toCharArray();
	}

	public String toString() {
		return getCharArrayWriter().toString();
	}

	public void writeTo( Writer out ) throws IOException {
		getCharArrayWriter().writeTo( out );
	}

	private CharArrayWriter getCharArrayWriter() {
		return (CharArrayWriter)this.out;
	}
	
}
