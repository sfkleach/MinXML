package com.steelypip.powerups.hydra;

import org.eclipse.jdt.annotation.NonNull;

public class StdAttribute< K extends Comparable< K >, V > implements Attribute< K, V > {

	private @NonNull K key;
	private int keyIndex;
	private @NonNull V value;
	
	public StdAttribute( @NonNull K key, int keyIndex, @NonNull V value ) {
		super();
		this.key = key;
		this.keyIndex = keyIndex;
		this.value = value;
	}

	public @NonNull K getKey() {
		return key;
	}

	public int getKeyIndex() {
		return keyIndex;
	}

	public @NonNull V getValue() {
		return value;
	}
	
}
