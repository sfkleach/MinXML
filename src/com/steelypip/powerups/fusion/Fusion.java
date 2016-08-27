package com.steelypip.powerups.fusion;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.common.StdIndenter;
import com.steelypip.powerups.fusion.io.FusionWriter;
import com.steelypip.powerups.fusion.io.FusionXmlElementTheme;
import com.steelypip.powerups.fusion.io.JSONTheme;
import com.steelypip.powerups.hydra.Hydra;
public interface Fusion extends Hydra< String, String, String, Fusion >, JSONFeatures {

	/**
	 * Returns an unfrozen copy of an element that shares the children.
	 * @return an unfrozen copy
	 */
	default Fusion shallowCopy() {
		final Fusion f = new FlexiFusion( this.getInternedName() );
		for ( Map.Entry< String, String > a : this.attributesToIterable() ) {
			f.addValue( a.getKey(), a.getValue() );
		}
		for ( Map.Entry< String, Fusion > a : this.fieldsIterable() ) {
			f.addChild( a.getKey(), a.getValue() );
		}
		return f;
	}
	
	/**
	 * A programmer directed promise that no further modifications will be
	 * made to this object. The implementation is encouraged but not required
	 * to detect any further modifications. In the event that a broken promise
	 * is detected, the effect will be a thrown UnsupportedOperationException.
	 */
	void freeze();
	
	/**
	 * Returns an frozen copy of an element that shares the children.
	 * @return a copy that has had freeze applied.
	 */
	default Fusion frozenCopy() {
		final Fusion f = this.shallowCopy();
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
		FusionWriter fw = new FusionWriter( pw );
		for ( String c : options ) {
			switch ( c ) {
			case "--pretty":
				fw.setIndenterFactory( new StdIndenter.Factory() );
				break;
			case "--plain":
				fw.setIndenterFactory( new NullIndenter.Factory() );
				break;
			case "--json":
				fw.setTheme( new JSONTheme().compose( new FusionXmlElementTheme() ) );
				break;
			case "--element":
				fw.setTheme( new FusionXmlElementTheme() );
				break;
			default:
				throw new RuntimeException( "Unknown option" );
			}
		}
		fw.print( this );
	}

//	default void print( PrintWriter pw ) {
//		new FusionWriter( pw ).print( this );
//	}
	
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
		new FusionWriter( pw, new StdIndenter.Factory(), new FusionXmlElementTheme() ).print( this );
	}
	
	/**
	 * Renders the element using the supplied {@link java.io.Writer} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	default void prettyPrint( Writer w ) {
		new FusionWriter( new PrintWriter( w, true ), new StdIndenter.Factory(), new FusionXmlElementTheme() ).print( this );
	}


}