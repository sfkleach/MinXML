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

package com.steelypip.powerups.minxmlstar;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.annotation.NonNull;

/**
 *	A convenience class that implements a recursive
 * 	walk over a MinXMLStar tree. 
 */
public abstract class MinXMLStarWalker {
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void startWalk( @NonNull String field, int index, MinXMLStar subject );
	
	private void startWalk( MinXMLStar.Link link ) {
		this.startWalk( link.getField(), link.getFieldIndex(), link.getChild() );
	}

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void endWalk( @NonNull String field, int index, MinXMLStar subject );

	private void endWalk( MinXMLStar.Link link ) {
		this.endWalk( link.getField(), link.getFieldIndex(), link.getChild() );
	}


	
	/**
	 * The walk method is used to implement a depth-first, left-to-right recursive 
	 * scan over a tree. The startWalk and endWalk methods are invoked on the way
	 * down the tree and up the tree respectively. 
	 * 
	 * @param subject the element tree to be walked
	 * @return the walker itself, used for chaining method calls.
	 */	
	private MinXMLStarWalker walk( @NonNull String field, int index, final MinXMLStar subject ) {
		this.startWalk( field, index, subject );
		for ( MinXMLStar.Link link : subject ) {
			this.walk( link.getField(), link.getFieldIndex(), link.getChild() );
		}
		this.endWalk( field, index, subject );
		return this;
	}
	
	public MinXMLStarWalker walk( final MinXMLStar subject ) {
		return this.walk( "", 0, subject );
	}
	
	
	/**
	 * This is a marker value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. 
	 */
	private static final MinXMLStar.Link end_walk_marker = new FlexiMinXMLStar.Link( "", 0, new BadMinXMLStar() );
	
	static abstract class CommonIterator implements Iterator< @NonNull MinXMLStar > {
		
		protected final @NonNull MinXMLStarWalker walker;
		protected final Deque< MinXMLStar.Link > queue = new ArrayDeque<>();
		
		public CommonIterator( @NonNull MinXMLStarWalker walker, @NonNull MinXMLStar subject ) {
			this.walker = walker;
			this.queue.add( new FlexiMinXMLStar.Link( "", 0, subject ) );
		}	

		protected void expand( final MinXMLStar.Link x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( end_walk_marker );
			List< MinXMLStar.Link > link_list = x.getChild().linksToList();
			final ListIterator< MinXMLStar.Link > it = link_list.listIterator( link_list.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}
		
	}
	
	static class PreOrderIterator extends CommonIterator {
		
		public PreOrderIterator( @NonNull MinXMLStarWalker walker, @NonNull MinXMLStar subject ) {
			super( walker, subject );
		}	
		
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				if ( end_walk_marker != queue.getLast() ) return true;
				queue.removeLast();
				this.walker.endWalk( queue.removeLast() );
			}
			return false;
		}
		
		@Override
		public @NonNull MinXMLStar next() {
			final MinXMLStar.Link x = queue.removeLast();
			if ( x != end_walk_marker ) {
				this.expand( x );
				return x.getChild();
			}
			this.walker.endWalk( queue.removeLast() );
			this.hasNext();
			return queue.removeLast().getChild();
		}
		
	}
	
	public Iterable< @NonNull MinXMLStar > preOrder( final @NonNull MinXMLStar subject ) {
		return new Iterable< @NonNull MinXMLStar >() {
			@Override
			public Iterator< @NonNull MinXMLStar > iterator() {
				return new PreOrderIterator( MinXMLStarWalker.this, subject );
			}
		};
	}
	
	static class PostOrderIterator extends CommonIterator {
	
		public PostOrderIterator( @NonNull MinXMLStarWalker walker, @NonNull MinXMLStar subject) {
			super( walker, subject );
		}	
		
		/**
		 * Advances the queue so that the head is a sentinel value. We
		 * call this the normal situation. 
		 * @return the queue has items in it.
		 */
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				if ( end_walk_marker == queue.getLast() ) return true;
				MinXMLStar.Link e = queue.removeLast();
				this.expand( e );
			}
			return false;
		}
		

		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext would sort it out.
		 */
		@Override
		public @NonNull MinXMLStar next() {
			final MinXMLStar.Link x = queue.removeLast();
			if ( x == end_walk_marker ) {
				//	The queue is normalised.
				//	This is the overwhelmingly common case - so we special case it.
				final MinXMLStar.Link e = queue.removeLast();
				this.walker.endWalk( e );
				return e.getChild();
			} else {
				//	The queue was not normalised by a call to hasNext and the
				//	head of the queue was not a request for an end-visit. We
				//	Must process this element, normalise the queue and re-try.
				this.expand( x );
				this.hasNext();
				return this.next();
			}
		}		
	}

	
	public Iterable< @NonNull MinXMLStar > postOrder( final @NonNull MinXMLStar subject ) {
		return new Iterable< @NonNull MinXMLStar >() {
			@Override
			public Iterator< @NonNull MinXMLStar > iterator() {
				return new PostOrderIterator( MinXMLStarWalker.this, subject );
			}
		};
	}
	
}
