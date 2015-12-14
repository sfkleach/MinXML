package com.steelypip.powerups.minxmlstar;


/**
 * Elements have three
 * parts: name, attributes and links. The general pattern they print 
 * to is:
 * 
 * 		<		doStartElement( String name, boolean hasAttributes, boolean hasLinks )
 *		NAME	doName( String name )
 *				doStartAttributes( boolean hasAttributes )
 *				doStartAttributeGroup( String key )
 *				doStartAttribute( String key, String Value )
 *		KEY		doKey( String key )
 *		=|+=	doAttributeBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doValue( String value )
 *		...		// repeat Key/Binding/Value
 *				doEndAttribute( String key, String value )
 *				doEndAttributeGroup( String key )
 *		...		// repeat
 *		>		doEndAttributes( boolean hasAttributes )
 *				doStartLinks( boolean hasLinks )
 *				doStartLinkGroup( String field )
 *		FIELD	doField( String field )
 *		:		doLinkBinding( boolean first_in_group, boolean last_in_group )
 *		VALUE	doChild( MinXMLStar child )
 *		...		//	 repeat previous 3 steps
 *				doEndLinkGroup( String field )
 *		...		// repeat
 *				doEndLinks( boolean hasLinks )
 *		</NAME>	doEndElement( String name, boolean hasAttributes, boolean hasLinks )
 */

public abstract class ElementTheme {
	
	protected final MinXMLStarWriter starw;

	public ElementTheme( MinXMLStarWriter starw ) {
		super();
		this.starw = starw;
	}
		
	public abstract void doStartElement( String name, boolean hasAttributes, boolean hasLinks );
	public abstract void doName( String name );
	public abstract void doStartAttributes( boolean hasAttributes );
	public abstract void doStartAttributeGroup( String key );
	public abstract void doStartAttribute( String key, String Value );
	public abstract void doKey( String key );
	public abstract void doAttributeBinding( boolean first_in_group, boolean last_in_group );
	public abstract void doValue( String value );
	public abstract void doEndAttribute( String key, String value );
	public abstract void doEndAttributeGroup( String key );
	public abstract void doEndAttributes( boolean hasAttributes );
	public abstract void doStartLinks( boolean hasLinks );
	public abstract void doStartLinkGroup( String field );
	public abstract void doField( String field );
	public abstract void doLinkBinding( boolean first_in_group, boolean last_in_group );
	public abstract void doChild( MinXMLStar child );
	public abstract void doEndLinkGroup( String field );
	public abstract void doEndLinks( boolean hasLinks );
	public abstract void doEndElement( String name, boolean hasAttributes, boolean hasLinks );

	public abstract static class Selector {
		
		public abstract static class Factory {
			public abstract Selector newInstance( MinXMLStarWriter w );
		}
		
		public abstract ElementTheme select( MinXMLStar element );
		
		public Selector compose( final Selector alternative ) {
			return(
				new Selector() {
				
					final Selector first_choice = this;

					@Override
					public ElementTheme select( MinXMLStar element ) {
						ElementTheme t = this.first_choice.select( element );
						if ( t == null ) {
							return t;
						} else {
							return alternative.select( element );
						}
					}
					
				}
			);
		}
	}
	
}
