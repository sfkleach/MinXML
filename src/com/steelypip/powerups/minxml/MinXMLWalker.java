/**
 * Copyright Stephen Leach, 2014
 * This file is part of the MinXML for Java library.
 * 
 * MinXML for Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MinXML for Java.  If not, see <http://www.gnu.org/licenses/>.
 *  
 */

package com.steelypip.powerups.minxml;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;

public abstract class MinXMLWalker {
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void startWalk( MinXML subject );

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void endWalk( MinXML subject );
	

	
	/**
	 * The walk method is used to implement a depth-first, left-to-right recursive 
	 * scan over a tree. The startWalk and endWalk methods are invoked on the way
	 * down the tree and up the tree respectively. 
	 * 
	 * @param subject
	 */
	public void walk( final MinXML subject ) {
		this.startWalk( subject );
		for ( MinXML kid : subject ) {
			this.walk(  kid );
		}
		this.endWalk( subject );
	}
	
	
	/**
	 * This is a marker value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. 
	 */
	private static final MinXML marker = new BadMinXML();
	
	static class PreOrderIterator implements Iterator< MinXML > {
		
		final MinXMLWalker walker;
		final Deque< MinXML > queue;
		
		public PreOrderIterator( MinXMLWalker walker, Deque< MinXML > queue ) {
			this.walker = walker;
			this.queue = queue;
		}	
		
		private boolean advance() {
			while ( ! queue.isEmpty() ) {
				if ( marker != queue.getLast() ) return true;
				queue.removeLast();
				this.walker.endWalk( queue.removeLast() );
			}
			return false;
		}

		@Override
		public boolean hasNext() {
			return this.advance();
		}
		
		private void expand( final MinXML x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( marker );
			final ListIterator< MinXML > it = x.listIterator( x.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}

		@Override
		public MinXML next() {
			final MinXML x = queue.removeLast();
			if ( x != marker ) {
				this.expand( x );
				return x;
			}
			this.walker.endWalk( queue.removeLast() );
			this.advance();
			return queue.removeLast();
		}
		
	}
	
	static class PreOrderIterable implements Iterable< MinXML > {
		
		final MinXMLWalker walker;
		final MinXML subject;
		
		public PreOrderIterable( MinXMLWalker walker, MinXML subject ) {
			this.walker = walker;
			this.subject = subject;
		}

		@Override
		public Iterator< MinXML > iterator() {
			final Deque< MinXML > queue = new ArrayDeque< MinXML >();
			queue.add( this.subject );
			return new PreOrderIterator( this.walker, queue );
		}
		
	}
	
	public Iterable< MinXML > preOrder( final MinXML subject ) {
		return new PreOrderIterable( this, subject );
	}
	
	static class PostOrderIterator implements Iterator< MinXML > {
		
		final MinXMLWalker walker;
		final Deque< MinXML > queue;
		
		public PostOrderIterator( MinXMLWalker walker, Deque< MinXML > queue ) {
			this.walker = walker;
			this.queue = queue;
		}	
		
		
		private void expand( final MinXML x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( marker );
			final ListIterator< MinXML > it = x.listIterator( x.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}
		
		/**
		 * Advances the queue so that the head is a sentinel value. We
		 * call this the normal situation. 
		 * @return the queue has items in it.
		 */
		private boolean advance() {
			while ( ! queue.isEmpty() ) {
				if ( marker == queue.getLast() ) return true;
				MinXML e = queue.removeLast();
				this.expand( e );
			}
			return false;
		}

		@Override
		public boolean hasNext() {
			return this.advance();
		}
		

		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext would sort it out.
		 */
		@Override
		public MinXML next() {
			final MinXML x = queue.removeLast();
			if ( x == marker ) {
				//	The queue is normalised.
				//	This is the overwhelmingly common case - so we special case it.
				final MinXML e = queue.removeLast();
				this.walker.endWalk( e );
				return e;
			} else {
				//	The queue was not normalised by a call to hasNext and the
				//	head of the queue was not a request for an end-visit. We
				//	Must process this element, normalise the queue and re-try.
				this.expand( x );
				this.advance();
				return this.next();
			}
		}		
	}

	static class PostOrderIterable implements Iterable< MinXML > {
		
		final MinXMLWalker walker;
		final MinXML subject;
		
		public PostOrderIterable( MinXMLWalker walker, MinXML subject ) {
			this.walker = walker;
			this.subject = subject;
		}

		@Override
		public Iterator< MinXML > iterator() {
			final Deque< MinXML > queue = new ArrayDeque< MinXML >();
			queue.add( this.subject );
			return new PostOrderIterator( this.walker, queue );
		}
		
	}
	
	public Iterable< MinXML > postOrder( final MinXML subject ) {
		return new PostOrderIterable( this, subject );
	}
	
}
