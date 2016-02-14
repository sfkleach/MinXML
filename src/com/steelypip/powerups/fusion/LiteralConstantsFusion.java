package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public abstract class LiteralConstantsFusion implements Fusion, LiteralConstants {
	
	private boolean isConstantOfType( final @NonNull String type ) {
		return ( 
			this.hasName( this.constConstant() ) &&
			this.hasAttribute( this.constValue() ) &&
			this.hasAttribute( this.constType(), type )
		);
	}

	public boolean isInteger() {
		return this.isConstantOfType( this.constInteger() );
	}
	
	public @Nullable Long integerValue() {
		if ( ! this.isInteger() ) return null;
		return Long.parseLong( this.getValue( this.constValue() ) );
	}
	
	public long integerValue( long otherwise ) {
		if ( ! this.isInteger() ) return otherwise;
		return Long.parseLong( this.getValue( this.constValue() ) );		
	}
	
	public boolean isFloat() {
		return this.isConstantOfType( this.constFloat() );
	}
	public @Nullable Double floatValue() {
		if ( ! this.isFloat() ) return null;
		return Double.parseDouble( this.getValue( this.constValue() ) );
	}
	
	public double floatValue( double otherwise ) {
		if ( ! this.isFloat() ) return otherwise;
		return Double.parseDouble( this.getValue( this.constValue() ) );		
	}

	public boolean isString() {
		return this.isConstantOfType( this.constString() );
	}
	
	public @Nullable String stringValue() {
		return this.stringValue( null );		
	}
	
	public String stringValue( String otherwise ) {
		if ( ! this.isString() ) return null;
		return this.getValue( this.constValue() );		
	}
	
	public boolean isBoolean() {
		return this.isConstantOfType( this.constBoolean() );
	}
	
	public @Nullable Boolean booleanValue() {
		if ( ! this.isString() ) return null;
		return Boolean.parseBoolean( this.getValue( this.constValue() ) );		
		
	}
	
	public boolean booleanValue( boolean otherwise ) {
		if ( ! this.isString() ) return otherwise;
		return Boolean.parseBoolean( this.getValue( this.constValue() ) );		
	}
	
	public boolean isNull() {
		return this.isConstantOfType( this.constNullType() );
	}
	
	public boolean isArray() {
		return this.hasName( this.constArrayType() );
	}
	
	public boolean isObject() {
		return this.hasName( this.constObjectType() );
	}
	
}
