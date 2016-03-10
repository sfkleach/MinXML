package com.steelypip.powerups.minxmlstar;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.fusion.AbsFusionBuilder;
import com.steelypip.powerups.fusion.FlexiFusionBuilder;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.hydra.OldFlexiHydra;

public class TestMinXMLStarWriter {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testName() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha/>", w.toString() );
	}

	@Test
	public void testAttributes() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		alpha.addValue( "left", "right" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"right\"/>", w.toString() );
	}

	@Test
	public void testAttributeWithWideChar() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		alpha.addValue( "left", "é" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"&#233;\"/>", w.toString() );
	}

	@Test
	public void testSeveralAttributes() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		alpha.addValue( "left1", "right1" );
		alpha.addValue( "left2", "right2" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left1=\"right1\" left2=\"right2\"/>", w.toString() );
	}

	@Test
	public void testMultiValuedAttribute() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		alpha.addValue( "left", "right1" );
		alpha.addValue( "left", "right2" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", w.toString() );
	}
	
	@Test
	public void testChild() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		OldFlexiHydra beta = new OldFlexiHydra( "beta" );
		alpha.addChild( beta );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha><beta/></alpha>", w.toString() );
	}
	
	@Test
	public void testChildren() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		OldFlexiHydra beta = new OldFlexiHydra( "beta" );
		beta.addValue( "b.left", "b.right" );
		OldFlexiHydra gamma = new OldFlexiHydra( "gamma" );
		alpha.addChild( beta );
		alpha.addChild( gamma );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha><beta b.left=\"b.right\"/><gamma/></alpha>", w.toString() );
	}

	@Test
	public void testFieldChild() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		OldFlexiHydra beta = new OldFlexiHydra( "beta" );
		beta.addValue( "b.left", "b.right" );
		alpha.addChild( "child", beta );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha>child:<beta b.left=\"b.right\"/></alpha>", w.toString() );
	}

	@Test
	public void testFieldChildren() {
		OldFlexiHydra alpha = new OldFlexiHydra( "alpha" );
		OldFlexiHydra beta = new OldFlexiHydra( "beta" );
		alpha.addChild( "child", beta );
		OldFlexiHydra gamma = new OldFlexiHydra( "gamma" );
		alpha.addChild( "child", gamma );
		OldFlexiHydra delta = new OldFlexiHydra( "delta" );
		alpha.addChild( "alt.child", delta );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha>alt.child:<delta/>child:<beta/>child+:<gamma/></alpha>", w.toString() );
	}

	@Test
	public void testLiteral() {
		AbsFusionBuilder b = new FlexiFusionBuilder();
		b.addChild( 99 );
		Fusion alpha = b.build();
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<constant type=\"integer\" value=\"99\"/>", w.toString() );
	}
	

}
