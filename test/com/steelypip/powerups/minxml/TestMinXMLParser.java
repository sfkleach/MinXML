/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */
package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class TestMinXMLParser {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEmpty() {
		assertNull( new MinXMLParser( new StringReader( "" ) ).readElement() );
	}

	@Test
	public void testEmptyAsIterable() {
		int n = 0;
		for ( MinXML m : new MinXMLParser( new StringReader( "" ) ) ) {
			n += 1;
		}
		assertEquals( 0, n );
	}
	
	@Test
	public void testNonEmpty() {
		MinXML m = new MinXMLParser( new StringReader( "<foo/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "foo", m.getName() );
		assertTrue( m.getAttributes().isEmpty() );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testAttributesEitherQuote() {
		MinXML m = new MinXMLParser( new StringReader( "<foo left='right' less=\"more\"/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "right", m.getAttribute( "left" ) );
		assertEquals( "more", m.getAttribute( "less" ) );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testNested() {
		MinXML m = new MinXMLParser( new StringReader( "<outer><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test
	public void testComment() {
		MinXML m = new MinXMLParser( new StringReader( "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test
	public void testPrologElison() {
		MinXML m = new MinXMLParser( new StringReader( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><xxx/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "xxx", m.getName() );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testAsIterable() {
		int n = 0;
		for ( MinXML m :  new MinXMLParser( new StringReader( "<xxx/><yyy/><!-- woot --><zzz/>" ) ) ) {
			n += 1;
		}
		assertEquals( 3, n );
	}
			
}
