package com.steelypip.powerups.fusion.io;

import com.steelypip.powerups.fusion.Fusion;

public class JSONTheme implements Theme {
	
	@Override
	public boolean tryRender( FusionWriter w, Fusion x ) {
		if ( x.isJSONItem() ) {
			if ( x.isInteger() ) {
				w.print( x.integerValue( 0 ) );
				return true;				
			} else if ( x.isFloat() ) {
				w.print( x.floatValue( 0 ) );
				return true;				
			} else if ( x.isString() ) {
				w.print( '"' );
				//	TODO PRINT ESCAPED CHARACTERS
				w.print( '"' );
				return true;				
			} else if ( x.isBoolean() ) {
				w.print( x.booleanValue( false ) );
				return true;				
			} else if ( x.isNull() ) {
				w.print( "null" );
				return true;				
			} else if ( x.isArray() ) {
				w.print( '[' );
				//	TODO PRINT ATTRIBUTES
				w.print( ']' );
				return true;				
			} else if ( x.isObject() ) {
				w.print( '{' );
				//	TODO PRINT LINKS
				w.print( '}' );
				return true;				
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
