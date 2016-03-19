package com.steelypip.powerups.fusion.io;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.fusion.Fusion;

public class TestFusionParser {

	@Before
	public void setUp() throws Exception {
	}
	
	private void checkSimpleElement( FusionParser p ) {
		Fusion item = p.readElement();
		assertNotNull( item );
		assertEquals( "foo", item.getInternedName() );
		assertTrue( item.hasNoAttributes() );
		assertTrue( item.hasNoLinks() );
		Fusion end = p.readElement();
		assertNull( end );		
	}
	
//	Iterator< String > jitter( final String s ) {
//		for ( int n = 1; n < s.length(); n++ ) {
//			char before = s.charAt( n - 1 );
//			char after = s.charAt( n );
//		}
//	}
	
	@Test
	public void minimalElement() {
		for ( String input : new String[] { "<foo></foo>", " < foo > </ foo > " } ) {
			StringReader rep = new StringReader( input );
			checkSimpleElement( new FusionParser( rep ) );
		}
	}

	@Test
	public void minimalStandaloneElement() {
		StringReader rep = new StringReader( "<foo/>" );
		checkSimpleElement( new FusionParser( rep ) );
	}

	@Test
	public void multipleElements() {
		StringReader rep = new StringReader( "<foo/><bar/>" );
		FusionParser p = new FusionParser( rep );
		Fusion item1 = p.readElement();
		assertTrue( item1 instanceof Fusion );
		Fusion item2 = p.readElement();
		assertTrue( item2 instanceof Fusion );
		Fusion end = p.readElement();
		assertNull( end );		
	}
	

	@Test
	public void attributeOnEndTag() {
		StringReader rep = new StringReader( "<foo></foo bar='88'>" );
		FusionParser p = new FusionParser( rep );
		Fusion item1 = p.readElement();

		Fusion end = p.readElement();
		assertNull( end );		
	}
	
	@Test
	public void endTag() {
		StringReader rep = new StringReader( "<foo></>" );
		FusionParser p = new FusionParser( rep );
		Fusion item1 = p.readElement();
		assertTrue( item1 instanceof Fusion );		
		Fusion end = p.readElement();
		assertNull( end );		
	}
	

	@Test
	public void oneAttribute() {
		StringReader rep = new StringReader( "<foo bar='99'/>" );
		FusionParser p= new FusionParser( rep );
		Fusion item = p.readElement();
		assertNotNull( item );
		assertEquals( "foo", item.getInternedName() );
		assertSame( 1, item.sizeAttributes() );
		assertTrue( item.hasAttribute( "bar", "99" ) );
		assertTrue( item.hasNoLinks() );
		Fusion end = p.readElement();
		assertNull( end );		
	}
	
	@Test( expected=Exception.class )
	public void repeatedBadAttribute() {
		StringReader rep = new StringReader( "<foo bar='99' bar='88'/>" );
		FusionParser p= new FusionParser( rep );
		Fusion item = p.readElement();
	}

	@Test
	public void repeatedOKAttribute() {
		StringReader rep = new StringReader( "<foo bar='99' bar+='88'/>" );
		FusionParser p= new FusionParser( rep );
		Fusion item = p.readElement();
		assertSame( "foo", item.getInternedName() );
		Set< String > key_set = item.keysToSet();
		assertEquals( 1, key_set.size() );
		assertTrue( key_set.contains( "bar" ) );
	}


	@Test
	public void nestedElements() {
		StringReader rep = new StringReader( "<bar><foo/></bar>" );
		FusionParser p= new FusionParser( rep );
		Fusion item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 1, item.sizeLinks() );
		Fusion child = item.getChild();
		assertEquals( "foo", child.getName() );
		Fusion end = p.readElement();
		assertNull( end );		
	}

	@Test
	public void linkedElements() {
		StringReader rep = new StringReader( "<bar>gort:<foo/><oof/></bar>" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 2, item.sizeLinks() );
		Fusion child = item.getChild();
		assertEquals( "oof", child.getName() );
		Fusion end = p.readElement();
		assertNull( end );		
	}
	
	@Test( expected = Exception.class )
	public void badDuplicateLinks() {
		StringReader rep = new StringReader( "<bar>gort:<foo/>gort:<oof/></bar>" );
		FusionParser p = new FusionParser( rep );
		p.readElement();
	}

	@Test
	public void okDuplicateLinks() {
		StringReader rep = new StringReader( "<bar>gort:<foo/>gort+:<oof/></bar>" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		assertNotNull( item );
		assertEquals( "bar", item.getInternedName() );
		assertSame( 0, item.sizeAttributes() );
		assertSame( 2, item.sizeLinks() );
		assertSame( 2, item.sizeChildren( "gort" ) );
		Fusion child0 = item.getChild( "gort" );
		assertEquals( "foo", child0.getName() );
		Fusion child1 = item.getChild( "gort", 1 );
		assertEquals( "oof", child1.getName() );
		Fusion end = p.readElement();
		assertNull( end );		
	}


	@Test
	public void integerLiteral() {
		StringReader rep = new StringReader( "6" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		assertNotNull( item );
		checkConstant( item, "integer", "6" );
		Fusion end = p.readElement();
		assertNull( end );		
	}
	
	@Test
	public void floatLiteral() {
		StringReader rep = new StringReader( "-0.1" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		assertNotNull( item );
		checkConstant( item, "float", "-0.1" );
		Fusion end = p.readElement();
		assertNull( end );
	}
	
	@Test
	public void stringLiteral() {
		StringReader rep = new StringReader( "\"foo\"" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		checkConstant( item, "string", "foo" );
	}

	@Test
	public void nullLiteral() {
		StringReader rep = new StringReader( "null" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		checkConstant( item, "null", "null" );
	}

	@Test
	public void booleanLiteral() {
		StringReader rep = new StringReader( "true false" );
		FusionParser p = new FusionParser( rep );
		Fusion item = p.readElement();
		checkConstant( item, "boolean", "true" );
		item = p.readElement();
		checkConstant( item, "boolean", "false" );
		assertNull( p.readElement() );
	}
	
	@Test
	public void emptyArray() {
		for ( String input : new String[] { "[]", "[ ]" } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isArray() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasNoLinks() );
	//		item = p.readElement();
	//		checkConstant( item, "boolean", "false" );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void nonEmptyArray() {
		for ( String input : new String[] { "[1]", " [ 1 ] ", "[<foo/>]", "[ < foo /> ]" } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isArray() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 1 ) );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void emptyObject() {
		for ( String input : new String[] { "{}", " {  } " } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasNoLinks() );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void integerInObject() {
		for ( String input : new String[] { "{foo:99}", " { foo : 99 } " } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 1 ) );
//			Fusion f99 = item.getChild( "foo" );
//			assertTrue( f99.isInteger() );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void booleanInObject() {
		for ( String input : new String[] { "{foo:true}" } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 1 ) );
//			Fusion f99 = item.getChild( "foo" );
//			assertTrue( f99.isInteger() );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void nullInObject() {
		for ( String input : new String[] { "{foo:null}" } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 1 ) );
//			Fusion f99 = item.getChild( "foo" );
//			assertTrue( f99.isInteger() );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void decimalInObject() {
		for ( String input : new String[] { "{foo:0.2}" } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 1 ) );
//			Fusion f99 = item.getChild( "foo" );
//			assertTrue( f99.isInteger() );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void stringInObject() {
		for ( String input : new String[] { "{foo:\"bar\"}" } ) {
			StringReader rep = new StringReader( input );
			FusionParser p = new FusionParser( rep );
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 1 ) );
//			Fusion f99 = item.getChild( "foo" );
//			assertTrue( f99.isInteger() );
			assertNull( p.readElement() );
		}
	}
	
	@Test
	public void commasForJSON() {
		StringReader rep = new StringReader( "[ 1, 2, 3, 4 ], {}" );
		FusionParser p = new FusionParser( rep );
		{
			Fusion item = p.readElement();
			assertTrue( item.isArray() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasSizeLinks( 4 ) );
		}
		{
			Fusion item = p.readElement();
			assertTrue( item.isObject() );
			assertTrue( item.hasNoAttributes() );
			assertTrue( item.hasNoLinks() );
		}
		assertNull( p.readElement() );
	}
	
	@Test( expected = Alert.class )
	public void badCommasForJSON() {
		StringReader rep = new StringReader( "<foo, bar='x'/>" );
		FusionParser p = new FusionParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badMultipleCommasForJSON() {
		StringReader rep = new StringReader( "[1,,2]" );
		FusionParser p = new FusionParser( rep );
		p.readElement();
	}
	
	@Test( expected = Alert.class )
	public void badMultipleTopLevelCommasForJSON() {
		StringReader rep = new StringReader( "1,,2" );
		FusionParser p = new FusionParser( rep );
		p.readElement();
		p.readElement();
	}
	
	private void checkConstant( Fusion item, String type, String value ) {
		assertEquals( "constant", item.getInternedName() );
		assertTrue( item.hasSizeAttributes( 2 ) );
		assertTrue( item.hasAttribute( "type" ) ); 
		assertEquals( type, item.getValue( "type" ) ); 
		assertTrue( item.hasAttribute( "value" ) ); 
		assertEquals( value, item.getValue( "value" ) ); 
	}

	

}
