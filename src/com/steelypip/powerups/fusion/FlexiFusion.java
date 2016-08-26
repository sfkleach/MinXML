package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.FlexiHydra;

/** 
 * An all-round implementation of a mutable Fusion class that implements all methods
 * at reasonable cost and at reasonable performance. 
 */
public class FlexiFusion extends FlexiHydra< String, String, String, Fusion > implements Fusion, StdJSONFeatures {

	public FlexiFusion( @NonNull String name ) {
		super( name );
	}

	public FlexiFusion( Fusion fusion ) {
		this( fusion.getInternedName() );
		this.attributes = this.attributes.addAllEntries( fusion.attributesToList() );
		this.links = this.links.addAllEntries( fusion.linksToList() );
	}
	

}
