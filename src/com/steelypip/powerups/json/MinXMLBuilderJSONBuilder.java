package com.steelypip.powerups.json;

import com.steelypip.powerups.minxml.MinXML;
import com.steelypip.powerups.minxml.MinXMLBuilder;

public class MinXMLBuilderJSONBuilder implements JSONBuilder< MinXML > {
	
	MinXMLBuilder builder;
	boolean is_in_element = false;
	String field_name = null;
	
	public MinXMLBuilderJSONBuilder( final MinXMLBuilder builder ) {
		this.builder = builder;
	}
	
	private void emitField() {
		if ( this.field_name != null ) {
			this.builder.put( "field", this.field_name );
		}		
		this.field_name = null;
	}
	
	private void emitConstant( final String type, final String value ) {
		this.builder.startTagOpen( "constant" );
		this.builder.put( "type", type );
		this.builder.put( "value", value );
		this.emitField();
		this.builder.startTagClose( "constant" );
		this.builder.endTag( "constant" );		
	}

	@Override
	public void addNull() {
		this.emitConstant( "null", "null" );
	}

	@Override
	public void addBoolean( final boolean value ) {
		this.emitConstant( "boolean", "" + value );
	}

	@Override
	public void addInteger( final long value ) {
		this.emitConstant( "integer", "" + value );
	}

	@Override
	public void addFloat( double value ) {
		this.emitConstant( "float", "" + value );
	}

	@Override
	public void addString( String value ) {
		this.emitConstant( "string", value );
	}

	@Override
	public void startArray() {
		this.builder.startTagOpen( "array" );
		this.emitField();
		this.builder.startTagClose( "array" );
	}

	@Override
	public void endArray() {
		this.builder.endTag( "array" );
	}

	@Override
	public void field( final String field ) {
		this.field_name = field;
	}

	@Override
	public void startObject() {
		this.builder.startTagOpen( "object" );
		this.emitField();
		this.builder.startTagClose( "object" );
		
	}

	@Override
	public void endObject() {
		this.builder.endTag( "object" );
	}

	@Override
	public MinXML build() {
		return this.builder.build();
	}

}
