package com.steelypip.powerups.fusion.io;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.fusion.Fusion;

/**
 * Elements have three
 * parts: name, attributes and links. The general pattern they print 
 * to is:
 * 
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
 */

public abstract class ElementTheme implements Theme {
	
	@Override
	public boolean tryRender( FusionWriter fwriter, Fusion x ) {
		
		final String name = x.getName();
		final boolean has_any_attributes = x.hasAnyAttributes();
		final boolean has_any_links = x.hasAnyLinks();
		
		fwriter.getIndenter().indent();
		this.doStartElement( fwriter, name, has_any_attributes, has_any_links );
		this.doName( fwriter, name );
		
		this.doStartAttributes( fwriter, has_any_attributes, has_any_links );
		for ( @NonNull String key : x.keysToSet() ) {
			this.doStartAttributeGroup( fwriter, key );
			final List< @NonNull String > values = x.valuesToList( key );
			int n = 0;
			for ( @NonNull String value : values ) {
				n += 1;
				this.doAttribute( fwriter, key, value,  n == 1, n == values.size() );
			}
			this.doEndAttributeGroup( fwriter, key );
		}
		this.doEndAttributes( fwriter, has_any_attributes, has_any_links );
		
		this.doStartLinks( fwriter, has_any_attributes, has_any_links );
		for ( @NonNull String field : x.fieldsToSet() ) {
			final @NonNull List< @NonNull Fusion > children = x.childrenToList( field );
			this.doStartLinkGroup( fwriter, field );
			int n = 0;
			for ( @NonNull Fusion child : children) {
				n += 1;
				this.doLink( fwriter, field, child, n == 1, n == children.size() );
			}
			this.doEndLinkGroup( fwriter, field );
		}
		this.doEndLinks( fwriter, has_any_attributes, has_any_links ); 
		
		this.doEndElement( fwriter, name, has_any_attributes, has_any_links );
		
		return true;
	}
		
	public abstract void doStartElement( FusionWriter starw, String name, boolean hasAttributes, boolean hasLinks );
	public abstract void doName( FusionWriter starw, String name );
	public abstract void doStartAttributes( FusionWriter starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doStartAttributeGroup( FusionWriter starw, String key );
	public abstract void doAttribute( FusionWriter starw, String key, String value, boolean first_in_group, boolean last_in_group );
	public abstract void doEndAttributeGroup( FusionWriter starw, String key );
	public abstract void doEndAttributes( FusionWriter starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doStartLinks( FusionWriter starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doStartLinkGroup( FusionWriter starw, String field );
	public abstract void doLink( FusionWriter starw, String field, Fusion child, boolean first_in_group, boolean last_in_group );
	public abstract void doEndLinkGroup( FusionWriter starw, String field );
	public abstract void doEndLinks( FusionWriter starw, boolean hasAttributes, boolean hasLinks );
	public abstract void doEndElement( FusionWriter starw, String name, boolean hasAttributes, boolean hasLinks );

	public abstract static class Selector {
		
		public abstract static class Factory {
			public abstract Selector newInstance( FusionWriter w );
		}
		
		public abstract ElementTheme select( Fusion element );
		
		public Selector compose( final Selector alternative ) {
			return(
				new Selector() {
				
					final Selector first_choice = this;

					@Override
					public ElementTheme select( Fusion element ) {
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
