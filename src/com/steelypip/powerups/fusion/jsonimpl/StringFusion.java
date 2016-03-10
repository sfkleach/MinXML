package com.steelypip.powerups.fusion.jsonimpl;

import org.eclipse.jdt.annotation.NonNull;

public class StringFusion extends AbsConstantFusion {
	
	private String string;

	public StringFusion( String n ) {
		this.string = n;
	}

//	@Override
//	public @NonNull Fusion shallowCopy() {
//		return new IntegerFusion( this.number );
//	}

	@Override
	protected @NonNull String internedType() {
		return this.constTypeInteger();
	}

	@SuppressWarnings("null")
	@Override
	protected @NonNull String literalValue() {
		return this.string;
	}

	@Override
	protected void setValueAttribute( String new_value ) throws UnsupportedOperationException {
		this.string = new_value;
	}

	@Override
	public String stringValue( String otherwise ) {
		return this.string;
	}

}
