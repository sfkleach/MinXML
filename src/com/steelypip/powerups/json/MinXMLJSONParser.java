package com.steelypip.powerups.json;

import java.io.Reader;

import com.steelypip.powerups.charrepeater.ReaderCharRepeater;
import com.steelypip.powerups.minxml.FlexiMinXMLBuilder;
import com.steelypip.powerups.minxml.MinXML;

public class MinXMLJSONParser extends JSONParser< MinXML > {

	public MinXMLJSONParser( final Reader reader ) {
		super( new ReaderCharRepeater( reader ), new MinXMLBuilderJSONBuilder( new FlexiMinXMLBuilder() ) );
	}

	
}
