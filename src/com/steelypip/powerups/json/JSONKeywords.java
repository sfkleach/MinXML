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

package com.steelypip.powerups.json;

import java.util.Map;

public class JSONKeywords {

	public final String TYPE;
	public final String FIELD;
	public final String CONSTANT;
	public final String CONSTANT_TYPE;
	public final String CONSTANT_VALUE;
	public final String FLOAT;
	public final String INTEGER;
	public final String STRING;
	public final String BOOLEAN;
	public final String NULLEAN;
	public final String ARRAY;
	public final String OBJECT;
	public final String NULLEAN_NULL;
	public final String BOOLEAN_TRUE;
	public final String BOOLEAN_FALSE;
	public final String ID;
	public final String ID_NAME;
	
	
	
	private static String pick( Map< String, String > keys, String k, String otherwise ) {
		if ( keys == null ) return otherwise;
		final String first_choice = keys.get( k );
		return first_choice != null ? first_choice : otherwise;
	}
	
	public JSONKeywords( final Map< String, String > keys ) {
		 TYPE = pick( keys, "TYPE", "type" );
		 FIELD = pick( keys, "FIELD", "field" );
		 CONSTANT = pick( keys, "CONSTANT", "constant" );
		 CONSTANT_TYPE = pick( keys, "CONSTANT_TYPE", "type" );
		 CONSTANT_VALUE = pick( keys, "CONSTANT_VALUE", "value" );
		 FLOAT = pick( keys, "FLOAT", "float" );
		 INTEGER = pick( keys, "INTEGER", "integer" );
		 STRING = pick( keys, "STRING", "string" );
		 BOOLEAN = pick( keys, "BOOLEAN", "boolean" );
		 NULLEAN = pick( keys, "NULLEAN", "null" );
		 ARRAY = pick( keys, "ARRAY", "array" );
		 OBJECT = pick( keys, "OBJECT", "object" );
		 NULLEAN_NULL = pick( keys, "NULLEAN_NULL", "null" );
		 BOOLEAN_TRUE = pick( keys, "BOOLEAN_TRUE", "true" );
		 BOOLEAN_FALSE = pick( keys, "BOOLEAN_FALSE", "false" );
		 ID = pick( keys, "ID", "id" );
		 ID_NAME = pick( keys, "ID_NAME", "name" );
	}
	
	public static final JSONKeywords KEYS = new JSONKeywords( null );
}
