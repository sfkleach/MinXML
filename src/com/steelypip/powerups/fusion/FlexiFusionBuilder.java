package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public class FlexiFusionBuilder extends AbsFusionBuilder {
	
	private FusionImplementation factory;
	
	@Override
	public @NonNull FusionImplementation implementation() {
		FusionImplementation fi = this.factory;
		if ( fi == null ) {
			fi = this.factory = new FlexiFusionImplementation();
		}
		return fi;
	}

	
}
