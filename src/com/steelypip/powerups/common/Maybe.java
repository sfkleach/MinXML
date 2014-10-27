package com.steelypip.powerups.common;

import java.util.Iterator;

import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.alert.Alert;

public class Maybe< @Nullable T > implements Iterable< T > {

	private boolean has_value;
	
	private @Nullable T the_value;
	
	public static < T > Maybe< T > newMaybe() {
		return new Maybe< T >();
	}
	
	public static < T > Maybe< T > newMaybe( T x ) {
		return new Maybe< T >( x );
	}
	
	public Maybe( T x ) {
		this.has_value = true;
		this.the_value = x;
	}
	
	public Maybe() {
		this.has_value = false;
		this.the_value = null;
	}
	
	public boolean hasValue() {
		return this.has_value;
	}
	
	public T value() throws Alert {
		if ( this.has_value ) {
			return this.the_value;
		} else {
			throw new Alert( "Trying extract value from an empty Maybe" );
		}
	}
	
	public void setValue( T x ) {
		this.has_value = true;
		this.the_value = x;
	}
	
	public void unsetValue() {
		this.has_value = false;
		this.the_value = (@Nullable T)null;	//	free up for garbage collection.
	}
	
	public T otherwise( T default_value ) {
		if ( this.has_value ) {
			return this.the_value;
		} else {
			return default_value;
		}
	}
	
    public Maybe< T > otherwise( Maybe<T> maybe_default_value ) {
		if ( this.has_value ) {
			return this;
		} else {
			return maybe_default_value;
		}
    }
    
    public Iterator< T > iterator() {
    	if ( this.has_value ) {
    		return new Iterator< T >() {
    			boolean done = false;
    			
				@Override
				public boolean hasNext() {
					return ! this.done && Maybe.this.hasValue();
				}

				@Override
				public T next() {
					this.done = true;
					return Maybe.this.value();
				}

				@Override
				public void remove() {
					if ( this.done ) {
						Maybe.this.unsetValue();
					} else {
						throw new IllegalStateException( "Trying to remove from iterator before the next method has been used" );
					}
				}
    			
    		};
    	} else {
    		return new EmptyIterator< T >();
    	}
    }
}
