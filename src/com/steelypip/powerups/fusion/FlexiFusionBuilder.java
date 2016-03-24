package com.steelypip.powerups.fusion;

public class FlexiFusionBuilder extends AbsFusionBuilder {
	
	private static FusionFactory DEFAULT_FACTORY = new FlexiFusionFactory();
	private FusionFactory _factory; 
	
	@Override
	public FusionFactory factory() {
		if ( this._factory == null ) {
			this._factory = DEFAULT_FACTORY;
		}
		return this._factory;
	}

	
}
