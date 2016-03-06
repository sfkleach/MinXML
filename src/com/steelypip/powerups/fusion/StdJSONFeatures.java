package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import com.steelypip.powerups.fusion.LiteralConstants;

public interface StdJSONFeatures extends JSONFeatures, LiteralConstants {
	
	boolean hasAttribute( final @NonNull String type );
	boolean hasAttribute( final @NonNull String type, final String value );
	String getValue( final @NonNull String type );
	
	default boolean isConstant( final @NonNull String type ) {
		return this.hasAttribute( this.keyType(), type ) && this.hasAttribute( this.keyValue() );
	}
	
	default String getConstantValueAsString() {
		return this.getValue( this.keyValue() );
	}

	@Override
	default @Nullable Long integerValue() {
		try {
			return this.isConstant( this.constTypeInteger() ) ? Long.parseLong( this.getConstantValueAsString() ) : null;
		} catch ( NumberFormatException _e ) {
			return null;
		}
	}

	@Override
	default @Nullable Double floatValue() {
		try {
			return this.isConstant( this.constTypeFloat() ) ? Double.parseDouble( this.getConstantValueAsString() ) : null;
		} catch ( NumberFormatException _e ) {
			return null;
		}
	}

	@Override
	default @Nullable String stringValue() {
		return this.isConstant( this.constTypeString() ) ? this.getConstantValueAsString() : null;
	}

	@Override
	default @Nullable Boolean booleanValue() {
		return this.isConstant( this.constTypeBoolean() ) ? Boolean.parseBoolean( this.getConstantValueAsString() ) : null;
	}

	@Override
	default boolean isNull() {
		return this.isConstant( this.constTypeNull() );
	}

	@Override
	default boolean isArray() {
		return this.isConstant( this.constTypeArray() );
	}

	@Override
	default boolean isObject() {
		return this.isConstant( this.constTypeObject() );
	}

	
}