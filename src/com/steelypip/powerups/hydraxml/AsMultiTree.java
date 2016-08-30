package com.steelypip.powerups.hydraxml;

import java.util.List;
import java.util.Map;

public interface AsMultiTree<Key, Value > {
	
	public abstract Iterable< Map.Entry< Key, Value > > entries( Value v );
	public abstract Key defaultKey();
	public abstract Value badValue();
	public abstract List< Map.Entry< Key, Value > > linksToList( Value v );

}
