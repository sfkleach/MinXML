package com.steelypip.powerups.fusion;

import java.io.PrintWriter;
import java.io.Writer;

import com.steelypip.powerups.common.NullIndenter;
import com.steelypip.powerups.common.StdIndenter;
import com.steelypip.powerups.fusion.io.FusionWriter;
import com.steelypip.powerups.fusion.io.JSONTheme;
import com.steelypip.powerups.fusion.io.XmlElementTheme;
import com.steelypip.powerups.hydra.Hydra;

public interface Fusion extends Hydra< String, String, String, Fusion >, JSONFeatures {

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
				fw.setTheme( new JSONTheme().compose( new XmlElementTheme() ) );
				break;
			case "--element":
				fw.setTheme( new XmlElementTheme() );
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
		new FusionWriter( pw, new StdIndenter.Factory(), new XmlElementTheme() ).print( this );
	}
	
	/**
	 * Renders the element using the supplied {@link java.io.Writer} such that each start and 
	 * end tag are on their own line and the children indented. The output always finishes
	 * with a newline.
	 * 
	 * @param w the {@link Writer} to use.
	 */
	default void prettyPrint( Writer w ) {
		new FusionWriter( new PrintWriter( w, true ), new StdIndenter.Factory(), new XmlElementTheme() ).print( this );
	}


}