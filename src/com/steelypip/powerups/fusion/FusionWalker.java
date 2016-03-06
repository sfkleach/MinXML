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

import org.eclipse.jdt.annotation.NonNull;

import com.steelypip.powerups.hydra.Link;
import com.steelypip.powerups.hydra.StdLink;

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
	public abstract void startWalk( @NonNull String field, int index, Fusion subject );
	
	private void startWalk( Link< String, Fusion > link ) {
		this.startWalk( link.getField(), link.getFieldIndex(), link.getChild() );
	}

	
	/**
	 * endWalk is called at the end of the tree-walk of the subject and its children.
	 * It will be called once for each node in the tree.
	 * 
	 * @param subject the MinXML element to be visited
	 */
	public abstract void endWalk( @NonNull String field, int index, Fusion subject );

	private void endWalk( Link< String, Fusion > link ) {
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
	private FusionWalker walk( @NonNull String field, int index, final Fusion subject ) {
		this.startWalk( field, index, subject );
		for ( Link< String, Fusion > link : subject ) {
			this.walk( link.getField(), link.getFieldIndex(), link.getChild() );
		}
		this.endWalk( field, index, subject );
		return this;
	}
	
	public FusionWalker walk( final Fusion subject ) {
		return this.walk( "", 0, subject );
	}
	
	
	/**
	 * This is a marker value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. 
	 */
	private static final Link< String, Fusion > end_walk_marker = new StdLink< String, Fusion >( "", 0, new BadFusion() );
	
	static abstract class CommonIterator implements Iterator< @NonNull Fusion > {
		
		protected final @NonNull FusionWalker walker;
		protected final Deque< Link< String, Fusion > > queue = new ArrayDeque<>();
		
		public CommonIterator( @NonNull FusionWalker walker, @NonNull Fusion subject ) {
			this.walker = walker;
			this.queue.add( new StdLink< String, Fusion >( "", 0, subject ) );
		}	

		protected void expand( final Link< String, Fusion > x ) {
			this.walker.startWalk( x );
			queue.addLast( x );
			queue.addLast( end_walk_marker );
			List< Link< String, Fusion > > link_list = x.getChild().linksToList();
			final ListIterator< Link< String, Fusion > > it = link_list.listIterator( link_list.size() );
			while ( it.hasPrevious() ) {
				queue.addLast( it.previous() );
			}						
		}
		
	}
	
	static class PreOrderIterator extends CommonIterator {
		
		public PreOrderIterator( @NonNull FusionWalker walker, @NonNull Fusion subject ) {
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
		public @NonNull Fusion next() {
			final Link< String, Fusion > x = queue.removeLast();
			if ( x != end_walk_marker ) {
				this.expand( x );
				return x.getChild();
			}
			this.walker.endWalk( queue.removeLast() );
			this.hasNext();
			return queue.removeLast().getChild();
		}
		
	}
	
	public Iterable< @NonNull Fusion > preOrder( final @NonNull Fusion subject ) {
		return new Iterable< @NonNull Fusion >() {
			@Override
			public Iterator< @NonNull Fusion > iterator() {
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
				Link< String, Fusion > e = queue.removeLast();
				this.expand( e );
			}
			return false;
		}
		

		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext would sort it out.
		 */
		@Override
		public @NonNull Fusion next() {
			final Link< String, Fusion > x = queue.removeLast();
			if ( x == end_walk_marker ) {
				//	The queue is normalised.
				//	This is the overwhelmingly common case - so we special case it.
				final Link< String, Fusion > e = queue.removeLast();
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

	
	public Iterable< @NonNull Fusion > postOrder( final @NonNull Fusion subject ) {
		return new Iterable< @NonNull Fusion >() {
			@Override
			public Iterator< @NonNull Fusion > iterator() {
				return new PostOrderIterator( FusionWalker.this, subject );
			}
		};
	}
	
}
