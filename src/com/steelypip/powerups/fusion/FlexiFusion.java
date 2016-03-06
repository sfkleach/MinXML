package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.FlexiHydra;

public class FlexiFusion extends FlexiHydra< String, String, String, Fusion > implements Fusion, StdJSONFeatures {

	public FlexiFusion( @NonNull String name ) {
		super( name );
	}



}
