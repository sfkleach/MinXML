package com.steelypip.powerups.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.common.Pair;

public class TestStarMap {
	
	StarMap< String, Integer > base;
	StarMap< String, Integer > sample;

	@Before
	public void setUp() throws Exception {
		this.base = new TreeStarMap< String, Integer >();
		this.sample = new TreeStarMap< String, Integer >();
		this.sample.add( "a1", 10 );
		this.sample.add( "a2", 20 );
		this.sample.add( "a2", 21 );
	}

	@Test
	public void testClear() {
		assertFalse( this.sample.isEmpty() );
		this.sample.clear();
		assertTrue( this.sample.isEmpty() );
	}
	
	@Test
	public void testContainsEntry() {
		assertFalse( this.sample.containsEntry( "a3", 99 ) );
		assertTrue( this.sample.containsEntry( "a1", 10 ) );
		assertTrue( this.sample.containsEntry( "a2", 20 ) );
		assertTrue( this.sample.containsEntry( "a2", 21 ) );
		assertFalse( this.sample.containsEntry( "a1", 11 ) );
		assertFalse( this.sample.containsEntry( "a2", 9 ) );
	}
	
	@Test
	public void testContainsKey() {
		assertFalse( this.base.containsKey( "a1" ) );
		assertTrue( this.sample.containsKey( "a1" ) );
		assertTrue( this.sample.containsKey( "a2" ) );
		assertFalse( this.sample.containsKey( "a3" ) );
	}
	
	@Test
	public void testContainsValue() {
		assertFalse( this.base.containsValue( 99 ) );
		assertTrue( this.sample.containsValue( 10 ) );
		assertTrue( this.sample.containsValue( 20 ) );
		assertTrue( this.sample.containsValue( 21 ) );
		assertFalse( this.sample.containsValue( 22 ) );
	}
	
	@Test
	public void testEntriesAsList() {
		assertTrue( this.base.entriesAsList().isEmpty() );
		List< Pair< String, Integer > > list = this.sample.entriesAsList();
		assertSame( 3, list.size() );
		assertEquals( "a1", list.get( 0 ).getFirst() );
		assertSame( 10, list.get( 0 ).getSecond() );
		assertEquals( "a2", list.get( 1 ).getFirst() );
		assertSame( 20, list.get( 1 ).getSecond() );
		assertEquals( "a2", list.get( 2 ).getFirst() );
		assertSame( 21, list.get( 2 ).getSecond() );
	}
	
	@Test
	public void testEquals() {
		assertEquals( this.base, this.base );
		assertNotEquals( this.base, this.sample );
		assertNotEquals( this.sample, this.base );
		assertEquals( this.sample, this.sample );
		this.sample.clear();
		assertEquals( this.base, this.sample );
	}
	
	@Test
	public void testGetAll() {
		List< Integer > list1 = this.sample.getAll( "a1" );
		assertSame( 1, list1.size() );
		assertSame( 10, list1.get( 0 ) );
		List< Integer > list2 = this.sample.getAll( "a2" );
		assertSame( 2, list2.size() );
		assertSame( 20, list2.get( 0 ) );
		assertSame( 21, list2.get( 1 ) );
	}

	@Test
	public void testGet() {
		final int n = this.sample.get( "a1"  );
		assertSame( 10, n );
		boolean has_failed = false;
		try {
			this.sample.get( "a3" );
		} catch ( IllegalArgumentException _e ) {
			has_failed = true;
		}
		assertTrue( has_failed );
	}
	
	@Test
	public void testGet1() {
		assertSame( 10, this.sample.get( "a1", 0 ) );
		assertSame( 20, this.sample.get( "a2", 0 ) );
		assertSame( 21, this.sample.get( "a2", 1 ) );
	}
	
	@Test
	public void testHashCode() {
		int hashb = this.base.hashCode();
		int hashs = this.sample.hashCode();
		this.sample.clear();
		int hashc = this.sample.hashCode();
		assertSame( hashb, hashc );
		assertNotSame( hashb, hashs );
	}

	@Test
	public void testIsEmpty() {
		assertTrue( this.base.isEmpty() );
		assertFalse( this.sample.isEmpty() );
	}
	
	@Test
	public void testKeySet() {
		assertTrue( this.base.keySet().isEmpty() );
		Set< String > keys = this.sample.keySet();
		assertSame( 2, keys.size() );
		assertTrue( keys.contains( "a1" ) );
		assertTrue( keys.contains( "a2" ) );
		assertFalse( keys.contains( "a3" ) );
	}
	
	@Test
	public void testAdd() {
		this.base.add( "A1", 100 );
		assertSame( 100, this.base.get( "A1" ) );
		this.base.add( "A1", 101 );
		assertSame( 101, this.base.get(  "A1", 1 ) );
	}
	
	@Test
	public void testAddAll() {
		//void addAll(K key, Iterable<? extends V> values);
		this.base.addAll( "a1", Arrays.asList( 10 ) );
		this.base.addAll( "a2", Arrays.asList( 20, 21 ) );
		assertEquals( this.base, this.sample );
	}
	
	@Test
	public void testAddAll1() {
		//void addAll(StarMap<? extends K,? extends V> multimap);
		this.base.addAll( this.sample );
		assertEquals( this.base, this.sample );		
	}

	@Test
	public void testRemoveEntry() {
		//void removeEntry( Object key, Object value );
		this.base.addAll(  this.sample );
		this.base.removeEntry( "a3", 999 );
		assertEquals( this.base, this.sample );		
		this.base.removeEntry( "a2", 21 );
		assertNotEquals( this.base, this.sample );		
		assertSame( 2, this.base.size() );
		assertSame( 2, this.base.keySet().size() );
		this.base.removeEntry( "a1", 10 );
		this.base.removeEntry( "a2", 20 );
		System.err.println( this.base.size() );
		System.err.println( this.base.isEmpty() );
		assertTrue( this.base.isEmpty() );
				
	}
	
	@Test 
	public void testRemoveEntryAt() {
		//void removeEntryAt( Object key, int N );
		this.sample.removeEntryAt( "a2", 1 );
		assertSame( 1, this.sample.getAll( "a2" ).size() );
		assertSame( 20, this.sample.getAll( "a2" ).get( 0 ) );
	}
	
	@Test
	public void testRemoveEntries() {
		//void removeEntries( Object key );
		this.sample.removeEntries( "a2" );
		assertSame( 1, this.sample.size() );
	}
	
	@Test
	public void testSetValues() {
		//void setValues(K key, Iterable<? extends V> values);
		this.sample.setValues( "a3", Arrays.asList( 30,31,32 ) );
		assertSame( 6, this.sample.size() );
	}
	
	@Test
	public void	testSize() {
		assertSame( 0, this.base.size() );
		assertSame( 3, this.sample.size() );
	}
	@Test
	public void testValues() {
		assertEquals( Arrays.asList( 10, 20, 21 ), this.sample.values() );
	}

}
