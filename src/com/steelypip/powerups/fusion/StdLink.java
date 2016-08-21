package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public class StdLink< Field extends Comparable< Field >, ChildValue > implements Link< Field, ChildValue > {
	
	@NonNull Field field;
	int fieldIndex;
	@NonNull ChildValue child;
	
	public StdLink( @NonNull Field field, int fieldIndex, @NonNull ChildValue child ) {
		super();
		this.field = field;
		this.fieldIndex = fieldIndex;
		this.child = child;
	}

	public @NonNull Field getField() {
		return field;
	}

	public int getFieldIndex() {
		return fieldIndex;
	}

	public @NonNull ChildValue getChild() {
		return child;
	}
	
}
