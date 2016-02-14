package com.steelypip.powerups.fusion;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public interface FusionImplementation {
	
	public @NonNull Fusion newIntegerFusion( long n );
	public @NonNull Fusion newFloatFusion( double d );
	public @NonNull Fusion newStringFusion( String s );
	public @NonNull Fusion newBooleanFusion( boolean b );
	public @NonNull Fusion newNullFusion();

	public @NonNull Fusion newMutableFusion( final @NonNull String name );
	
	default @Nullable Fusion specialiseAsArray( Fusion fusion ) { return null; }
	
	default @Nullable Fusion specialiseAsObject( Fusion fusion ) { return null; }
	
	default @Nullable Fusion specialiseAsInteger( Fusion fusion ) {
		final Long x = fusion.integerValue();
		return x != null ? this.newIntegerFusion( x ) : null;	
	}
	
	default @Nullable Fusion specialiseAsFloat( Fusion fusion ) {
		final Double x = fusion.floatValue();
		return x != null ? this.newFloatFusion( x ) : null;	
	}
	
	default @Nullable Fusion specialiseAsString( Fusion fusion ) {
		final String x = fusion.stringValue();
		return x != null ? this.newStringFusion( x ) : null;	
	}
	
	default @Nullable Fusion specialiseAsBoolean( Fusion fusion ) {
		final Boolean x = fusion.booleanValue();
		return x != null ? this.newBooleanFusion( x ) : null;	
	}
	
	default @Nullable Fusion specialiseAsNull( Fusion fusion ) {
		return fusion.isNull() ? this.newNullFusion() : null;
	}
	
	default @NonNull Fusion specialise( final @NonNull Fusion fusion ) {
		{ Fusion x = this.specialiseAsInteger( fusion ); if ( x != null ) return x; }
		{ Fusion x = this.specialiseAsFloat( fusion ); if ( x != null ) return x; }
		{ Fusion x = this.specialiseAsString( fusion ); if ( x != null ) return x; }
		{ Fusion x = this.specialiseAsBoolean( fusion ); if ( x != null ) return x; }
		{ Fusion x = this.specialiseAsNull( fusion ); if ( x != null ) return x; }
		{ Fusion x = this.specialiseAsArray( fusion ); if ( x != null ) return x; }
		{ Fusion x = this.specialiseAsObject( fusion ); if ( x != null ) return x; }
		return fusion;
	}

}
