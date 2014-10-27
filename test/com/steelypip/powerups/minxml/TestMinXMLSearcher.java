package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class TestMinXMLSearcher {

	private static final String source1 = "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>";

	@Before
	public void setUp() throws Exception {
	}
	
	static class CountAttributes extends MinXMLSearcher {
		
		private int count = 0;

		public int getCount() {
			return count;
		}

		@Override
		public boolean startSearch( MinXML subject ) {
			this.count += subject.sizeAttributes();
			return false;
		}

		@Override
		public boolean endSearch( MinXML subject, boolean found ) {
			return false;
		}
		
	}

	@Test
	public void testSource1() {
		final CountAttributes c = new CountAttributes();
		c.search( new MinXMLParser( new StringReader( source1 ) ).readElement() );
		assertEquals( 2, c.getCount() );
	}
	
	static class EarlyExit extends MinXMLSearcher {
		int count_visited = 0;
		
		public int getCount() {
			return count_visited;
		}

		@Override
		public boolean startSearch( MinXML subject ) {
			this.count_visited += 1;
			return subject.hasAttribute( "early.exit" );
		}

		@Override
		public boolean endSearch( MinXML subject, boolean found ) {
			return found;
		}
		
	}
	
	private static final String source2 = (
		"<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo><bar early.exit=''/><gort/></outer>"
	);
	
	@Test
	public void testSource2() {
		final EarlyExit e = new EarlyExit();
		e.search( new MinXMLParser( new StringReader( source2 ) ).readElement() );
		assertEquals( 3, e.getCount() );
	}


}
