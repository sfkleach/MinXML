package com.steelypip.powerups.fusion;

public interface Theme {
	
	boolean tryRender( FusionWriter w, Fusion x );
	
	default Theme compose( Theme alternative ) {
		return
			new Theme() {

				@Override
				public boolean tryRender( FusionWriter w, Fusion x ) {
					return this.tryRender( w, x ) || alternative.tryRender( w, x );
				}
				
			};
	}

}
