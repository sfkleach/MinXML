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

import java.util.LinkedList;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;

public class FlexiMinXMLBuilder implements MinXMLBuilder {

	@NonNull FlexiMinXML current_element = new FlexiMinXML( "DUMMY_NODE" );
	final LinkedList< @NonNull FlexiMinXML > element_stack = new LinkedList<>();

	@Override
	public void startTagOpen( String name ) {
		element_stack.addLast( current_element );
		this.current_element = new FlexiMinXML( name );
	}

	@Override
	public void put( String key, String value ) {
		this.current_element.putAttribute( key, value );
	}

	@Override
	public void startTagClose( final String _unused ) {
	}

	@Override
	public void endTag( String name ) {
		this.current_element.trimToSize();
		if ( name == null || this.current_element.hasName( name ) ) {
			final FlexiMinXML b2 = element_stack.removeLast();
			b2.add( this.current_element );
			this.current_element = b2;
		} else {
			throw new Alert( "Mismatched tags" ).culprit( "Expected", this.current_element.getName() ).culprit( "Actual", name );
		}
	}

	@Override
	public MinXML build() {
		if ( this.current_element.isEmpty() ) {
			return null;
		} else {
			MinXML result = this.current_element.get( this.current_element.size() - 1 );
			this.current_element.clear();
			return result;
		}
	}
	
}
