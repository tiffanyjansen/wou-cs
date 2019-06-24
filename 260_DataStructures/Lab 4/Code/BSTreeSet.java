package edu.wou.cs260.SpellChecker;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * The BSTreeSet class for Lab 3.
 * @author Tiffany Jansen
 * @version 1.1
 * @param <T>; The generic type to be stored in the list.
 */
public class BSTreeSet<T extends Comparable<T>> implements Set<T>, CompareCount {
	protected Node root;
	protected int size;
	private int compareCount;
	
	/**
	 * Default constructor for the BSTreeSet. 
	 */
	public BSTreeSet() {
		root = null;
		size = 0;
	}
	
	/**
	 * The inner class for the Nodes to be contained in the BSTreeSet
	 * @author Tiffany Jansen
	 * @version 1.0
	 */
	public class Node {
		public T item;
		public int height;
		public Node lChild;
		public Node rChild;
		public int balanceFactor;
		 
		/**
		 * Default Constructor. Sets everything to null.
		 */
		public Node() {
			this(null, null, null);
		}
		
		/**
		 * Non-Default Constructor. Sets the item stored in the Node.
		 * @param item; the item stored in the Node.
		 */
		public Node(T item) {
			this(null, item, null);
		}
		
		/**
		 * Non-Default Constructor. Sets everything to the values passed in.
		 * @param lChild; the left child of the Node. (Lesser Value.)
		 * @param item; the item stored in the Node.
		 * @param rChild; the right child of the Node. (Greater Value.)
		 */
		public Node(Node lChild, T item, Node rChild) {
			this.lChild = lChild;
			this.item = item;
			this.rChild = rChild;
			balanceFactor = 0;
			height = 0;
		}
	}
	
	/**
	 * Returns the compare count from the last time the contains method was ran.
	 * @return compareCount; the compare count of the contains method.
	 */
	public int getLastCompareCount() {
		return compareCount;
	}

	/**
	 * Returns the size of the tree set.
	 * @return size; the size of the tree set.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks to see if the tree set is empty or not.
	 * @return true; if the list is empty.
	 */
	public boolean isEmpty() {
		if(root == null) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Decides if the object passed in is contained in the tree set.
	 * @param obj; the object to be tested.
	 * @return true; if the object is found in the set
	 * @throws NullPointerException; if obj is null.
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(Object obj) {
		if(obj == null) {
			throw new NullPointerException();
		}
		else if(isEmpty()) {
			return false;
		}
		compareCount = 0;
		Node current = findData(root, (T) obj);
		if(current == null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Finds the Node with the given data stored in it. Used in contains to check
	 * if the set contains the data.
	 * @param node; the node to compare the data to.
	 * @param data; the data we wish to find.
	 * @return Node; the node with the given item stored in it.
	 */
	private Node findData(Node node, T data) {
		int compareInt = data.compareTo(node.item);
		compareCount++;
		if(compareInt == 0) {
			return node;
		}
		else if(compareInt < 0) {
			if(node.lChild != null) {
				return findData(node.lChild, data);
			}
			else {
				return null;
			}
		}
		else {
			if(node.rChild != null) {
				return findData(node.rChild, data);
			}
			else {
				return null;
			}
		}
	}

	/**
	 * Iterator to iterate through the list and get the values we want.
	 * @returns Iterator<T>
	 */
	public Iterator<T> iterator() {
		class TreeIterator implements Iterator<T> {
			private Node node;
			private Queue<Node> queue;
			private Queue<Node> queueList;
			
			/**
			 * Constructor for the iterator.
			 */
			public TreeIterator(){
				node = root;
				queue = new DLList<Node>();
				queueList = new DLList<Node>();
				queueList();
			}
			
			/**
			 * Adds everything to the Queue in breadth-first order so
			 * the iterator will pass the JUnitTests.
			 */
			private void queueList() {
				queue.add(node);
				while(!queue.isEmpty()) {
					node = queue.remove();
					queueList.offer(node);
					if(node.lChild != null) {
						queue.add(node.lChild);
					}
					if(node.rChild != null) {
						queue.add(node.rChild);
					}
				}
			}
			
			/**
			 * Checks to see if the list has another value in it or not.
			 * @returns true; if there is more in the list.
			 */
			public boolean hasNext() {
				if(!queueList.isEmpty()) {
					return true;
				}
				else {
					return false;				
				}
			}

			/**
			 * Returns the next value in the list.
			 * @return T; the next item in the list.
			 */
			public T next() {
				node = queueList.remove();
				return node.item;
			}
			
		}
		return new TreeIterator();
	}

	@Override
	/**
	 * Turns the set into an array.
	 * @return Object[]; the array with all the data stored in it.
	 */
	public Object[] toArray() {
		Object[] array = new Object[size];
		DLList<T> arrayList = InOrder(root, new DLList<T>());
		for(int i = 0; i < size; i++) {
			array[i] = arrayList.remove();
		}
		return array;
	}
	
	/**
	 * Puts all the items in order and adds them to a list to be used
	 * by the toArray() method to make an array with the data.
	 * @param node; The node to be placed in order.
	 * @param array; the array to add the items to.
	 * @return DLList<T>; the list to be used to create the array.
	 */
	private DLList<T> InOrder(Node node, DLList<T> array) {
		if(node != null) {
			InOrder(node.lChild, array);
			array.add(node.item);
			InOrder(node.rChild, array);
		}
		return array;
	}
	
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds the data to the list in the correct spot.
	 * @param data; the data to be added to the set.
	 * @return true; if the data was added.
	 * @throws NullPointerException; if data is null.
	 */
	public boolean add(T data) {
		if(data == null) {
			throw new NullPointerException();
		}
		if(isEmpty()) {
			Node node = new Node(data);
			root = node;
			size++;
			return true;
		}
		else {
			return compareAdd(root, data);
		}
	}
	
	/**
	 * Compares and adds the node to the list.
	 * @param node; the node to compare the node to be added to.
	 * @param dat; the item stored in the node to add.
	 * @return true; if the Node was added to the set.
	 */
	private boolean compareAdd(Node node, T dat) {
		int compareInt = dat.compareTo(node.item);
		Node addNode = new Node(dat);
		boolean found = false;
		if(compareInt < 0) {
			if(node.lChild == null) {
				size++;
				node.lChild = addNode;
				found = true;
			}
			else {
				return compareAdd(node.lChild, dat);
			}
		}
		else {
			if(node.rChild == null) {
				size++;
				node.rChild = addNode;
				found = true;
			}
			else {
				return compareAdd(node.rChild, dat);
			}
		}
		fixHeight(root);
		return found;
	}

	@Override
	public boolean remove(Object obj) {
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Clears the tree set.
	 */
	public void clear() {
		root = null;
		size = 0;		
	}
	
	/**
	 * Fixes the height of the Node that is passed in.
	 * @param node; the node for the height to be fixed.
	 */
	public int fixHeight(Node node) {
		if(node == null) {
			return -1;
		}
		else {
			int heightLeft = fixHeight(node.lChild);
			int heightRight = fixHeight(node.rChild);
			node.height = (Math.max(heightLeft, heightRight) + 1);
			return node.height;
		}
	}
	
	/**
	 * 
	 * For Testing Purposes only. 
	 * Prints heights of root, the root's left child, and the root's right child.
	 * (In that order).
	 */
	public void printHeight() {
		System.out.println(root.height);
		
	}
	
	/**
	 * The main method to do some of my own testing.
	 * @param args; required for the main method.
	 */
	public static void main(String[] args) {
		BSTreeSet<Integer> testSet = new BSTreeSet<Integer>( ); 
		testSet.clear( );
		testSet.add(10);
		testSet.add(5);
		testSet.add(30);
		testSet.add(40);
		testSet.add(15);
		testSet.add(25);
		testSet.add(35);
		testSet.add(45);
		testSet.add(8);
		testSet.add(7);
		testSet.add(9);
		testSet.add(3);
		for(Integer i : testSet) {
			System.out.println(i);
		}
		
		System.out.println();
		System.out.println(testSet.root.height);
		if(testSet.root.lChild != null) {
			System.out.println(testSet.root.lChild.height);
		}
		if(testSet.root.rChild != null) {
			System.out.println(testSet.root.rChild.height);
		}
	}
}
