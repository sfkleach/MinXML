package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Must return INTERNED values.
 */
interface LiteralConstants {
	
	default @NonNull String constConstant() {return "constant"; }
	default @NonNull String constValue() { return "value"; }
	default @NonNull String constType() { return "type"; }
	default @NonNull String constBoolean() { return "boolean"; }
	default @NonNull String constNullType() { return "null"; }
	default @NonNull String constNull() { return "null"; }
	default @NonNull String constString() { return "string"; }
	default @NonNull String constFloat()  { return "float"; }
	default @NonNull String constInteger()  { return "integer"; }
	default @NonNull String constTrueOrFalse( boolean b ) { return b ? "true" : "false"; }
	default @NonNull String constArrayType() { return "array"; }
	default @NonNull String constObjectType() { return "object"; }
			
}