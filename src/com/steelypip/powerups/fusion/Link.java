package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.Pair;

public interface Link< Field extends Comparable< Field >, Child > extends Pair< @NonNull Field, @NonNull Child > {
	
	@NonNull Field getField();
	int getFieldIndex();
	@NonNull Child getChild();

	default @NonNull Field getFirst() {
		return this.getField();
	}
	
	default @NonNull Child getSecond() {
		return this.getChild();
	}
	
	default void setFirst( @NonNull Field x ) {
		throw new UnsupportedOperationException();
	}
	
	default void setSecond( @NonNull Child x ) {
		throw new UnsupportedOperationException();
	}
}

