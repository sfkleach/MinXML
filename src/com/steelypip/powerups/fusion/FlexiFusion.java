package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.FlexiHydra2;
//import com.steelypip.powerups.hydra.Hydra;

public class FlexiFusion extends FlexiHydra2< String, String, String, Fusion > implements Fusion, StdJSONFeatures {

	public FlexiFusion( @NonNull String name ) {
		super( name );
	}

}
