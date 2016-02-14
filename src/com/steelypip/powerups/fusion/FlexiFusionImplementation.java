package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;

public class FlexiFusionImplementation implements FusionImplementation, LiteralConstants {

	@SuppressWarnings("null")
	@Override
	public @NonNull Fusion newIntegerFusion( final long n ) {
		final Fusion f = new FlexiFusion( this.constConstant() );
		f.addValue( this.constType(), this.constInteger() );
		f.addValue( this.constValue(), Long.toString( n ) );
		return f;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Fusion newFloatFusion( final double d ) {
		final Fusion f = new FlexiFusion( this.constConstant() );
		f.addValue( this.constType(), this.constFloat() );
		f.addValue( this.constValue(), Double.toString( d ) );
		return f;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Fusion newStringFusion( final String s ) {
		final Fusion f = new FlexiFusion( this.constConstant() );
		f.addValue( this.constType(), this.constString() );
		f.addValue( this.constValue(), s );
		return f;
	}

	@SuppressWarnings("null")
	@Override
	public @NonNull Fusion newBooleanFusion( final boolean b ) {
		final Fusion f = new FlexiFusion( this.constConstant() );
		f.addValue( this.constType(), this.constBoolean() );
		f.addValue( this.constValue(), Boolean.toString( b ) );
		return f;
	}

	@Override
	public @NonNull Fusion newNullFusion() {
		final Fusion f = new FlexiFusion( this.constConstant() );
		f.addValue( this.constType(), this.constNullType() );
		f.addValue( this.constValue(), this.constNull() );
		return f;
	}

	@Override
	public @NonNull Fusion newMutableFusion( @NonNull String name ) {
		return new FlexiFusion( name );
	}

}
