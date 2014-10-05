package com.steelypip.powerups.json;

public interface JSONBuilder< T > {
	void addNull();
	void addBoolean( boolean value );
	void addInteger( long value );
	void addFloat( double value );
	void addString( String value );
	void startArray();
	void endArray();
	void field( String field );
	void startObject();
	void endObject();
	T build();
}
