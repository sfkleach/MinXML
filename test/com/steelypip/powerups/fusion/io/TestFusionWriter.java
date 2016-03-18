package com.steelypip.powerups.fusion.io;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.fusion.AbsFusionBuilder;
import com.steelypip.powerups.fusion.FlexiFusionBuilder;
import com.steelypip.powerups.fusion.Fusion;
import com.steelypip.powerups.fusion.FlexiFusion;

public class TestFusionWriter {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testName() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha/>", w.toString() );
	}

	@Test
	public void testAttributes() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		alpha.addValue( "left", "right" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"right\"/>", w.toString() );
	}

	@Test
	public void testAttributeWithWideChar() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		alpha.addValue( "left", "é" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"&#233;\"/>", w.toString() );
	}

	@Test
	public void testSeveralAttributes() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		alpha.addValue( "left1", "right1" );
		alpha.addValue( "left2", "right2" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left1=\"right1\" left2=\"right2\"/>", w.toString() );
	}

	@Test
	public void testMultiValuedAttribute() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		alpha.addValue( "left", "right1" );
		alpha.addValue( "left", "right2" );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha left=\"right1\" left+=\"right2\"/>", w.toString() );
	}
	
	@Test
	public void testChild() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		FlexiFusion beta = new FlexiFusion( "beta" );
		alpha.addChild( beta );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha><beta/></alpha>", w.toString() );
	}
	
	@Test
	public void testChildren() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		FlexiFusion beta = new FlexiFusion( "beta" );
		beta.addValue( "b.left", "b.right" );
		FlexiFusion gamma = new FlexiFusion( "gamma" );
		alpha.addChild( beta );
		alpha.addChild( gamma );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha><beta b.left=\"b.right\"/><gamma/></alpha>", w.toString() );
	}

	@Test
	public void testFieldChild() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		FlexiFusion beta = new FlexiFusion( "beta" );
		beta.addValue( "b.left", "b.right" );
		alpha.addChild( "child", beta );
		StringWriter w = new StringWriter();
		alpha.print( w );
		assertEquals( "<alpha>child:<beta b.left=\"b.right\"/></alpha>", w.toString() );
	}

	@Test
	public void testFieldChildren() {
		FlexiFusion alpha = new FlexiFusion( "alpha" );
		FlexiFusion beta = new FlexiFusion( "beta" );
		alpha.addChild( "child", beta );
		FlexiFusion gamma = new FlexiFusion( "gamma" );
		alpha.addChild( "child", gamma );
		FlexiFusion delta = new FlexiFusion( "delta" );
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
