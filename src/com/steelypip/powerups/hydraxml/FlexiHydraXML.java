package com.steelypip.powerups.hydraxml;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.FlexiHydra;

/** 
 * An all-round implementation of a mutable Fusion class that implements all methods
 * at reasonable cost and at reasonable performance. 
 */
public class FlexiHydraXML extends FlexiHydra< String, String, String, HydraXML > implements HydraXML {

	public FlexiHydraXML( @NonNull String name ) {
		super( name );
	}

	public FlexiHydraXML( HydraXML hx ) {
		this( hx.getInternedName() );
		this.attributes = this.attributes.addAllEntries( hx.attributesToList() );
		this.links = this.links.addAllEntries( hx.linksToList() );
	}
	

}
