package com.steelypip.powerups.hydra;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.Pair;

public interface Attribute< Key extends Comparable< Key >, Value > extends Pair< @NonNull Key, @NonNull Value > {
	
	@NonNull Key getKey();
	int getKeyIndex();
	@NonNull Value getValue();
	
	default @NonNull Key getFirst() {
		return this.getKey();
	}
	
	default @NonNull Value getSecond() {
		return this.getValue();
	}
	
	default void setFirst( @NonNull Key x ) {
		throw new UnsupportedOperationException();
	}
	
	default void setSecond(@NonNull Value x ) {
		throw new UnsupportedOperationException();
	}
	
}
