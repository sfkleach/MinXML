package com.steelypip.powerups.minxml;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinXMLStepper implements Iterator< MinXML > {
	
	public static enum HorizontalOrder {
		FORWARD,
		BACKWARD
	}
	
	public static enum VerticalOrder {
		DEPTH_FIRST,
		BREADTH_FIRST
	}
	
	private MinXML current;
	private Deque< MinXML > queue = new ArrayDeque< MinXML >();
	private boolean forward_traversal = true;
	private boolean depth_first_traversal = true;
	
	public MinXMLStepper( MinXML current ) {
		this.current = current;
	}
	
	public MinXMLStepper( MinXML current, HorizontalOrder forward_traversal, VerticalOrder depth_first_traversal ) {
		this.current = current;
		this.forward_traversal = forward_traversal == HorizontalOrder.FORWARD;
		this.depth_first_traversal = depth_first_traversal == VerticalOrder.DEPTH_FIRST;
	}
	
	public MinXMLStepper setBreadthFirst() {
		this.depth_first_traversal = false;
		return this;
	}
	
	public MinXMLStepper setReverseTraversal() {
		this.forward_traversal = false;
		return this;
	}
	
	public MinXML getCurrent() {
		return this.current;
	}
	
	public boolean include( MinXML subject ) {
		return true;
	}
	
	public boolean includeChildren( MinXML subject ) {
		return true;
	}
	
	private void addChildren( MinXML subject ) {
		if ( this.forward_traversal == this.depth_first_traversal ) {
			//	Add to the front in reverse order for forward, depth-first traversal.
			final int n = subject.size();
			for ( int i = n - 1; i >= 0; i -= 1 ) {
				final MinXML k = subject.get( i );
				if ( this.depth_first_traversal ) {
					//	Forward traversal, depth-first.
					this.queue.addFirst( k );
				} else {
					//	Reverse traversal, breath-first.
					this.queue.addLast( k );
				}
			}
		} else {
			for ( MinXML k : subject ) {
				if ( ! this.depth_first_traversal ) {
					//	Forward traversal, breadth-first.
					this.queue.addLast( k );
				} else {
					//	Reverse traversal, depth-first.
					this.queue.addFirst( k );
				}
			}
		}
	}
	
	public MinXML advance() {
		if ( this.includeChildren( this.current ) ) {
			this.addChildren( this.current );
		}
		return this.current = queue.pollFirst();
	}
	
	public boolean hasNext() {
		return this.current != null;
	}
	
	public MinXML next() {
		for (;;) {
			MinXML next_element = this.getCurrent();
			if ( next_element == null ) {
				throw new NoSuchElementException();
			}
			this.advance();
			if ( this.include( next_element ) ) {
				return next_element;
			}
		}
	}

}
