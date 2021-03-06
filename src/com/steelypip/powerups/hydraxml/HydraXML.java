package com.steelypip.powerups.hydraxml;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.common.StdIndenter;
import com.steelypip.powerups.hydranode.HydraNode;

/**
 * HydraXML is a multi-valued extension of MinXML that, in its 
 * turn, is a cleaner, leaner, cut-down version of XML with only the
 * absolute essentials. An HydraXML object is a single node or 'element' that 
 * represents a multi-valued tree of elements, where each element is a named, multi-valued 
 * dictionary. The HydraXML interface defines a standard interface 
 * for all classes representing HydraXML elements.
 * 
 * HydraXML elements support freezing and unfreezing via freeze, shallowCopy
 * and frozenCopy. Freezing is a promise from the programmer that no
 * further updates will be applied; it is advisory insofar that the
 * implementation can ignore a freeze. 
 */
public interface HydraXML extends HydraNode< String, String, String, HydraXML > {

	/**
	 * Returns an unfrozen copy of an element that shares the children.
	 * @return an unfrozen copy
	 */
	default HydraXML shallowCopy() {
		final HydraXML f = new FlexiHydraXML( this.getInternedName() );
		for ( Map.Entry< String, String > a : this.attributesToIterable() ) {
			f.addValue( a.getKey(), a.getValue() );
		}
		for ( Map.Entry< String, HydraXML > a : this.linksToIterable() ) {
			f.addChild( a.getKey(), a.getValue() );
		}
		return f;
	}
	
	/**
	 * A programmer directed promise that no further modifications will be
	 * made to this object. The implementation is encouraged but not required
	 * to detect any further modifications. In the event that a broken promise
	 * is detected, the effect will be a thrown UnsupportedOperationException.
	 * 
	 * There is no unfreeze counterpart, in order that implementations are
	 * encouraged to take advantage of store sharing.
	 */
	void freeze();
	
	/**
	 * Returns an frozen copy of an element that shares the children of
	 * the original element.
	 * @return a copy that has had freeze applied.
	 */
	default HydraXML frozenCopy() {
		final HydraXML f = this.shallowCopy();
		f.freeze();
		return f;
	}
		
	default String defaultField() {
		return "";
	}
	
	/**
	 * Renders the element using the supplied {@link PrintWriter}. The rendering will
	 * not contain any newlines. This is the same as the string generated by toString(). 
	 * 
	 * @param pw the {@link PrintWriter} to use.
	 */
	default void print( PrintWriter pw, String ... options ) {
		HydraXMLWriter fw = new HydraXMLWriter( pw );
		for ( String c : options ) {
			switch ( c ) {
			case "--pretty":
				fw.setIndenterFactory( new StdIndenter.Factory() );
				break;
			case "--plain":
				fw.setIndenterFactory( new NullIndenter.Factory() );
				break;
			case "--element":
				fw.setTheme( new HydraXmlElementTheme() );
				break;
			default:
				throw new RuntimeException( "Unknown option" );
			}
		}
		fw.print( this );
	}

	
	/**
	 * Renders the element to the supplied {@link java.io.Writer}.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	default void print( Writer w, String ... options ) {
		this.print( new PrintWriter( w ), options );
	}
	
	/**
	 * Renders the element using the supplied {@link PrintWriter} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param pw the {@link PrintWriter} to use.
	 */
	default void prettyPrint( PrintWriter pw, String ... options ) {
		new HydraXMLWriter( pw, new StdIndenter.Factory(), new HydraXmlElementTheme() ).print( this );
	}
	
	/**
	 * Renders the element using the supplied {@link java.io.Writer} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	default void prettyPrint( Writer w ) {
		new HydraXMLWriter( new PrintWriter( w, true ), new StdIndenter.Factory(), new HydraXmlElementTheme() ).print( this );
	}


}