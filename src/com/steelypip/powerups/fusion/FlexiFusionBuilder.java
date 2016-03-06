package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public class FlexiFusionBuilder extends AbsFusionBuilder {
	
	private @NonNull FusionFactory _factory = new FlexiFusionFactory();
	
	@Override
	public @NonNull FusionFactory factory() {
		return this._factory;
	}

	
}
