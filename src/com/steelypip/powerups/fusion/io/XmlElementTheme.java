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
	public void doStartElement( FusionWriter fwriter, String name, boolean hasAttributes, boolean hasLinks ) {
		fwriter.print( '<' );
	}

	@Override
	public void doName( FusionWriter fwriter, String name ) {
		fwriter.print( name );
	}

	@Override
	public void doStartAttributes( FusionWriter fwriter, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartAttributeGroup( FusionWriter fwriter, String key ) {
	}

	@Override
	public void doAttribute( FusionWriter fwriter, String key, String value, boolean first_in_group, boolean last_in_group ) {
		fwriter.print( ' ' );
		fwriter.print( key );
		fwriter.print( first_in_group ? "=" : "+=" );
		fwriter.print( '"' );
		fwriter.renderString( value );
		fwriter.print( '"' );
	}

	@Override
	public void doEndAttributeGroup( FusionWriter fwriter, String key ) {
	}

	@Override
	public void doEndAttributes( FusionWriter fwriter, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			fwriter.print( '>' );
		} else {
			fwriter.print( "/>" );
		}
	}

	@Override
	public void doStartLinks( FusionWriter fwriter, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartLinkGroup( FusionWriter starw, String field ) {
	}

	@Override
	public void doLink( FusionWriter fwriter, String field, Fusion child, boolean first_in_group, boolean last_in_group ) {
		if ( ! field.isEmpty() ) {
			fwriter.print( field );
			fwriter.print( first_in_group ? ":" : "+:" );
		}
		fwriter.print( child );
	}


	@Override
	public void doEndLinkGroup( FusionWriter fwriter, String field ) {
	}

	@Override
	public void doEndLinks( FusionWriter fwriter, boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doEndElement( FusionWriter fwriter, String name, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			fwriter.print( "</" );
			fwriter.print( name );
			fwriter.print( ">" );
		}
	}

}
