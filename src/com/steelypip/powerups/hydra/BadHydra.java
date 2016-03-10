package com.steelypip.powerups.hydra;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.Pair;
import com.steelypip.powerups.util.StarMap;

public class BadHydra< Key extends Comparable< Key >, Value, Field extends Comparable< Field >, Child  > implements Hydra< Key, Value, Field, Child > {

	@Override
	public @NonNull String getName() {
		throw new IllegalStateException();
	}

	@Override
	public @NonNull String getInternedName() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasName( @Nullable String name ) {
		throw new IllegalStateException();
	}

	@Override
	public void setName( @NonNull String x ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public Value getValue( Key key ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Value getValue( Key key, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Value getValue( Key key, Value otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public Value getValue( Key key, int index, Value otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setValue( Key key, Value value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setValue( Key key, int index, Value value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllValues( Key key, Iterable< Value > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addValue( Key key, Value value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void removeValue( Key key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void removeValue( Key key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAttributes( Key key ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAllAttributes() throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public int sizeAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoAttributes() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( Key key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasValueAt( Key key, int index ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( Key key, Value value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasAttribute( Key key, int index, Value value ) {
		throw new IllegalStateException();
	}

	@Override
	public int sizeKeys() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoKeys() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeValues( Key key ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasSizeValues( Key key, int n ) {
		throw new IllegalStateException();
	}

	@Override
	public Set< Key > keysToSet() {
		throw new IllegalStateException();
	}

	@Override
	public List< Attribute< Key, Value > > attributesToList() {
		throw new IllegalStateException();
	}

	@Override
	public List< Value > valuesToList( Key key ) {
		throw new IllegalStateException();
	}

	@Override
	public Map< Key, Value > firstValuesToMap() {
		throw new IllegalStateException();
	}

	@Override
	public StarMap< Key, Value > attributesToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map< Pair< Key, Integer >, Value > attributesToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public Field defaultField() {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild() throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( Field field ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( Field field, int index ) throws IllegalArgumentException {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( Field field, Child otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public Child getChild( Field field, int index, Child otherwise ) {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( Field field, Child value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setChild( Field field, int index, Child value ) throws IllegalArgumentException, UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void setAllChildren( Field field, Iterable< Child > values ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( Field field, Child value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void addChild( Child value ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void removeChild( Field key ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void removeChild( Field key, int index ) throws UnsupportedOperationException, IndexOutOfBoundsException {
		throw new IllegalStateException();
	}

	@Override
	public void clearLinks( Field key ) throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public void clearAllLinks() throws UnsupportedOperationException {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoLinks() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeLinks() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( Field field, int index ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( Field field, Child value ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasLink( Field field, int index, Child value ) {
		throw new IllegalStateException();
	}

	@Override
	public int sizeFields() {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoFields() {
		throw new IllegalStateException();
	}

	@Override
	public int sizeChildren( Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public boolean hasNoChildren( Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public Set< Field > fieldsToSet() {
		throw new IllegalStateException();
	}

	@Override
	public List< Link< Field, Child > > linksToList() {
		throw new IllegalStateException();
	}

	@Override
	public List< Child > childrenToList( Field field ) {
		throw new IllegalStateException();
	}

	@Override
	public Map< Field, Child > firstChildrenToMap() {
		throw new IllegalStateException();
	}

	@Override
	public StarMap< Field, Child > linksToStarMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map< Pair< Field, Integer >, Child > linksToPairMap() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator< Link< Field, Child > > iterator() {
		throw new IllegalStateException();
	}

//	@Override
//	public Hydra< Key, Value, Field, Child > shallowCopy() {
//		throw new IllegalStateException();
//	}
//
//	@Override
//	public Hydra< Key, Value, Field, Child > deepCopy() {
//		throw new IllegalStateException();
//	}
	


}
