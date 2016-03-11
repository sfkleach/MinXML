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
import org.eclipse.jdt.annotation.Nullable;

import com.steelypip.powerups.common.EmptyIterator;
import com.steelypip.powerups.hydra.Link;
import com.steelypip.powerups.hydra.StdLink;

/**
 * A convenience class for implementing a recursive walk of a tree
 * with some control over the branches explored.
 */
public abstract class FusionSearcher {

	/**
	 * startSearch is called at the start of the tree-walk of the subject and its children. 
	 * Return false if you want the the child elements to be visited, true otherwise.
	 * 
	 * @param subject the MinXML element to be visited
	 * @return flag saying if the children were cutoff.
	 */
	public abstract boolean startSearch( String field, Fusion subject );
	
	public boolean startSearch( final Map.Entry< String, Fusion > link ) {
		return this.startSearch( link.getKey(), link.getValue() );
	}

	
	/**
	 * endSearch is called at the end of the tree-walk of the subject and its children.
	 * Return false normally but true to signal to the parent that the iteration over
	 * the child nodes can be stopped early. The found flag indicates if
	 * any child search found the goal, it is the short-circuit OR of all the
	 * flags returned by the children's endVisits.
	 * 
	 * @param subject the MinXML element to be visited.
	 * @param cutoff flag indicating if any child search was cutoff.
	 * @return boolean a flag saying if sibling-search should be cutoff.
	 */
	public abstract boolean endSearch( String field, Fusion subject, boolean cutoff );

	public boolean endSearch( final Map.Entry< String, Fusion > link, boolean cutoff ) {
		return this.endSearch( link.getKey(), link.getValue(), cutoff );
	}


	
	private static final Iterator< Map.Entry< String, Fusion > > empty = new EmptyIterator<>();
	
	/**
	 * The search method is used to implement basic recursive scans over a tree
	 * of elements. It is typically used to search a tree or to implement a series of
	 * in-place updates.
	 * 
	 * @param subject the element tree to be searched
	 * @return a successful node, otherwise null.
	 */
	public @Nullable Fusion search( String field, final Fusion subject ) {
		Fusion cutoff = this.startSearch( field, subject ) ? subject : null;
		final Iterator< Map.Entry< String, Fusion > > kids = cutoff != null ? empty : subject.iterator();
		while ( cutoff == null && kids.hasNext() ) {
			cutoff = this.search( kids.next() );
		}
		return this.endSearch( field, subject, cutoff != null ) ? ( cutoff != null ? cutoff : subject ) : null;
	}
	
	public @Nullable Fusion search( final Map.Entry< String, Fusion > link ) {
		return this.search( link.getKey(), link.getValue() );
	}

	
	public @Nullable Fusion search( final Fusion subject ) {
		return this.search(  "", subject );
	}
	
	/**
	 * This are a pair of markers value used to mark the element 'below' it (lower index)
	 * in the queue. Marked items represent 'endWalk' tasks and unmarked items
	 * represent 'startWalk + expand' tasks. The choice of marker is used to signal
	 * true/false.
	 */
	private static final Link< String, Fusion > cutoff_true = new StdLink< String, Fusion >( "", 0, new BadFusion() );
	private static final Link< String, Fusion > cutoff_false = new StdLink< String, Fusion >( "", 0, new BadFusion() );
	
	static abstract class CommonMethodsPreOrderIterator implements Iterator< Fusion > {
		
		protected final FusionSearcher searcher;
		protected final Deque< Map.Entry< String, Fusion > > queue = new ArrayDeque<>();
		
		public CommonMethodsPreOrderIterator( FusionSearcher searcher, @NonNull Fusion subject ) {
			this.searcher = searcher;
			this.queue.add( new StdLink< String, Fusion >( "", 0, subject ) );
		}			

		protected void cutAwaySiblings() {
			//	We have to cut away the siblings.
			while ( ! queue.isEmpty() ) {
				final Map.Entry< String, Fusion > last = queue.removeLast();
				if ( cutoff_false == last || cutoff_true == last ) break;
			}
			queue.addLast( cutoff_true );
		}
		
		protected void expand( final Map.Entry< String, Fusion > x ) {
			final boolean cutoff = this.searcher.startSearch( x );
			queue.addLast( x );
			queue.addLast( cutoff ? cutoff_true : cutoff_false );
			if ( ! cutoff ) {
				final List< Map.Entry< String, Fusion > > link_list = x.getValue().linksToList();
				final ListIterator< Map.Entry< String, Fusion > > it = link_list.listIterator( link_list.size() );
				while ( it.hasPrevious() ) {
					queue.addLast( it.previous() );
				}				
			}
		}
	
	}
	
	static class SearcherPreOrderIterator extends CommonMethodsPreOrderIterator {
		
		public SearcherPreOrderIterator( FusionSearcher searcher, @NonNull Fusion subject ) {
			super( searcher, subject );
		}	

		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				final Map.Entry< String, Fusion > last = queue.getLast();
				if ( cutoff_false != last && cutoff_true != last ) return true;
				queue.removeLast();
				final boolean cutoff = this.searcher.endSearch( queue.removeLast(), cutoff_true == last );
				if ( cutoff ) {
					this.cutAwaySiblings();
				}
			}
			return false;
		}

		@Override
		public Fusion next() {
			final Map.Entry< String, Fusion > x = queue.removeLast();
			if ( cutoff_false != x && cutoff_true != x ) {
				this.expand( x );
				return x.getValue();
			} else {
				queue.addLast( x );
				this.hasNext();
				return this.next();
			}
		}
		
	}
	
	public Iterable< Fusion > preOrder( final @NonNull Fusion subject ) {
		return new Iterable< Fusion >() {
			@Override
			public Iterator< Fusion > iterator() {
				return new SearcherPreOrderIterator( FusionSearcher.this, subject );
			}
		};
	}
	
	static class SearcherPostOrderIterator extends CommonMethodsPreOrderIterator {
		
		public SearcherPostOrderIterator( FusionSearcher searcher, @NonNull Fusion subject ) {
			super( searcher, subject );
		}	
		
		/**
		 * This not only returns if there is a next member but normalises
		 * the queue. In the case of a post-order traversal, the queue is
		 * normal we
		 */
		@Override
		public boolean hasNext() {
			while ( ! queue.isEmpty() ) {
				final Map.Entry< String, Fusion > last = queue.getLast();
				if ( cutoff_false == last || cutoff_true == last ) return true;
				this.expand( queue.removeLast() );
			}
			return false;
		}
		
		/**
		 * After this method the queue is NOT normal. A call to
		 * hasNext will sort it out.
		 */
		@Override
		public Fusion next() {
			final Map.Entry< String, Fusion > x = queue.removeLast();
			if ( cutoff_false == x || cutoff_true == x ) {
				final Map.Entry< String, Fusion > last = queue.removeLast();
				final boolean cutoff = this.searcher.endSearch( last, cutoff_true == x );
				if ( cutoff ) {
					this.cutAwaySiblings();
				}
				return last.getValue();
			} else {
				queue.addLast( x );
				this.hasNext();
				return this.next();
			}
		}

	}
	
	public Iterable< Fusion > postOrder( final @NonNull Fusion subject ) {
		return new Iterable< Fusion >() {
			@Override
			public Iterator< Fusion > iterator() {
				return new SearcherPostOrderIterator( FusionSearcher.this, subject );
			}
		};
	}
	
}
