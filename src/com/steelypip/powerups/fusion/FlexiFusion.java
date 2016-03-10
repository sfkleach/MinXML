package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.OldFlexiHydra;

public class FlexiFusion extends OldFlexiHydra< String, String, String, Fusion > implements Fusion, StdJSONFeatures {

	public FlexiFusion( @NonNull String name ) {
		super( name );
	}

}
