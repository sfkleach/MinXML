package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.Hydra;

public interface Fusion extends Hydra< String, String, String, Fusion >, JSONFeatures {

	@Override
	default @NonNull String defaultField() {
		return "";
	}
	
}