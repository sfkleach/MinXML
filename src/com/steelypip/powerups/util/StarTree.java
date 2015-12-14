package com.steelypip.powerups.util;

import java.util.List;
import java.util.Map;

/**
 * A StarTree<T> is a tree of T's, where the children are
 * indexed by both a textual label and positionally. The 
 * labels are considered to be unordered but the children
 * that share a label are ordered sequentially.
 * 
 * @author steve
 *
 */
public interface StarTree< T > extends Map< String, List< StarTree< T > > > {
}
