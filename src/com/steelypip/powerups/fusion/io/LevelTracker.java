package com.steelypip.powerups.fusion.io;

import java.util.ArrayDeque;

public class LevelTracker {
	
	static enum Context {
		ELEMENT( "element" ),
		ARRAY( "array" ),
		OBJECT( "object" );
		
		public String name;
		Context( String name ) {
			this.name= name;
		}
	}
	
	ArrayDeque< Context > contexts = new ArrayDeque<>();
	
	boolean isAtTopLevel() {
		return contexts.isEmpty();
	}
	
	void pop( Context actual ) {
		Context expecting = contexts.removeLast();
		if ( actual != expecting ) {
			throw new IllegalStateException(String.format( "Found {} but was expecting {}", actual.name, expecting.name ) );
		}
	}
	
	void popElement() {
		pop( Context.ELEMENT );
	}
	
	void pushElement() {
		contexts.add( Context.ELEMENT );
	}
	
	void popArray() {
		pop( Context.ARRAY );
	}
	
	void pushArray() {
		contexts.add( Context.ARRAY );
	}
	
	void popObject() {
		pop( Context.OBJECT );
	}
	
	void pushObject() {
		contexts.add( Context.OBJECT );
	}
	
}
