package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

public class TestMinXMLStepper {
	
	//	This is a horrid cheat.
    static <E> Iterable<E> iterable( final @NonNull Iterator< E > iterator ) {
        return new Iterable<E>() {
            public Iterator<E> iterator() {
                return iterator;
            }
        };
    }
	
	MinXML tree;

	@Before
	public void setUp() throws Exception {
		this.tree = new MinXMLParser( new StringReader( "<a><b><c/></b><d><e/><f/></d></a>" ) ).readElement();		
	}

	@Test
	public void test() {
		MinXMLStepper stepper = new MinXMLStepper( this.tree );
		assertEquals( "a", stepper.next().getName() );
		assertEquals( "b", stepper.next().getName() );
		assertEquals( "c", stepper.next().getName() );
		assertEquals( "d", stepper.next().getName() );
		assertEquals( "e", stepper.next().getName() );
		assertEquals( "f", stepper.next().getName() );
		assertFalse( stepper.hasNext() );
		try {
			stepper.next();
		} catch ( NoSuchElementException e ) {
		}
	}

	@Test
	public void testBreadthFirst() {
		MinXMLStepper stepper = new MinXMLStepper( this.tree ).setBreadthFirst();
		assertEquals( "a", stepper.next().getName() );
		assertEquals( "b", stepper.next().getName() );
		assertEquals( "d", stepper.next().getName() );
		assertEquals( "c", stepper.next().getName() );
		assertEquals( "e", stepper.next().getName() );
		assertEquals( "f", stepper.next().getName() );
		assertFalse( stepper.hasNext() );
		try {
			stepper.next();
		} catch ( NoSuchElementException e ) {
		}
	}

	@Test
	public void testReverse() {
		MinXMLStepper stepper = new MinXMLStepper( this.tree ).setReverseTraversal();
		assertEquals( "a", stepper.next().getName() );
		assertEquals( "d", stepper.next().getName() );
		assertEquals( "f", stepper.next().getName() );
		assertEquals( "e", stepper.next().getName() );
		assertEquals( "b", stepper.next().getName() );
		assertEquals( "c", stepper.next().getName() );
		assertFalse( stepper.hasNext() );
		try {
			stepper.next();
		} catch ( NoSuchElementException e ) {
		}
	}

	@Test
	public void testReverseBreadthFirst() {
		MinXMLStepper stepper = new MinXMLStepper( this.tree ).setReverseTraversal().setBreadthFirst();
		assertEquals( "a", stepper.next().getName() );
		assertEquals( "d", stepper.next().getName() );
		assertEquals( "b", stepper.next().getName() );
		assertEquals( "f", stepper.next().getName() );
		assertEquals( "e", stepper.next().getName() );
		assertEquals( "c", stepper.next().getName() );
		assertFalse( stepper.hasNext() );
		try {
			stepper.next();
		} catch ( NoSuchElementException e ) {
		}
	}

}
