package com.steelypip.powerups.minxmlstar;

import java.io.PrintWriter;

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
	
	final PrintWriter pw;

	public XmlElementTheme( MinXMLStarWriter starw ) {
		super( starw );
		this.pw = starw.getPrintWriter();
	}
	
	public static class Selector extends ElementTheme.Selector {
		
		public static class Factory extends ElementTheme.Selector.Factory {
			public Selector newInstance( MinXMLStarWriter w ) {
				return new Selector( w );
			}
		}

		private MinXMLStarWriter writer;
		
		public Selector( MinXMLStarWriter writer ) {
			super();
			this.writer = writer;
		}

		@Override
		public ElementTheme select( MinXMLStar element ) {
			// TODO Auto-generated method stub
			return new XmlElementTheme( writer );
		}
		
	}

	@Override
	public void doStartElement( String name, boolean hasAttributes, boolean hasLinks ) {
		this.pw.print( '<' );
	}

	@Override
	public void doName( String name ) {
		this.pw.print( name );
	}

	@Override
	public void doStartAttributes( boolean hasAttributes, boolean hasLinks ) {
		if ( hasAttributes ) {
			this.pw.print( ' ' );
		}
	}

	@Override
	public void doStartAttributeGroup( String key ) {
	}

	@Override
	public void doAttribute( String key, String value, boolean first_in_group, boolean last_in_group ) {
		this.pw.print( key );
		this.pw.print( first_in_group ? "=" : "+=" );
		this.pw.print( '"' );
		this.starw.renderString( value );
		this.pw.print( '"' );
	}

	@Override
	public void doEndAttributeGroup( String key ) {
	}

	@Override
	public void doEndAttributes( boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			this.pw.print( '>' );
		} else {
			this.pw.print( "/>" );
		}
	}

	@Override
	public void doStartLinks( boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doStartLinkGroup( String field ) {
	}

	@Override
	public void doLink( String field, MinXMLStar child, boolean first_in_group, boolean last_in_group ) {
		this.pw.print( field );
		this.pw.print( first_in_group ? ":" : "+:" );
		this.starw.print( child );
	}


	@Override
	public void doEndLinkGroup( String field ) {
	}

	@Override
	public void doEndLinks( boolean hasAttributes, boolean hasLinks ) {
	}

	@Override
	public void doEndElement( String name, boolean hasAttributes, boolean hasLinks ) {
		if ( hasLinks ) {
			this.pw.print( "</" );
			this.pw.print( name );
			this.pw.print( ">" );
		}
	}

}
