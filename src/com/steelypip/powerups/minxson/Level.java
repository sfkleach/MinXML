package com.steelypip.powerups.minxson;

import java.security.InvalidParameterException;

class Level {
	
	private String tag;
	private char expected;	//	If a particular character is expected, '\0' if not.
	private boolean local_newline_extension;
	private Context context;
	
	public Level( final String tag, final char expected, final Context context ) {
		super();
		if ( tag == null ) throw new InvalidParameterException();
		this.tag = tag;
		this.expected = expected;
		this.context = context;
	}
	
	public Level setNewlineTerminator( final boolean flag ) {
		this.local_newline_extension = flag;
		return this;
	}
	
	public boolean isInObject() { return this.context == Context.InObject || this.context == Context.InEmbeddedObject; }
	public boolean isInElement() { return this.context == Context.InElement; }
	public boolean isInParentheses() { return this.context == Context.InEmbeddedParentheses; }
	public boolean isInEmbeddedContainer() { return this.context == Context.InEmbeddedArray || this.context == Context.InEmbeddedObject; }
	public boolean hasTag( final String t ) { return this.tag.equals( t ); }
	public boolean hasntTag( final String t ) { return ! this.tag.equals( t ); }
	public String getTag() { return this.tag; }
	public char getExpected() { return this.expected; }
	public boolean hasExpected( char ch ) { return this.expected != '\0'; }
	public boolean hasntExpected( char ch ) { return this.expected == '\0'; }
	public boolean isNewlineTerminator() { return this.local_newline_extension; }
	
}