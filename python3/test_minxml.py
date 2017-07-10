#!/usr/bin/python3

from minxml import *

def testBasic():
    x = MinXML( "xxx" )
    assert "xxx" == x.getName()
    assert 0 == len( x.getAttributes() )
    x.put( "alpha", "001" )
    assert 1 == len( x.getAttributes() )
    assert "001" == x.get( "alpha" )
    assert not( x )
    assert 0 == len( x ) 
    x.add( MinXML( "yyy" ) )
    x.add( MinXML( "zzz" ) )
    assert 2 == len( x )
    assert "yyy" == x[0].getName()
    
    # @Test
    # public void testPrintingEmpty() {
    #     FlexiMinXML x = new FlexiMinXML( "xxx" );
    #     assertEquals( "<xxx/>", x.toString() );
    # }

    # @Test
    # public void testPrintingAttributes() {
    #     FlexiMinXML x = new FlexiMinXML( "xxx" );
    #     x.putAttribute( "alpha", "001" );
    #     x.putAttribute( "beta", "002" );
    #     assertEquals( "<xxx alpha=\"001\" beta=\"002\"/>", x.toString() );
    # }

    # @Test
    # public void testPrintingNonAscii() {
    #     FlexiMinXML x = new FlexiMinXML( "xxx" );
    #     x.putAttribute( "nonascii", "\u00FF" );
    #     assertEquals( "<xxx nonascii=\"&#255;\"/>", x.toString() );
    # }

    # @Test
    # public void testPrintingSpecialCharacters() {
    #     FlexiMinXML x = new FlexiMinXML( "xxx" );
    #     x.putAttribute( "special", "<>&'\"" );
    #     assertEquals( "<xxx special=\"&lt;&gt;&amp;&apos;&quot;\"/>", x.toString() );
    # }

    # @Test
    # public void testPrintingChildren() {
    #     FlexiMinXML x = new FlexiMinXML( "xxx" );
    #     x.add( new FlexiMinXML( "yyy" ) );
    #     x.add( new FlexiMinXML( "zzz" ) );
    #     assertEquals( "<xxx><yyy/><zzz/></xxx>", x.toString() );
    # }

