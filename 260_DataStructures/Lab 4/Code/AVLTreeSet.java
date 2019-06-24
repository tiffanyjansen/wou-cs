package edu.wou.cs260.SpellChecker;

//import edu.wou.cs260.SpellChecker.BSTreeSet.Node;

/**
 * AVL Tree Set
 * @author Tiffany Jansen
 * @version 11.06.2017
 *
 * @param <T>; the type of data to be held by the AVL Tree.
 */
public class AVLTreeSet<T extends Comparable<T>> extends BSTreeSet<T> {
	
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
		else if(isEmpty()) {
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
				node.lChild = addNode;
				size++;
				found = true;
			}
			else {
				return compareAdd(node.lChild, dat);
			}
		}
		else {
			if(node.rChild == null) {
				node.rChild = addNode;
				size++;
				found = true;
			}
			else {
				return compareAdd(node.rChild, dat);
			}
		}
		fixHeight(root);
		fixBalanceFactor(root);
		balance(root);
		return found;
	}
	
	private Node singleRotateRight(Node oldRoot) {
		Node newRoot = oldRoot.lChild;
		if(newRoot.rChild != null) {
			oldRoot.lChild = newRoot.rChild;
		}
		else {
			oldRoot.lChild = null;
		}
		newRoot.rChild = oldRoot;
		if(oldRoot == root) {
			root = newRoot;
		}
		return newRoot;
	}
	
	private Node singleRotateLeft(Node oldRoot) {
		Node newRoot = oldRoot.rChild;
		if(newRoot.lChild != null) {
			oldRoot.rChild = newRoot.lChild;
		}
		else {
			oldRoot.rChild = null;
		}
		newRoot.lChild = oldRoot;
		if(oldRoot == root) {
			root = newRoot;
		}
		return newRoot;
	}
	
	private Node doubleRotateRight(Node oldRoot) {
		oldRoot.lChild = singleRotateLeft(oldRoot.lChild);
		return singleRotateRight(oldRoot);
	}
	
	private Node doubleRotateLeft(Node oldRoot) {
		oldRoot.rChild = singleRotateRight(oldRoot.rChild);
		return singleRotateLeft(oldRoot);
	}
	
	private Node balance(Node node) {
		Node retNode = null;
		if(node == null) {
			return retNode;
		}
		else if(node.balanceFactor >= 2) {
			if(node.rChild.balanceFactor >= 1) {
				retNode = singleRotateLeft(node);
			}
			else {
				retNode = doubleRotateLeft(node);
			}
			fixHeight(root);
			fixBalanceFactor(root);
		}
		else if(node.balanceFactor <= -2) {
			if(node.lChild.balanceFactor <= -1) {
				retNode = singleRotateRight(node);
			}
			else {
				retNode = doubleRotateRight(node);
			}
			
			fixHeight(root);
			fixBalanceFactor(root);
		}
		return retNode;
	}
	
	private void fixBalanceFactor(Node node) {
		if(node == null) {
			return;
		}
		else {
			fixBalanceFactor(node.lChild);
			fixBalanceFactor(node.rChild);
			if(node.lChild != null && node.rChild != null) {
				node.balanceFactor = ((node.rChild.height + 1) - (node.lChild.height + 1));
			}
			else if(node.lChild == null && node.rChild == null) {
				node.balanceFactor = 0;
			}
			else if(node.lChild == null && node.rChild != null) {
				node.balanceFactor = (node.rChild.height + 1);
			}
			else {
				node.balanceFactor = - (node.lChild.height + 1);
			}
		}
	}
	
	public static void main(String args[]) {
		AVLTreeSet<Integer> testSet = new AVLTreeSet<Integer>( ); 	
		testSet.add( 3);
		testSet.add( 2);
		testSet.add( 1);
		for(Integer i : testSet) {
			System.out.println(i);
		}
	}
}
