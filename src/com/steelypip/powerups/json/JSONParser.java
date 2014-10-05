package com.steelypip.powerups.json;

import com.steelypip.powerups.alert.Alert;
import com.steelypip.powerups.charrepeater.CharRepeater;

public class JSONParser< T > {
	
	private static final char NON_NUMERIC_CHAR = ' ';
	private static final char NON_WHITESPACE_CHAR = '_';
	
	final JSONBuilder< T > builder;
	final CharRepeater cucharin; 

	public JSONParser( final CharRepeater rep, JSONBuilder< T > builder ) {
		this.builder = builder;
		this.cucharin = rep;
	}
	
	private void eatWhitespace() {
		while ( Character.isWhitespace( this.cucharin.peekChar( NON_WHITESPACE_CHAR ) ) ) {
			this.cucharin.skipChar();
		}
	}
	
	private void readNumber() {
		boolean is_floating_point = false;
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		do {
			final char ch = this.cucharin.peekChar( NON_NUMERIC_CHAR );
			switch ( ch ) {
				case '-':
				case '+':
					break;
				case '.':
					is_floating_point = true;
					break;
				default:
					if ( ! Character.isDigit( ch ) ) {
						done = true;
					}
					break;
			}
			if ( done ) break;
			sofar.append( ch );
			this.cucharin.skipChar();
		} while ( ! done );
		
		//	We have slightly different tests for floating point versus integers.
		final String s = sofar.toString();
		try {
			if ( is_floating_point ) {
				this.builder.addFloat( Double.parseDouble( s ) );
			} else {
				this.builder.addInteger( Long.parseLong( s ) );
			}
		} catch ( NumberFormatException e ) {
			throw new Alert( "Malformed number" ).culprit( "Bad number", sofar );
		}
	}
	
	static boolean isIdentifierContinuation( final char ch ) {
		return Character.isLetterOrDigit( ch ) || ch == '_';
	}
	
	private String readIdentiferText() {
		StringBuilder sofar = new StringBuilder();
		for (;;) {
			final char ch = this.cucharin.peekChar( ' ' );	// A character that's not part of an identifier.
			if ( isIdentifierContinuation( ch ) ) {
				this.cucharin.skipChar();
				sofar.append( ch );
			} else {
				break;
			}
		}
		return sofar.toString();
	}	
	
	private void readIdentifier() {
		final String identifier = readIdentiferText();
		if ( "null".equals( identifier ) ) {
			this.builder.addNull();
		} else if ( "true".equals( identifier ) ) {
			this.builder.addBoolean( true );
		} else if ( "false".equals( identifier ) ) {
			this.builder.addBoolean( false );
		} else {
			throw new Alert( "Unrecognised identifier" ).culprit(  "Identifier", identifier );
		}
	}
	
	void readEscapeChar( final StringBuilder sofar ) {
		final char ch = this.cucharin.nextChar();
		switch ( ch ) {
			case 'n':
				sofar.append( '\n' );
				break;
			case 'r':
				sofar.append( '\r' );
				break;
			case 't':
				sofar.append( '\t' );
				break;
			case 'f':
				sofar.append( '\f' );
				break;
			case 'b':
				sofar.append( '\b' );
				break;
			default:
				sofar.append( ch );
				break;
		}
	}
	
	private String readStringText() {
		final char quote_char = this.cucharin.nextChar();
		StringBuilder sofar = new StringBuilder();
		boolean done = false;
		
		while( ! done ) {
			final char ch = this.cucharin.nextChar();
			switch ( ch ) {
				case '"':
				case '\'':
					if ( ch == quote_char ) {
						done = true;
					} else {
						sofar.append( ch );
					}
					break;
				case '\\':
					this.readEscapeChar( sofar );
					break;
				default:
					sofar.append( ch );
					break;
			}
		}
		return sofar.toString();
	}
	
	private void readString() {
		this.builder.addString( this.readStringText() );
	}
	
	private void readArray() {
		this.builder.startArray();
		this.cucharin.skipChar();	//	Discard the '['
		if ( this.cucharin.peekChar() != ']' ) {
			this.readExpression();
			for (;;) {
				this.eatWhitespace();
				final char ch = this.cucharin.nextChar();
				if ( ch == ']' ) break;
				if ( ch != ',' ) {
					throw new Alert( "Missing separator" ).hint( "Expected ','" ).culprit( "Found", ch );
				}
				this.readExpression();
			}
		}
		this.builder.endArray();
	}
	
	private void readField() {
		if ( this.cucharin.peekChar() != '"' ) {
			throw new Alert( "Object field not a string" ).hint( "Must begin with a '\"' mark" ).culprit( "Found", this.cucharin.peekChar() );
		}
		this.builder.field( this.readStringText() );
	}
	
	private void readObject() {
		this.builder.startObject();
		this.cucharin.skipChar();	//	Discard the '{'
		if ( this.cucharin.peekChar() != '}' ) {
			this.eatWhitespace();
			this.readField();
			this.eatWhitespace();
			if ( this.cucharin.nextChar() != ':' ) {
				throw new Alert( "Missing ':' in object" );
			}
			this.readExpression();
			for (;;) {
				this.eatWhitespace();
				final char ch = this.cucharin.nextChar();
				if ( ch == '}' ) break;
				if ( ch != ',' ) {
					throw new Alert( "Missing separator" ).hint( "Expected ','" ).culprit( "Found", ch );
				}
				this.readField();
				this.eatWhitespace();
				if ( this.cucharin.nextChar() != ':' ) {
					throw new Alert( "Missing ':' in object" );
				}
				this.readExpression();
			}
		}
		this.builder.endObject();
	}
	
	
	
	private void readExpression() {
		this.eatWhitespace();
		final char ch = this.cucharin.peekChar();
		if ( Character.isDigit( ch ) || ch == '-' || ch == '+' ) {
			this.readNumber();
		} else if ( Character.isLetter( ch ) ) {
			this.readIdentifier();
		} else if ( ch == '"' ) {
			this.readString();
		} else if ( ch == '[' ) {
			this.readArray();
		} else if ( ch == '{' ) {
			this.readObject();
		} else {
			new Alert( "Unexpected character" ).culprit( "Character", ch );
		}
	}
	
	public T read() {
		this.readExpression();
		return this.builder.build();
	}

}
