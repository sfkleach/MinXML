package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.Nullable;

public interface JSONic {

	boolean isInteger();
	@Nullable Long integerValue();
	long integerValue( long otherwise );
	
	boolean isFloat();
	@Nullable Double floatValue();
	double floatValue( double otherwise );

	boolean isString();
	@Nullable String stringValue();
	String stringValue( String otherwise );
	
	boolean isBoolean();
	@Nullable Boolean booleanValue();
	boolean booleanValue( boolean otherwise );
	
	boolean isNull();
	boolean isArray();
	boolean isObject();
	
}
