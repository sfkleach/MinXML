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

import java.util.LinkedList;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.alert.Alert;

/**
 * This implementation of MinXMLBuilder is maximally flexible:
 * <ul>
 *	<li>It accepts null element names in startTagOpen, startTagClose and endTag.
 *	<li>The startTagClose method may be omitted in the build sequence.
 *	<li>After build is called, the builder can be reused.
 * </ul>
 */
public class FlexiMinXMLStarBuilder implements MinXMLStarBuilder {
	
	static class Link {
		
		private String field;
		private @NonNull FlexiMinXMLStar current;
		
		public Link( String field, String name ) {
			this.field = field;
			this.current = new FlexiMinXMLStar( name != null ? name : "" );
		}

		public String getField() {
			return field;
		}

		public void setField( String field ) {
			this.field = field;
		}

		public @NonNull FlexiMinXMLStar getCurrent() {
			return current;
		}
		
		@SuppressWarnings("null")
		public @NonNull String getNonNullField() {
			return this.field != null ? this.field : "";
		}
		
	}

	private @NonNull Link current_link = new Link( "DUMMY FIELD", "DUMMY_NODE" );
	private final LinkedList< @NonNull Link > link_stack = new LinkedList<>();
	
	private @NonNull FlexiMinXMLStar current() {
		return this.current_link.getCurrent();
	}

	@Override
	public void startTagOpen( final String field, final String name ) {
		link_stack.addLast( current_link );
		this.current_link = new Link( field, name );
	}

	@Override
	public void startTagOpen( final String name ) {
		this.startTagOpen( name, null );
	}

	@Override
	public void startTagOpen() {
		this.startTagOpen( null, null );
	}

	@Override
	public void add( @NonNull String key, @NonNull String value ) {
		this.current().addValue( key, value );
	}
	
	private void bindName( final String name ) {
		if ( name != null ) {
			if ( this.current().hasName( "" ) ) {
				this.current().setName( name );
			} else if ( ! this.current().hasName( name ) ) {
				throw new Alert( "Mismatched tags" ).culprit( "Expected", this.current().getName() ).culprit( "Actual", name );				
			}
		}		
	}
	
	private void bindField( final String field ) {
		if ( field != null ) {
			if ( this.current_link.getField() == null ) {
				this.current_link.setField( field );
			} else if ( ! this.current_link.getField().equals( field ) ) {
				throw new Alert( "Mismatched fields" ).culprit( "Expected", this.current_link.getField() ).culprit( "Actual", field );				
			}
		}
	}

	@Override
	public void startTagClose( final String name ) {
		this.bindName( name );
	}

	@Override
	public void startTagClose( final String field, final String name ) {
		this.bindField( field );
		this.bindName( name );
	}

	@Override
	public void startTagClose() {
	}
	
	@Override
	public void endTag( String name ) {
		this.bindName( name );
		this.endTag();
	}
	

	@Override
	public void endTag( String name, String field ) {
		this.bindField( field );
		this.endTag( name );
	}

	@Override
	public void endTag() {
		this.current().trimToSize();
		final Link b2 = link_stack.removeLast();
		b2.getCurrent().addChild( this.current_link.getNonNullField(), this.current_link.getCurrent() );
		this.current_link = b2;
	}

	@Override
	public MinXMLStar build() {
		if ( this.current().hasNoFields() ) {
			return null;
		} else {
			MinXMLStar result = this.current().getChild();
			this.current().clearAllLinks();
			return result;
		}
	}

	
}
