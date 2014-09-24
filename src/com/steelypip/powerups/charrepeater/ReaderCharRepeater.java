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
package com.steelypip.powerups.charrepeater;

import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

public class ReaderCharRepeater implements CharRepeater {

	final Reader reader;
	final CharBuffer buffer = new CharBuffer();
	
	public ReaderCharRepeater( Reader reader ) {
		super();
		this.reader = reader;
	}

	/**
	 * This is the key method used to take characters
	 * off the input stream. If the method returns true
	 * then it is guaranteed that there is at least one
	 * character in the buffer.
	 */
	@Override
	public boolean hasNextChar() {
		if ( ! this.buffer.isEmpty() ) return true;
		try {
			final int ich = this.reader.read();
			if ( ich >= 0 ) {
				this.buffer.pushChar( (char)ich );
			}
			return ich >= 0;
		} catch ( IOException e ) {
			throw new RuntimeException( e );
		}
	}
	
	@Override
	public boolean isNextChar( final char wanted ) {
		return( 
			( ! this.buffer.isEmpty() || this.hasNextChar() ) &&
			this.buffer.peekChar() == wanted
		);
	}

	@Override
	public char nextChar() {
		if ( ! buffer.isEmpty() || this.hasNextChar() ) {
			return this.buffer.popChar();
		} else {
			throw new RuntimeException( new EOFException() );
		}
	}

	@Override
	public char nextChar( char value_if_needed ) {
		if ( ! this.buffer.isEmpty() || this.hasNextChar() ) {
			return this.buffer.popChar();
		} else {
			return value_if_needed;
		}
	}

	@Override
	public void pushChar( char value ) {
		this.buffer.pushChar( value );
	}

	@Override
	public char peekChar() {
		if ( ! this.buffer.isEmpty() || this.hasNextChar() ) {
			return this.buffer.peekChar();
		} else {
			throw new RuntimeException( new EOFException() );
		}
	}

	@Override
	public char peekChar( char value_if_needed ) {
		if ( ! this.buffer.isEmpty() || this.hasNextChar() ) {
			return this.buffer.peekChar();
		} else {
			return value_if_needed;
		}
	}

	@Override
	public void skipChar() {
		if ( ! this.buffer.isEmpty() || this.hasNextChar() ) {
			this.buffer.popChar();
		}		
	}
	
}
