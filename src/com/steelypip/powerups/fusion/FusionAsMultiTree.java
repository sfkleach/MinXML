package com.steelypip.powerups.fusion;

import java.util.List;
import java.util.Map.Entry;

public interface FusionAsMultiTree {

		default Iterable< Entry< String, Fusion > > entries( Fusion v ) {
			return v;
		}

		default String defaultKey() {
			return "";
		}
		
		static Fusion bad_value = new BadFusion();

		default Fusion badValue() {
			return bad_value;
		}

		default List< Entry< String, Fusion > > linksToList( Fusion v ) {
			return v.linksToList();
		}	
		
}
