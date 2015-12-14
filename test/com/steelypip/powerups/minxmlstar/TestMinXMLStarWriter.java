package com.steelypip.powerups.minxmlstar;

import static org.junit.Assert.*;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

public class TestMinXMLStarWriter {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testName() {
		FlexiMinXMLStar alpha = new FlexiMinXMLStar( "alpha" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha/>", w.toString() );
	}

	@Test
	public void testAttributes() {
		FlexiMinXMLStar alpha = new FlexiMinXMLStar( "alpha" );
		alpha.addValue( "left", "right" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"right\"/>", w.toString() );
	}

	@Test
	public void testSeveralAttributes() {
		FlexiMinXMLStar alpha = new FlexiMinXMLStar( "alpha" );
		alpha.addValue( "left1", "right1" );
		alpha.addValue( "left2", "right2" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left1=\"right1\" left2=\"right2\"/>", w.toString() );
	}

	@Test
	public void testMultiValuedAttribute() {
		FlexiMinXMLStar alpha = new FlexiMinXMLStar( "alpha" );
		alpha.addValue( "left", "right1" );
		alpha.addValue( "left", "right2" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", w.toString() );
	}
	
	@Test
	public void testChild() {
		FlexiMinXMLStar alpha = new FlexiMinXMLStar( "alpha" );
		FlexiMinXMLStar beta = new FlexiMinXMLStar( "beta" );
		alpha.addChild( beta );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha><beta/></alpha>", w.toString() );
	}

}
