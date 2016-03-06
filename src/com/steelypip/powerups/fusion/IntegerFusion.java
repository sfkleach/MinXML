package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public class IntegerFusion extends AbsConstantFusion {
	
	private long number;


	public IntegerFusion( long n ) {
		this.number = n;
	}

//	@Override
//	public @NonNull Fusion shallowCopy() {
//		return new IntegerFusion( this.number );
//	}

	@Override
	protected @NonNull String internedType() {
		return "integer";
	}

	@SuppressWarnings("null")
	@Override
	protected @NonNull String literalValue() {
		return Long.toString( this.number );
	}

	@Override
	protected void setValueAttribute( String new_value ) throws UnsupportedOperationException {
		try {
			this.number = Long.parseLong( new_value );
		} catch ( NumberFormatException _e ) {
			throw new UnsupportedOperationException();
		}
	}
	
	

}
