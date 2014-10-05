package com.steelypip.powerups.json;

import com.steelypip.powerups.minxml.MinXML;
import com.steelypip.powerups.minxml.MinXMLBuilder;

public class MinXMLBuilderJSONBuilder implements JSONBuilder< MinXML > {
	
	MinXMLBuilder builder;
	boolean is_in_element = false;
	String field_name = null;
	final JSONKeywords keywords;
	
	
	public MinXMLBuilderJSONBuilder( final MinXMLBuilder builder ) {
		this( builder, JSONKeywords.KEYS );
	}
	
	public MinXMLBuilderJSONBuilder( final MinXMLBuilder builder, final JSONKeywords keys ) {
		this.builder = builder;
		this.keywords = keys;
	}
	
	private void emitField() {
		if ( this.field_name != null ) {
			this.builder.put( keywords.FIELD, this.field_name );
		}		
		this.field_name = null;
	}
	
	private void emitConstant( final String type, final String value ) {
		this.builder.startTagOpen( keywords.CONSTANT );
		this.builder.put( keywords.CONSTANT_TYPE, type );
		this.builder.put( keywords.CONSTANT_VALUE, value );
		this.emitField();
		this.builder.startTagClose( keywords.CONSTANT );
		this.builder.endTag( keywords.CONSTANT );		
	}

	@Override
	public void addNull() {
		this.emitConstant( keywords.NULLEAN, keywords.NULLEAN_NULL );
	}

	@Override
	public void addBoolean( final boolean value ) {
		this.emitConstant( keywords.BOOLEAN, value ? keywords.BOOLEAN_TRUE : keywords.BOOLEAN_FALSE );
	}

	@Override
	public void addInteger( final long value ) {
		this.emitConstant( keywords.INTEGER, "" + value );
	}

	@Override
	public void addFloat( double value ) {
		this.emitConstant( keywords.FLOAT, "" + value );
	}

	@Override
	public void addString( String value ) {
		this.emitConstant( keywords.STRING, value );
	}

	@Override
	public void startArray() {
		this.builder.startTagOpen( keywords.ARRAY );
		this.emitField();
		this.builder.startTagClose( keywords.ARRAY );
	}

	@Override
	public void endArray() {
		this.builder.endTag( keywords.ARRAY );
	}

	@Override
	public void field( final String field ) {
		this.field_name = field;
	}

	@Override
	public void startObject() {
		this.builder.startTagOpen( keywords.OBJECT );
		this.emitField();
		this.builder.startTagClose( keywords.OBJECT );
		
	}

	@Override
	public void endObject() {
		this.builder.endTag( keywords.OBJECT );
	}

	@Override
	public MinXML build() {
		return this.builder.build();
	}

}
