package com.steelypip.powerups.hydraxml;

import java.util.List;
import java.util.Map.Entry;

public interface HydraXMLAsMultiTree {

	default Iterable< Entry< String, HydraXML > > entries( HydraXML v ) {
		return v;
	}

	default String defaultKey() {
		return "";
	}
	
	static HydraXML bad_value = new BadHydraXML();

	default HydraXML badValue() {
		return bad_value;
	}

	default List< Entry< String, HydraXML > > linksToList( HydraXML v ) {
		return v.linksToList();
	}	
	
}
