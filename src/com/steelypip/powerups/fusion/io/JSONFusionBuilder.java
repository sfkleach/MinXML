package com.steelypip.powerups.fusion.io;

import com.steelypip.powerups.fusion.AbsFusionBuilder;
import com.steelypip.powerups.fusion.FusionFactory;

public class JSONFusionBuilder extends AbsFusionBuilder {

	private FusionFactory _factory;
	
	@Override
	public FusionFactory factory() {
		if ( this._factory == null ) {
			return this._factory = new JSONFusionFactory( true );
		} else {
			return this._factory;
		}
	}
	
}