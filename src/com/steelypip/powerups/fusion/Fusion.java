package com.steelypip.powerups.fusion;

import com.steelypip.powerups.hydra.Hydra;

public interface Fusion extends Hydra< String, String, String, Fusion >, JSONFeatures {

	@Override
	default String defaultField() {
		return "";
	}
	
}
