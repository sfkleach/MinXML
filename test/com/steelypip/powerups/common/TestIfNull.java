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
package com.steelypip.powerups.common;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestIfNull {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertEquals( (Integer)0, IfNull.ifNull( 0, 1 ) );
		assertEquals( (Integer)0, IfNull.ifNull( 0, null ) );
		assertEquals( (Integer)0, IfNull.ifNull( null, 0 ) );
		assertEquals( null, IfNull.ifNull( null, null ) );
	}

}
