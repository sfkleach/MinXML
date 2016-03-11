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

package com.steelypip.powerups.fusion;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.common.StdPair;

/**
 *	A convenience class that implements a recursive
 * 	walk over a MinXMLStar tree. 
 */
public abstract class FusionWalker {
	/**
	 * startWalk is called at the start of the tree-walk of the subject and its children. 
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void startWalk( String field, Fusion subject );
	
	private void startWalk( Map.Entry< String, Fusion > link ) {
		this.startWalk( link.getKey(), link.getValue() );
	}

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void endWalk( String field, Fusion subject );

	private void endWalk( Map.Entry< String, Fusion > link ) {
		this.endWalk( link.getKey(), link.getValue() );
	}


	
	/**
	 * The walk method is used to implement a depth-first, left-to-right recursive 
	 * scan over a tree. The startWalk and endWalk methods are invoked on the way
	 * down the tree and up the tree respectively. 
	 * 
	 * @param subject the element tree to be walked
	 * @return the walker itself, used for chaining method calls.
	 */	
	private FusionWalker walk( String field, final Fusion subject ) {
		this.startWalk( field, subject );
		for ( Map.Entry< String, Fusion > link : subject ) {
			this.walk( link.getKey(), link.getValue() );
		}
		this.endWalk( field, subject );
		return this;
	}
	
	public FusionWalker walk( final Fusion subject ) {
		return this.walk( "", subject );
	}
	
	
	/**
	 * This is a marker value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. 
	 */
	private static final Map.Entry< String, Fusion > end_walk_marker = new StdPair< String, Fusion >( "", new BadFusion() );
	
	static abstract class CommonIterator implements Iterator< Fusion > {
		
		protected final FusionWalker walker;
		protected final Deque< Map.Entry< String, Fusion > > queue = new ArrayDeque<>();
		
		public CommonIterator( FusionWalker walker, Fusion subject ) {
			this.walker = walker;
			this.queue.add( new StdPair< String, Fusion >( "", subject ) );
		}	

		protected void expand( final Map.Entry< String, Fusion > x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( end_walk_marker );
			List< Map.Entry< String, Fusion > > link_list = x.getValue().linksToList();
			final ListIterator< Map.Entry< String, Fusion > > it = link_list.listIterator( link_list.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}
		
	}
	
	static class PreOrderIterator extends CommonIterator {
		
		public PreOrderIterator( FusionWalker walker, Fusion subject ) {
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
		public Fusion next() {
			final Map.Entry< String, Fusion > x = queue.removeLast();
			if ( x != end_walk_marker ) {
				this.expand( x );
				return x.getValue();
			}
			this.walker.endWalk( queue.removeLast() );
			this.hasNext();
			return queue.removeLast().getValue();
		}
		
	}
	
	public Iterable< Fusion > preOrder( final @NonNull Fusion subject ) {
		return new Iterable< Fusion >() {
			@Override
			public Iterator< Fusion > iterator() {
				return new PreOrderIterator( FusionWalker.this, subject );
			}
		};
	}
	
	static class PostOrderIterator extends CommonIterator {
	
		public PostOrderIterator( @NonNull FusionWalker walker, @NonNull Fusion subject) {
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
				Map.Entry< String, Fusion > e = queue.removeLast();
				this.expand( e );
			}
			return false;
		}
		

		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext would sort it out.
		 */
		@Override
		public Fusion next() {
			final Map.Entry< String, Fusion > x = queue.removeLast();
			if ( x == end_walk_marker ) {
				//	The queue is normalised.
				//	This is the overwhelmingly common case - so we special case it.
				final Map.Entry< String, Fusion > e = queue.removeLast();
				this.walker.endWalk( e );
				return e.getValue();
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

	
	public Iterable< Fusion > postOrder( final @NonNull Fusion subject ) {
		return new Iterable< Fusion >() {
			@Override
			public Iterator< Fusion > iterator() {
				return new PostOrderIterator( FusionWalker.this, subject );
			}
		};
	}
	
}
