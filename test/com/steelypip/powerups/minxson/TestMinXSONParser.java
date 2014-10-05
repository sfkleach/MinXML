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
package com.steelypip.powerups.minxson;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.minxml.MinXML;

public class TestMinXSONParser {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEmpty() {
		assertNull( new MinXSONParser( new StringReader( "" ) ).readElement() );
	}

	@Test
	public void testEmptyAsIterable() {
		int n = 0;
		for ( MinXML m : new MinXSONParser( new StringReader( "" ) ) ) {
			n += 1;
		}
		assertEquals( 0, n );
	}
	
	@Test
	public void testNonEmpty() {
		MinXML m = new MinXSONParser( new StringReader( "<foo/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "foo", m.getName() );
		assertTrue( m.getAttributes().isEmpty() );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testAttributesEitherQuote() {
		MinXML m = new MinXSONParser( new StringReader( "<foo left='right' less=\"more\"/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "right", m.getAttribute( "left" ) );
		assertEquals( "more", m.getAttribute( "less" ) );
		assertTrue( m.isEmpty() );
	}
	
	@Test
	public void testNested() {
		MinXML m = new MinXSONParser( new StringReader( "<outer><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test
	public void testComment() {
		MinXML m = new MinXSONParser( new StringReader( "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test
	public void testCommentWithEmbeddedSigns() {
		MinXML m = new MinXSONParser( new StringReader( "<outer><!-- <- this -> is -> a <- comment <! --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
		assertNotNull( m );
		assertEquals( 1, m.size() );
		assertEquals( "foo", m.get( 0 ).getName() );
		assertTrue( m.get( 0 ).isEmpty() );
	}
	
	@Test( expected=Alert.class )
	public void testBadComment() {
		new MinXSONParser( new StringReader( "<outer><!-- <- this --> is -> a <- bad comment <! --><foo left='right' less=\"more\"></foo></outer>" ) ).readElement();
	}
	
	
	@Test
	public void testPrologElison() {
		MinXML m = new MinXSONParser( new StringReader( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><xxx/>" ) ).readElement();
		assertNotNull( m );
		assertEquals( "xxx", m.getName() );
		assertTrue( m.isEmpty() );
	}
	
	@Test( expected=Alert.class ) 
	public void testForbiddenLessThan() {
		new MinXSONParser( new StringReader( "<xxx a='<'/>" ) ).readElement();
	}
	
	@Test( expected=Exception.class ) 
	public void testForbiddenAmpersand() {
		new MinXSONParser( new StringReader( "<xxx a='&'/>" ) ).readElement();
	}
	
	@Test( expected=Exception.class ) 
	public void testForbiddenDoubleInDouble() {
		new MinXSONParser( new StringReader( "<xxx a=\"\"\"/>" ) ).readElement();
	}
	
	@Test( expected=Exception.class ) 
	public void testForbiddenSingleInSingle() {
		new MinXSONParser( new StringReader( "<xxx a='\''/>" ) ).readElement();
	}
	
	@Test
	public void testAllowedGreaterThan() {
		assertNotNull( new MinXSONParser( new StringReader( "<xxx a='>'/>" ) ).readElement() );
	}
	
	@Test
	public void testAllowedSingleInDouble() {
		assertNotNull( new MinXSONParser( new StringReader( "<xxx a=\"'\"/>" ) ).readElement() );
	}
	
	@Test
	public void testAllowedDoubleInSingle() {
		assertNotNull( new MinXSONParser( new StringReader( "<xxx a='\"'/>" ) ).readElement() );		
	}
	
	
	@Test
	public void testEscape() {
		MinXML m = new MinXSONParser( new StringReader( "<foo bar='&lt;&gt;&amp;&quot;&apos;'/>" ) ).readElement();
		assertEquals( "<>&\"'", m.getAttribute( "bar" ) );
	}
	
	@Test
	public void testAsIterable() {
		int n = 0;
		for ( MinXML m : new MinXSONParser( new StringReader( "<xxx/><yyy/><!-- woot --><zzz/>" ) ) ) {
			n += 1;
		}
		assertEquals( 3, n );
	}
	
	private void equivalent( final String input, final String output ) {
		MinXML e = new MinXSONParser( new StringReader( input ) ).readElement();
		assertEquals( output, "" + e ); 
		
	}
	
	@Test
	public void testNumber() {
		equivalent( "0", "<constant type=\"integer\" value=\"0\"/>" );
		equivalent( "99", "<constant type=\"integer\" value=\"99\"/>" );
		equivalent( "-7", "<constant type=\"integer\" value=\"-7\"/>" );
		equivalent( "-7.1", "<constant type=\"float\" value=\"-7.1\"/>" );
	}
	
	@Test
	public void testBoolean() {
		equivalent( "true", "<constant type=\"boolean\" value=\"true\"/>" );
		equivalent( "false", "<constant type=\"boolean\" value=\"false\"/>" );
	}
	
	@Test
	public void testNull() {
		equivalent( "null", "<constant type=\"null\" value=\"null\"/>" );
	}
	
	@Test
	public void testString() {
		equivalent( "\"\"", "<constant type=\"string\" value=\"\"/>" );
		equivalent( "\"abc\"", "<constant type=\"string\" value=\"abc\"/>" );
		equivalent( "\'abc\'", "<constant type=\"string\" value=\"abc\"/>" );
	}
		
	@Test
	public void testStringSingleLetterEscapes() {
		equivalent( "\"\\b\"", "<constant type=\"string\" value=\"&#8;\"/>" );
		equivalent( "\"\\t\"", "<constant type=\"string\" value=\"&#9;\"/>" );
		equivalent( "\"\\n\"", "<constant type=\"string\" value=\"&#10;\"/>" );
		equivalent( "\"\\f\"", "<constant type=\"string\" value=\"&#12;\"/>" );
		equivalent( "\"\\r\"", "<constant type=\"string\" value=\"&#13;\"/>" );
		equivalent( "\"\\\\\"", "<constant type=\"string\" value=\"\\\"/>" );
		equivalent( "\"\\/\"", "<constant type=\"string\" value=\"/\"/>" );
		equivalent( "\"\\\"\"", "<constant type=\"string\" value=\"&quot;\"/>" );
	}
		
	@Test
	public void testId() {
		equivalent( "x", "<id name=\"x\"/>" );
	}

	@Test
	public void Array__MinXSON() {
		equivalent( "[]", "<array/>" );
		equivalent( "[-12]", "<array><constant type=\"integer\" value=\"-12\"/></array>" );
		equivalent( "[-12,<alpha/>]", "<array><constant type=\"integer\" value=\"-12\"/><alpha/></array>" );
		equivalent( "[-12;<alpha/>]", "<array><constant type=\"integer\" value=\"-12\"/><alpha/></array>" );
		equivalent( "[-12\n<alpha/>]", "<array><constant type=\"integer\" value=\"-12\"/><alpha/></array>" );
	}
	
	@Test
	public void Object__MinXSON() {
		equivalent( "{}", "<object/>" );
		equivalent( "{\"value\":-12}", "<object><constant field=\"value\" type=\"integer\" value=\"-12\"/></object>" );
	}
	
	@Test
	public void NestObjectOrArray__MinXSON() {
		equivalent( "<alpha foo='beta'>{\"value\":-12}</alpha>", "<alpha foo=\"beta\"><object><constant field=\"value\" type=\"integer\" value=\"-12\"/></object></alpha>" );
		equivalent( "<alpha foo='beta'>[-12]</alpha>", "<alpha foo=\"beta\"><array><constant type=\"integer\" value=\"-12\"/></array></alpha>" );
	}

	@Test
	public void NameField__MinXSON() {
		equivalent( 
			"{value:-12}", 
			"<object><constant field=\"value\" type=\"integer\" value=\"-12\"/></object>" 
		);
	}
	
	@Test
	public void GenericClose__MinXSON() {
		equivalent( 
			"<alpha>value</>", 
			"<alpha><id name=\"value\"/></alpha>" 
		);
		equivalent( 
			"<alpha></>", 
			"<alpha/>" 
		);
	}

	@Test
	public void MixedNotations__MinXSON() {
		equivalent( 
			"<alpha[value]/>", 
			"<alpha><id name=\"value\"/></alpha>" 
		);
		equivalent( 
			"<alpha{left:right}/>", 
			"<alpha><id field=\"left\" name=\"right\"/></alpha>" 
		);
	}
	
	@Test
	public void AttributesOnly__MinXSON() {
		equivalent( 
			"<alpha='beta'[value]/>", 
			"<array alpha=\"beta\"><id name=\"value\"/></array>" 
		);
		equivalent( 
			"<alpha='beta'{left:right}/>", 
			"<object alpha=\"beta\"><id field=\"left\" name=\"right\"/></object>" 
		);
	}
	
	@Test
	public void EmptyEmbedded__MinXSON() {
		equivalent( 
			"<[]/>", 
			"<array/>" 
		);
		equivalent( 
			"<{}/>", 
			"<object/>" 
		);
		equivalent( 
			"<(<x/>)/>",
			"<x/>"
		);
	}
	
	@Test
	public void testEscapeAttributes() {
		MinXML m = new MinXSONParser( new StringReader( "<foo bar='&lt;&gt;&amp;&quot;&apos;'/>" ) ).readElement();
		assertEquals( "<>&\"'", m.getAttribute( "bar" ) );
	}
			
	@Test( expected=Exception.class )
	public void testUnfinished() {
		new MinXSONParser( new StringReader( "<foo>" ) ).readElement();
	}
	
	@Test
	public void testEscapeString() {
		MinXML m = new MinXSONParser( new StringReader( "\"xxx\"" ) ).readElement();
		assertEquals( "xxx", m.getAttribute( "value" ) );
	}
	
	@Test
	public void testEOLComment() {
		equivalent( 
			"#!Foo\n<xxx/>", 
			"<xxx/>" 
		);		
		equivalent( 
				"<abc>//Foo\n<xxx/></abc>", 
				"<abc><xxx/></abc>" 
			);		
		equivalent( 
				"<abc>/*Foo/**/<xxx/></abc>", 
				"<abc><xxx/></abc>" 
			);		
	}
	
	@Test
	public void testClassName() {
		equivalent(
			"@foo[]",
			"<array type=\"foo\"/>"
		);
		equivalent(
			"@null[]",
			"<array type=\"null\"/>"
		);
		equivalent(
			"@\"abc\"[]",
			"<array type=\"abc\"/>"
		);
		equivalent(
			"@\"***\"[]",
			"<array type=\"***\"/>"
		);
		equivalent(
			"@\'*!*\'[]",
			"<array type=\"*!*\"/>"
		);
	}
	
	@Test
	public void testNestedDiscard() {
		MinXML m = (
			new MinXSONParser( new StringReader( 
				"<?xml version=\"1.0\" standalone=\"yes\" ?>\n" +
				"<!DOCTYPE author [\n" +
				"  <!ELEMENT author (#PCDATA)>\n" +
				"  <!ENTITY js \"Jo Smith\">\n" +
				"]>\n" +
				"<author name='Jo Smith'></author>"
			) ).readElement()
		);
		assertEquals( "author", m.getName() );
	}
	
}
