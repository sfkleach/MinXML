package com.steelypip.powerups.minxml;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

import com.steelypip.powerups.minxml.TestMinXMLSearcher.CountAttributes;
import com.steelypip.powerups.minxml.TestMinXMLSearcher.EarlyExit;

public class TestMinXMLWalker {


	private static final String source1 = "<outer><!-- this is a comment --><foo left='right' less=\"more\"></foo></outer>";
	
	static class CountAttributes implements MinXMLWalker {
		
		private int start_count = 0;
		private int end_count = 0;

		public int getStartCount() {
			return start_count;
		}

		public int getEndCount() {
			return end_count;
		}

		@Override
		public void startWalk( MinXML subject ) {
			this.start_count += 1;
		}

		@Override
		public void endWalk( MinXML subject ) {
			this.end_count += 1;
		}
		
	}

	@Test
	public void testCountStartsAndEnds() {
		final CountAttributes c = new CountAttributes();
		new MinXMLParser( new StringReader( source1 ) ).readElement().walk( c );
		assertEquals( 2, c.getStartCount() );
		assertEquals( 2, c.getEndCount() );
	}

}
