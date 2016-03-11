package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.Hydra;

public interface Fusion extends Hydra< String, String, String, Fusion >, JSONFeatures {

	default String defaultField() {
		return "";
	}
	
}