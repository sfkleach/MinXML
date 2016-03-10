package com.steelypip.powerups.fusion.io;

import com.steelypip.powerups.fusion.Fusion;

/**
 * 		<		doStartElement( String name, boolean hasAttributes, boolean hasLinks )
 *		NAME	doName( String name )
 *				doStartAttributes( boolean hasAttributes, boolean hasLinks )
 *				doStartAttributeGroup( String key )
 *				doStartAttribute( String key, String Value )
 *		KEY		doKey( String key )
 *		=|+=	doAttributeBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doValue( String value )
 *		...		// repeat Key/Binding/Value
 *				doEndAttribute( String key, String value )
 *				doEndAttributeGroup( String key )
 *		...		// repeat
 *		>		doEndAttributes( boolean hasAttributes, boolean hasLinks )
 *				doStartLinks( boolean hasAttributes, boolean hasLinks )
 *				doStartLinkGroup( String field )
 *		FIELD	doField( String field )
 *		:		doLinkBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doChild( MinXMLStar child )
 *		...		//	 repeat previous 3 steps
 *				doEndLinkGroup( String field )
 *		...		// repeat
 *				doEndLinks( boolean hasAttributes, boolean hasLinks )
 *		</NAME>	doEndElement( String name, boolean hasAttributes, boolean hasLinks )
 *
 */
public class XmlElementTheme extends ElementTheme {
	
	final static XmlElementTheme INSTANCE = new XmlElementTheme();
	
			
	@Override
	public void doStartElement( FusionWriter starw, String name, boolean hasAttributes, boolean hasLinks ) {
		starw.print( '<' );
	}

	@Override
	public void doName( FusionWriter starw, String name ) {
		starw.print( name );
	}

	@Override
	public void doStartAttributes( FusionWriter starw, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartAttributeGroup( FusionWriter starw, String key ) {
	}

	@Override
	public void doAttribute( FusionWriter starw, String key, String value, boolean first_in_group, boolean last_in_group ) {
		starw.print( ' ' );
		starw.print( key );
		starw.print( first_in_group ? "=" : "+=" );
		starw.print( '"' );
		starw.renderString( value );
		starw.print( '"' );
	}

	@Override
	public void doEndAttributeGroup( FusionWriter starw, String key ) {
	}

	@Override
	public void doEndAttributes( FusionWriter starw, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			starw.print( '>' );
		} else {
			starw.print( "/>" );
		}
	}

	@Override
	public void doStartLinks( FusionWriter starw, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartLinkGroup( FusionWriter starw, String field ) {
	}

	@Override
	public void doLink( FusionWriter starw, String field, Fusion child, boolean first_in_group, boolean last_in_group ) {
		if ( ! field.isEmpty() ) {
			starw.print( field );
			starw.print( first_in_group ? ":" : "+:" );
		}
		starw.print( child );
	}


	@Override
	public void doEndLinkGroup( FusionWriter starw, String field ) {
	}

	@Override
	public void doEndLinks( FusionWriter starw, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doEndElement( FusionWriter starw, String name, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			starw.print( "</" );
			starw.print( name );
			starw.print( ">" );
		}
	}

}
