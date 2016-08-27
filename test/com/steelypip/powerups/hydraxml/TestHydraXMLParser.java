package com.steelypip.powerups.hydraxml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.alert.Alert;

public class TestHydraXMLParser {

	@Before
	public void setUp() throws Exception {
	}


	private void checkSimpleElement( HydraXMLParser p ) {
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "foo", item.getInternedName() );
		assertTrue( item.hasNoAttributes() );
		assertTrue( item.hasNoLinks() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test
	public void minimalElement() {
		for ( String input : new String[] { "<foo></foo>", " < foo > </ foo > " } ) {
			StringReader rep = new StringReader( input );
			checkSimpleElement( new HydraXMLParser( rep ) );
		}
	}

	@Test
	public void minimalStandaloneElement() {
		StringReader rep = new StringReader( "<foo/>" );
		checkSimpleElement( new HydraXMLParser( rep ) );
	}

	@Test
	public void multipleElements() {
		StringReader rep = new StringReader( "<foo/><bar/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item1 = p.readElement();
		assertTrue( item1 instanceof HydraXML );
		HydraXML item2 = p.readElement();
		assertTrue( item2 instanceof HydraXML );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	

	@Test
	public void attributeOnEndTag() {
		StringReader rep = new StringReader( "<foo></foo bar='88'>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test
	public void endTag() {
		StringReader rep = new StringReader( "<foo></>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item1 = p.readElement();
		assertTrue( item1 instanceof HydraXML );		
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	

	@Test
	public void oneAttribute() {
		StringReader rep = new StringReader( "<foo bar='99'/>" );
		HydraXMLParser p= new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "foo", item.getInternedName() );
		assertSame( 1, item.sizeAttributes() );
		assertTrue( item.hasAttribute( "bar", "99" ) );
		assertTrue( item.hasNoLinks() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test( expected=Exception.class )
	public void repeatedBadAttribute() {
		StringReader rep = new StringReader( "<foo bar='99' bar='88'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}

	@Test
	public void repeatedOKAttribute() {
		StringReader rep = new StringReader( "<foo bar='99' bar+='88'/>" );
		HydraXMLParser p= new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertSame( "foo", item.getInternedName() );
		Set< String > key_set = item.keysToSet();
		assertEquals( 1, key_set.size() );
		assertTrue( key_set.contains( "bar" ) );
	}


	@Test
	public void nestedElements() {
		StringReader rep = new StringReader( "<bar><foo/></bar>" );
		HydraXMLParser p= new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 1, item.sizeLinks() );
		HydraXML child = item.getChild();
		assertEquals( "foo", child.getName() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}

	@Test
	public void linkedElements() {
		StringReader rep = new StringReader( "<bar>gort:<foo/><oof/></bar>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 2, item.sizeLinks() );
		HydraXML child = item.getChild();
		assertEquals( "oof", child.getName() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}
	
	@Test( expected = Exception.class )
	public void badDuplicateLinks() {
		StringReader rep = new StringReader( "<bar>gort:<foo/>gort:<oof/></bar>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}

	@Test
	public void okDuplicateLinks() {
		StringReader rep = new StringReader( "<bar>gort:<foo/>gort+:<oof/></bar>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 2, item.sizeLinks() );
		assertSame( 2, item.sizeChildren( "gort" ) );
		HydraXML child0 = item.getChild( "gort" );
		assertEquals( "foo", child0.getName() );
		HydraXML child1 = item.getChild( "gort", 1 );
		assertEquals( "oof", child1.getName() );
		HydraXML end = p.readElement();
		assertNull( end );		
	}


//	@Test
//	public void integerLiteral() {
//		StringReader rep = new StringReader( "6" );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		assertNotNull( item );
//		checkConstant( item, "integer", "6" );
//		HydraXML end = p.readElement();
//		assertNull( end );		
//	}
	
//	@Test
//	public void floatLiteral() {
//		StringReader rep = new StringReader( "-0.1" );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		assertNotNull( item );
//		checkConstant( item, "float", "-0.1" );
//		HydraXML end = p.readElement();
//		assertNull( end );
//	}
//	
//	@Test
//	public void stringLiteral() {
//		StringReader rep = new StringReader( "\"foo\"" );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		checkConstant( item, "string", "foo" );
//	}
//
//	@Test
//	public void nullLiteral() {
//		StringReader rep = new StringReader( "null" );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		checkConstant( item, "null", "null" );
//	}
//
//	@Test
//	public void booleanLiteral() {
//		StringReader rep = new StringReader( "true false" );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		checkConstant( item, "boolean", "true" );
//		item = p.readElement();
//		checkConstant( item, "boolean", "false" );
//		assertNull( p.readElement() );
//	}
//	
//	@Test
//	public void emptyArray() {
//		for ( String input : new String[] { "[]", "[ ]" } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isArray() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasNoLinks() );
//	//		item = p.readElement();
//	//		checkConstant( item, "boolean", "false" );
//			assertNull( p.readElement() );
//		}
//	}
	
//	@Test
//	public void nonEmptyArray() {
//		for ( String input : new String[] { "[1]", " [ 1 ] ", "[<foo/>]", "[ < foo /> ]" } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isArray() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasSizeLinks( 1 ) );
//			assertNull( p.readElement() );
//		}
//	}
//	
//	@Test
//	public void emptyObject() {
//		for ( String input : new String[] { "{}", " {  } " } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasNoLinks() );
//			assertNull( p.readElement() );
//		}
//	}
//	
//	@Test
//	public void integerInObject() {
//		for ( String input : new String[] { "{foo:99}", " { foo : 99 } " } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasSizeLinks( 1 ) );
////			Fusion f99 = item.getChild( "foo" );
////			assertTrue( f99.isInteger() );
//			assertNull( p.readElement() );
//		}
//	}
	
//	@Test
//	public void booleanInObject() {
//		for ( String input : new String[] { "{foo:true}" } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasSizeLinks( 1 ) );
////			Fusion f99 = item.getChild( "foo" );
////			assertTrue( f99.isInteger() );
//			assertNull( p.readElement() );
//		}
//	}
//	
//	@Test
//	public void nullInObject() {
//		for ( String input : new String[] { "{foo:null}" } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasSizeLinks( 1 ) );
////			Fusion f99 = item.getChild( "foo" );
////			assertTrue( f99.isInteger() );
//			assertNull( p.readElement() );
//		}
//	}
//	
//	@Test
//	public void decimalInObject() {
//		for ( String input : new String[] { "{foo:0.2}" } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasSizeLinks( 1 ) );
//			assertNull( p.readElement() );
//		}
//	}
	
//	@Test
//	public void stringInObject() {
//		for ( String input : new String[] { "{foo:\"bar\"}" } ) {
//			StringReader rep = new StringReader( input );
//			HydraXMLParser p = new HydraXMLParser( rep );
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertSame( 1, item.sizeLinks() );
//			assertNull( p.readElement() );
//		}
//	}
	
//	@Test
//	public void commasForJSON() {
//		StringReader rep = new StringReader( "[ 1, 2, 3, 4 ], {}" );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		{
//			HydraXML item = p.readElement();
//			assertTrue( item.isArray() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasSizeLinks( 4 ) );
//		}
//		{
//			HydraXML item = p.readElement();
//			assertTrue( item.isObject() );
//			assertTrue( item.hasNoAttributes() );
//			assertTrue( item.hasNoLinks() );
//		}
//		assertNull( p.readElement() );
//	}
	
	@Test( expected = Alert.class )
	public void badCommasForJSON() {
		StringReader rep = new StringReader( "<foo, bar='x'/>" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badMultipleCommasForJSON() {
		StringReader rep = new StringReader( "[1,,2]" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badMultipleTopLevelCommasForJSON() {
		StringReader rep = new StringReader( "1,,2" );
		HydraXMLParser p = new HydraXMLParser( rep );
		p.readElement();
		p.readElement();
	}
	
	
	@Test
	public void elementsWithStringsForNames() {
		for ( String input : new String[] { "<\"x y\"/>", "<'x y'/>" } ) {
			StringReader rep = new StringReader( input );
			HydraXMLParser p = new HydraXMLParser( rep );
			HydraXML item = p.readElement();
			assertSame( "x y", item.getInternedName() );
			assertNull( p.readElement() );
		}		
	}
	
//	@Test
//	public void doubleQuotes() {
//		final String s = "\"This has a newline\\n\"";
//		StringReader rep = new StringReader( s );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		assertEquals( "This has a newline\n", item.stringValue() );
//		assertNull( p.readElement() );
//	}
	
//	@Test
//	public void singleQuotes() {
//		final String s = "'This has an ampersand \\&amp;'";
//		StringReader rep = new StringReader( s );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		assertEquals( "This has an ampersand &", item.stringValue() );
//		assertNull( p.readElement() );
//	}
	
	@Test
	public void attributeEscapes1() {
		final String s = "<foo bar='&amp;'/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( "&", item.getValue( "bar" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void attributeEscapes2() {
		final String s = "<foo bar:'&amp;'/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertEquals( "&amp;", item.getValue( "bar" ) );
		assertNull( p.readElement() );
	}
	
	@Test
	public void testComment() {
		final String s = "// This is a test\n<foo/>";
		StringReader rep = new StringReader( s );
		HydraXMLParser p = new HydraXMLParser( rep );
		HydraXML item = p.readElement();
		assertTrue( item.hasName( "foo" ) );
		assertNull( p.readElement() );
	}
	
//	@Test
//	public void testHex() {
//		final String s = "0x1F";
//		StringReader rep = new StringReader( s );
//		HydraXMLParser p = new HydraXMLParser( rep );
//		HydraXML item = p.readElement();
//		checkConstant( item, "integer", "31" );
//		assertNull( p.readElement() );
//	}
	
//	private void checkConstant( Fusion item, String type, String value ) {
//		assertEquals( "constant", item.getInternedName() );
//		assertTrue( item.hasSizeAttributes( 2 ) );
//		assertTrue( item.hasAttribute( "type" ) ); 
//		assertEquals( type, item.getValue( "type" ) ); 
//		assertTrue( item.hasAttribute( "value" ) ); 
//		assertEquals( value, item.getValue( "value" ) ); 
//	}
	
	
}
