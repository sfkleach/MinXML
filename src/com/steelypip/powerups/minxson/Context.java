package com.steelypip.powerups.minxson;

enum Context {
	InArray,
	InEmbeddedArray,
	InObject,
	InEmbeddedObject,
	InEmbeddedParentheses,
	InElement,
	InAtom;
}