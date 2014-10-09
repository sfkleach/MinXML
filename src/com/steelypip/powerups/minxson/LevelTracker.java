package com.steelypip.powerups.minxson;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;

import com.steelypip.powerups.alert.Alert;

class LevelTracker {
	boolean pending_end_tag = false;
	ArrayDeque< Level > open_tags = new ArrayDeque< Level >();
	
	boolean hasPendingTag() {
		return this.pending_end_tag;
	}

	boolean isAtTopLevel() {
		return this.open_tags.isEmpty();
	}

	boolean isntAtTopLevel() {
		return ! this.open_tags.isEmpty();
	}

	boolean hasExpected( char ch ) {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().hasExpected( ch );
	}

	Level topLevel() {
		try {
			return this.open_tags.getLast();
		} catch ( NoSuchElementException e ) {
			throw new Alert( "Internal error", e );
		}
	}
	
	void dropTag() {
		try {
			this.open_tags.removeLast();	
			this.pending_end_tag = false;
		} catch ( NoSuchElementException e ) {
			throw new Alert( "Internal error", e );
		}
	}

	String popTag( char actual ) {
		if ( actual != '\0' && this.topLevel().hasntExpected( actual ) ) {
			throw (
				new Alert( "Mismatched closing character" ).
				culprit( "Expected", this.topLevel().getExpected() ).
				culprit( "Actually", actual )
			);
		}
		return this.popTag();
	}

	String popTag() {
		final String t = this.topLevel().getTag();
		this.dropTag();
		return t;
	}

	void mustPopTag( String tag ) {
		if ( this.topLevel().hasntTag( tag ) ) {
			throw new Alert( "Closing tag does not match open tag" ).culprit( "Open tag was", this.open_tags.getLast().getTag() ).culprit( "Closing tag is", tag );
		}
		this.dropTag();		
	}

	void pushPendingTag( final String tag, final char expected, final Context context ) {
		this.open_tags.addLast( new Level( tag, expected, context ) );
		this.pending_end_tag = true;
	}

	void pushTag( final String tag, final char expected, final Context context ) {
		this.pushTag( new Level( tag, expected, context ) );
	}

	void pushTag( final Level level ) {
		this.open_tags.addLast( level );
		this.pending_end_tag = false;
	}

	boolean isInObject() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInObject();
	}

	boolean isInElement() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInElement();
	}

	boolean isInParentheses() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInParentheses();
	}

	boolean isInEmbeddedContainer() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isInEmbeddedContainer();
	}
	
	boolean isNewlineTerminator() {
		return ! this.open_tags.isEmpty() && this.open_tags.getLast().isNewlineTerminator();
	}
	
}