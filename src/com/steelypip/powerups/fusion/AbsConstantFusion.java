package com.steelypip.powerups.fusion;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.CmpPair;
import com.steelypip.powerups.common.EmptyIterator;
import com.steelypip.powerups.common.EmptyList;
import com.steelypip.powerups.common.EmptyMap;
import com.steelypip.powerups.common.EmptySet;
import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.StarMap;
import com.steelypip.powerups.util.StdStarMap;

public abstract class AbsConstantFusion extends LiteralConstantsFusion implements PartiallyMutableFusion {
	
	abstract protected @NonNull String internedType();
	
	abstract protected @NonNull String literalValue();
	
	abstract protected void setValueAttribute( final String new_value );

	@Override
	public @NonNull Fusion deepCopy() {
		return this.shallowCopy();
	}

	@Override
	public @NonNull String getName() {
		return this.constConstant();
	}

	@Override
	public @NonNull String getInternedName() {
		return this.constConstant();
	}

	@Override
	public boolean hasName( @Nullable String name ) {
		return this.constConstant().equals( name );
	}

	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull String getValue( @NonNull String key ) throws IllegalArgumentException {
		if ( this.constType().equals( key ) ) {
			return this.internedType();
		} else if ( this.constValue().equals( key ) ) {
			return this.literalValue();
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getValue( @NonNull String key, int index ) throws IllegalArgumentException {
		if ( this.constType().equals( key ) ) {
			if ( index == 0 ) return this.internedType();
		} else if ( this.constValue().equals( key ) ) {
			if ( index == 0 ) return this.literalValue();
		}
		throw new IllegalArgumentException();
	}

	@Override
	public @Nullable String getValue( @NonNull String key, @Nullable String otherwise ) {
		if ( this.constType().equals( key ) ) {
			return this.internedType();
		} else if ( this.constValue().equals( key ) ) {
			return this.literalValue();
		} else {
			return otherwise;
		}
	}

	@Override
	public @Nullable String getValue( @NonNull String key, int index, @Nullable String otherwise ) {
		if ( this.constType().equals( key ) ) {
			if ( index == 0 ) return this.internedType();
		} else if ( this.constValue().equals( key ) ) {
			if ( index == 0 ) return this.literalValue();
		}
		return otherwise;
	}

	@Override
	public void setValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		if ( this.constValue().equals( key ) ) {
			this.setValueAttribute( value );
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void setValue( @NonNull String key, int index, @NonNull String value ) throws IllegalArgumentException, UnsupportedOperationException {
		if ( this.constValue().equals( key ) && index == 0 ) {
			this.setValueAttribute( value );
			return;
		} 
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllValues( @NonNull String key, Iterable< @NonNull String > values ) throws UnsupportedOperationException {
		if ( this.constValue().equals( key ) ) {
			Iterator< @NonNull String > it = values.iterator();
			if ( it.hasNext() ) {
				String v = it.next();
				if ( ! it.hasNext() ) {
					this.setValueAttribute( v );
					return;
				}
			}
		}
		throw new UnsupportedOperationException();
	}

	@Override
	public void addValue( @NonNull String key, @NonNull String value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();		
	}

	@Override
	public void removeValue( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeValue( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAttributes( @NonNull String key ) throws UnsupportedOperationException {
		if ( key.equals( this.constType() ) || key.equals( this.constValue() ) ) {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoAttributes() {
		return false;
	}

	@Override
	public boolean hasAttribute( @NonNull String key ) {
		return key.equals( this.constType() ) || key.equals( this.constValue() );
	}

	@Override
	public boolean hasValueAt( @NonNull String key, int index ) {
		return index == 0 && this.hasAttribute( key );
	}

	@Override
	public boolean hasAttribute( @NonNull String key, @Nullable String value ) {
		if ( key.equals( this.constType() ) ) {
			return this.internedType().equals( value );
		} else if ( key.equals( this.constValue() ) ) {
			return this.literalValue().equals( value );
		} else {
			return false;
		}
	}

	@Override
	public boolean hasAttribute( @NonNull String key, int index, @Nullable String value ) {
		return index == 0 && this.hasAttribute(  key, value );
	}

	@Override
	public boolean hasOneValue( @NonNull String key ) {
		return this.hasAttribute( key );
	}

	@Override
	public int sizeAttributes() {
		return 2;
	}

	@Override
	public int sizeKeys() {
		return 2;
	}

	@Override
	public boolean hasNoKeys() {
		return false;
	}

	@Override
	public int sizeValues( @NonNull String key ) {
		return this.hasAttribute( key ) ? 1 : 0;
	}

	@Override
	public boolean hasSizeValues( @NonNull String key, int n ) {
		return ( n == 0 || n == 1 ) && ( this.hasAttribute( key ) ? n == 1 : n == 0 );
	}

	@Override
	public boolean hasNoValues( @NonNull String key ) {
		return ! this.hasAttribute( key );
	}

	@Override
	public @NonNull Set< @NonNull String > keysToSet() {
		final @NonNull TreeSet< @NonNull String > result = new TreeSet<>();
		result.add( this.constType());
		result.add( this.constValue() );
		return result;
	}

	@Override
	public @NonNull List< Attr > attributesToList() {
		final List< Attr > attrs = new LinkedList<>();
		attrs.add( new FlexiFusion.Attr( this.constType(), 0, this.internedType() ) );
		attrs.add( new FlexiFusion.Attr( this.constValue(), 0, this.literalValue() ) );
		return attrs;
	}

	@Override
	public @NonNull List< @NonNull String > valuesToList( @NonNull String key ) {
		if ( this.hasAttribute( key ) ) {
			List< @NonNull String > values = new LinkedList<>();
			values.add( this.getValue( key ) );
			return values;
		} else {
			return new EmptyList<>();
		}
	}

	@Override
	public @NonNull Map< @NonNull String, String > firstValuesToMap() {
		final TreeMap< @NonNull String, String > m = new TreeMap<>();
		m.put( this.constType(), this.internedType() );
		m.put( this.constValue(), this.literalValue() );
		return m;
	}

	@Override
	public @NonNull StarMap< @NonNull String, @Nullable String > attributesToStarMap() {
		final @NonNull StarMap< @NonNull String, @Nullable String > m = new StdStarMap<>();
		m.add( this.constType(), this.internedType() );
		m.add( this.constValue(), this.literalValue() );
		return m;
	}

	@Override
	public @NonNull Map< Pair< @NonNull String, @NonNull Integer >, String > attributesToPairMap() {
		final TreeMap< Pair< @NonNull String, @NonNull Integer >, String > m = new TreeMap<>();
		m.put( new CmpPair< @NonNull String, @NonNull Integer >( this.constType(), 0 ), this.internedType() );
		m.put( new CmpPair< @NonNull String, @NonNull Integer >( this.constValue(), 0 ) , this.literalValue() );
		return m;
	}

	@Override
	public Fusion getChild() throws IllegalArgumentException {
		return null;
	}

	@Override
	public Fusion getChild( int index ) throws IllegalArgumentException {
		return null;
	}

	@Override
	public @NonNull Fusion getChild( @NonNull String field ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public @NonNull Fusion getChild( @NonNull String field, int index ) throws IllegalArgumentException {
		throw new IllegalArgumentException();
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, @Nullable Fusion otherwise ) {
		return otherwise;
	}

	@Override
	public @Nullable Fusion getChild( @NonNull String field, int index, @Nullable Fusion otherwise ) {
		return otherwise;
	}

	@Override
	public void setChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setChild( @NonNull String field, int index, @NonNull Fusion value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAllChildren( @NonNull String field, Iterable< @NonNull Fusion > values ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( @NonNull String field, @NonNull Fusion value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addChild( @NonNull Fusion value ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( @NonNull String key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeChild( @NonNull String key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearLinks( @NonNull String key ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasNoLinks() {
		return true;
	}

	@Override
	public boolean hasLink( @NonNull String field ) {
		return false;
	}

	@Override
	public boolean hasLink( @NonNull String field, int index ) {
		return false;
	}

	@Override
	public boolean hasLink( @NonNull String field, @Nullable Fusion value ) {
		return false;
	}

	@Override
	public boolean hasLink( @NonNull String field, int index, @Nullable Fusion value ) {
		return false;
	}

	@Override
	public boolean hasOneChild( @NonNull String field ) {
		return false;
	}

	@Override
	public int sizeLinks() {
		return 0;
	}

	@Override
	public int sizeFields() {
		return 0;
	}

	@Override
	public boolean hasNoFields() {
		return true;
	}

	@Override
	public int sizeChildren( @NonNull String field ) {
		return 0;
	}

	@Override
	public boolean hasSizeChildren( @NonNull String field, int n ) {
		return n == 0;
	}

	@Override
	public boolean hasNoChildren( @NonNull String field ) {
		return true;
	}

	@Override
	public @NonNull Set< @NonNull String > fieldsToSet() {
		return new EmptySet< @NonNull String >();
	}

	@Override
	public @NonNull List< Link > linksToList() {
		return new EmptyList< Link >();
	}

	@Override
	public @NonNull List< @NonNull Fusion > childrenToList( @NonNull String field ) {
		return new EmptyList< @NonNull Fusion >();
	}

	@Override
	public Map< @NonNull String, Fusion > firstChildrenToMap() {
		return new EmptyMap< @NonNull String,@Nullable Fusion >();
	}

	@Override
	public StarMap< @NonNull String, ? extends @NonNull Fusion > linksToStarMap() {
		return null;
	}

	@Override
	public Map< Pair< @NonNull String, @NonNull Integer >, ? extends Fusion > linksToPairMap() {
		return new EmptyMap<>();
	}

	@Override
	public Iterator< Link > iterator() {
		return new EmptyIterator< Link >();
	}
	
}
