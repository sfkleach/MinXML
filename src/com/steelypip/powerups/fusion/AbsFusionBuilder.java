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
package com.steelypip.powerups.fusion;

import java.util.LinkedList;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.alert.Alert;

/**
 * This implementation of MinXMLBuilder is maximally flexible:
 * <ul>
 *	<li>It accepts null element names in startTagOpen, startTagClose and endTag.
 *	<li>The startTagClose method may be omitted in the build sequence.
 *	<li>After build is called, the builder can be reused.
 * </ul>
 */
public abstract class AbsFusionBuilder implements FusionBuilder {
	
	public abstract @NonNull FusionImplementation implementation();
		
	static class Link {
		
		private String field;
		private @NonNull Fusion current;
		
		public Link( @NonNull FusionImplementation impl, String field, String name ) {
			this.field = field;
			this.current = impl.newMutableFusion( name != null ? name : "" );
		}

		public String getField() {
			return field;
		}

		public void setField( String field ) {
			this.field = field;
		}

		public @NonNull Fusion getCurrent() {
			return current;
		}
		
		@SuppressWarnings("null")
		public @NonNull String getNonNullField() {
			return this.field != null ? this.field : "";
		}
		
	}
	
	private @NonNull Link current_link = new Link( this.implementation(), "DUMMY FIELD", "DUMMY_NODE" );
	private final LinkedList< @NonNull Link > link_stack = new LinkedList<>();
	//private final Conventions litf = new StdConventions();
	
	private @NonNull Fusion current() {
		return this.current_link.getCurrent();
	}

	@Override
	public void startTagOpen( final String field, final String name ) {
		link_stack.addLast( current_link );
		this.current_link = new Link( this.implementation(), field, name );
	}

	@Override
	public void startTagOpen( final String name ) {
		this.startTagOpen( "", name );
	}

	@Override
	public void startTagOpen() {
		this.startTagOpen( null, null );
	}

	@Override
	public void add( @NonNull String key, @NonNull String value ) {
		this.current().addValue( key, value );
	}
	
//	private void addLiteral( final @NonNull String type_value, final @NonNull String value ) {
//		this.startTagOpen( StdLiterals.INSTANCE.getConstant() );
//		this.add( StdLiterals.INSTANCE.getType(), type_value );
//		this.add( StdLiterals.INSTANCE.getValue(), value );
//		this.endTag();		
//	}
	
//	@SuppressWarnings("null")
	@Override
	public void addChild( final long number ) {
		this.addChild( this.implementation().newIntegerFusion( number ) );
//		this.addLiteral( StdLiterals.INSTANCE.getInteger(), Objects.requireNonNull( Long.toString( number, 10 ) ) );
	}
	
//	@SuppressWarnings("null")
	@Override
	public void addChild( final double number ) {
		this.addChild( this.implementation().newFloatFusion( number ) );
//		this.addLiteral( StdLiterals.INSTANCE.getFloat(), Objects.requireNonNull( Double.toString( number ) ) );
	}
	
	@Override
	public void addChild( final @Nullable String string ) {
		this.addChild( this.implementation().newStringFusion( string ) );
//		if ( string != null ) {
//			this.addLiteral( StdLiterals.INSTANCE.getString(), string );
//		} else {
//			this.addLiteral( StdLiterals.INSTANCE.getNull(), StdLiterals.INSTANCE.getNull() );
//		}
	}
	
//	@SuppressWarnings("null")
	@Override
	public void addChild( final boolean bool ) {
		this.addChild( this.implementation().newBooleanFusion( bool ) );
//		this.addLiteral( StdLiterals.INSTANCE.getBoolean(), Objects.requireNonNull( Boolean.toString( bool ) ) );
	}
	
	@Override 
	public void addChild( final @Nullable Fusion x ) {
		if ( x == null ) {
			this.addNull();
		} else {
			this.current_link.getCurrent().addChild( x );
		}
	}
	
	@Override
	public void addNull() {
		this.current_link.getCurrent().addChild( this.implementation().newNullFusion() );
	}
	
	private void bindName( final @Nullable String name ) {
		if ( name != null ) {
			if ( this.current().hasName( "" ) ) {
				this.current().setName( name );
			} else if ( ! this.current().hasName( name ) ) {
				throw new Alert( "Mismatched tags" ).culprit( "Expected", this.current().getName() ).culprit( "Actual", name );				
			}
		}		
	}
	
	private void bindField( final @Nullable String field ) {
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
		final Link b2 = link_stack.removeLast();
		b2.getCurrent().addChild( this.current_link.getNonNullField(), this.current_link.getCurrent() );
		this.current_link = b2;
	}

	@Override
	public Fusion build() {
		if ( this.current().hasNoFields() ) {
			return null;
		} else {
			Fusion result = this.current().getChild();
			this.current().clearAllLinks();
			return result;
		}
	}

	
}
