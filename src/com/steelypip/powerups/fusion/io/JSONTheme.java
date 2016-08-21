package com.steelypip.powerups.fusion.io;

import com.steelypip.powerups.fusion.Fusion;

public class JSONTheme implements Theme {
	
	@Override
	public boolean tryRender( FusionWriter fwriter, Fusion x ) {
		if ( x.isJSONItem() ) {
			if ( x.isInteger() ) {
				fwriter.print( x.integerValue( 0 ) );
				return true;				
			} else if ( x.isFloat() ) {
				fwriter.print( x.floatValue( 0 ) );
				return true;				
			} else if ( x.isString() ) {
				fwriter.print( '"' );
				//	TODO PRINT ESCAPED CHARACTERS
				fwriter.print( '"' );
				return true;				
			} else if ( x.isBoolean() ) {
				fwriter.print( x.booleanValue( false ) );
				return true;				
			} else if ( x.isNull() ) {
				fwriter.print( "null" );
				return true;				
			} else if ( x.isArray() ) {
				fwriter.print( '[' );
				//	TODO PRINT ATTRIBUTES
				fwriter.print( ']' );
				return true;				
			} else if ( x.isObject() ) {
				fwriter.print( '{' );
				//	TODO PRINT LINKS
				fwriter.print( '}' );
				return true;				
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
