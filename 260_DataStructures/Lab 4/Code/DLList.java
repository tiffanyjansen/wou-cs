package edu.wou.cs260.SpellChecker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Doubly Linked List
 * @author Tiffany Jansen
 *
 * @param <T>; I don't even know...
 */
public class DLList<T> implements List<T>, CompareCount, Queue<T> {

	private DLLNode head;
	private DLLNode tail;
	private int size;
	private int compareCount;

	/**
	 * Constructor for the list. Sets the first and last Nodes to null, and the size to 0.
	 */
	public DLList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Nested Class for the Nodes
	 */
	class DLLNode{
		public DLLNode prev;
		public DLLNode next;
		public T data;
		
		/**
		 * Default Constructor. Fills the node with null values.
		 */
		public DLLNode() {
			this(null, null, null);
		}
		/**
		 * Non-default Constructor. Sets only the data field.
		 * @param d; the object to be passed into the new Node.
		 */
		public DLLNode(T d) {
			this(null, d, null);
		}
		/**
		 * Non-Default Constructor. Sets all the fields in the class.
		 * @param p; the prev Node.
		 * @param d; the object to be passed into the new Node.
		 * @param n; the next Node.
		 */
		public DLLNode(DLLNode p, T d, DLLNode n) {
			prev = p;
			data = d;
			next = n;
		}
	}
	
	/**
	 * Returns the current size of the List.
	 * @return size; the length of the list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks to see if list is empty.
	 * @return true; if list is empty
	 */
	public boolean isEmpty() {
		if(head == null){
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Checks the list to see if the given data is found in any of the 
	 * nodes in the list.
	 * @param data; the data to be checked.
	 * @return true; if it contains the data.
	 * @throws NullPointerException; if data is null.
	 */
	@SuppressWarnings("unchecked")
	public boolean contains(Object data) {
		if(data == null) {
			throw new NullPointerException("The list will not contain a null value.");
		}
		compareCount = 0;
		T info = (T) data;
		if(isEmpty()) {
			return false;
		}
		else if(head.data.equals(info)) {
			compareCount++;
			return true;
		}
		else {
			DLLNode node = head;
			for(int i = 1; i < size; i++) {
				node = node.next;
				compareCount++;
				if(info.equals(node.data)) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Creates and returns an iterator for the list.
	 * @return it; the iterator.
	 */
	@SuppressWarnings("unchecked")
	public Iterator iterator() {
		class Iterate implements Iterator{
			private DLLNode node;
			
			public Iterate() {
				node = head;
			}

			/**
			 * Decides if the list has another variable in the list.
			 * @return true; if there is another variable.
			 */
			public boolean hasNext() {
				if(node != null) {
					return true;
				}
				else{
					return false;
				}
			}

			/**
			 * Returns the next object in the list.
			 * @return next; the next object in the list.
			 */
			public Object next() {
				DLLNode newNode = node;
				node = node.next;
				return newNode.data;
			}
			
		}
		Iterate it = new Iterate();
		return it;
	}
	/**
	 * Adds a new node to the end of the list.
	 * @param temp; the new node to be added to the end
	 * @return true;
	 */
	public boolean add(T data) {
		if(data == null) {
			throw new NullPointerException("You cannot add null to the list.");
		}
		DLLNode temp = new DLLNode(data);
		if(isEmpty()) {
			head = temp;
			tail = temp;
			size++;
			return true;
		}
		else {
			temp.prev = tail;
			tail.next = temp;
			tail = temp;
			size++;
			return true;
		}
	}

	/**
	 * Removes the node with the given data in it.
	 * @param data; the data to be removed
	 * @returns true; if node was removed.
	 * @throws NullPointerException; if the parameter is null.
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(Object data) {
		if(data == null) {
			throw new NullPointerException("There are no null values in the list.");
		}
		else if(isEmpty()) {
			return false;
		}
		else {
			DLLNode node = findNode((T) data);
			if(node != null) {
				removeNode(node);
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Removes everything from the list. Returns the list into the original list 
	 * with the head and tail set as null and the size set to 0.
	 */
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Finds the node in the given position (starting at 1, not 0) and returns the data in 
	 * said node to the user.
	 * @return data; the data stored in the node at the position.
	 * @throws IndexOutOfBoundsException; if pos is out of range (pos 1 < || pos > size).
	 */
	public T get(int pos) {
		if(pos < 0 || pos >= size) {
			throw new IndexOutOfBoundsException();
		}
		else{
			return findNode(pos).data;
		}
	}
	
	private DLLNode findNode(T data) {
		DLLNode node = head;
		while(node != null) {
			if(node.data.equals(data)) {
				return node;
			}
			node = node.next;
		}
		return null;
	}
	
	private DLLNode findNode(int pos) {
		if(pos == 0) {
			return head;
		}
		DLLNode temp = head;
		int i = 0;
		while(i < pos && temp != null) {
			temp = temp.next;
			i++;
		}
		return temp;
	}
	
	private void addBefore(DLLNode node, T data) {
		DLLNode newNode = new DLLNode(data);
		if(node == head) {
			head.prev = newNode;
			newNode.next = head;
			head = newNode;
			size++;
		}
		else {
			newNode.next = node;
			newNode.prev = node.prev;
			node.prev = newNode;
			newNode.prev.next = newNode;
			size++;
		}
	}
	
	private T removeNode(DLLNode node) {
		if(size == 1 && head.equals(node)) {
			head = null;
			tail = null;
			size = 0;
			return node.data;
		}
		else if(head.equals(node)) {
			head.next.prev = null;
			head = head.next;
			size--;
			return node.data;
		}
		else if(tail.equals(node)) {
			tail.prev.next = null;
			tail = tail.prev;
			size--;
			return node.data;
		}
		else {
			node.next.prev = node.prev;
			node.prev.next = node.next;
			size--;
			return node.data;
		}
	}
	

	/**
	 * Adds a new node to the list with the given data in the given position.
	 * @param pos; the position to put the new node.
	 * @param data; the data to be put into the new node.
	 * @throws IndexOutOfBoundsException; if pos is out of range (pos < 0 || pos > size).
	 * @throws NullPointerException; if user tries to add a null value to the list.
	 */
	public void add(int pos, T data) {
		if(data == null) {
			throw new NullPointerException();
		}
		else if(pos > size || pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		else if(isEmpty()) {
			add(data);
		}
		else if(pos == 0) {
			addBefore(head, data);
		}
		else if(pos == size) {
			add(data);
		}
		else {
			DLLNode current = findNode(pos);
			addBefore(current, data);
		}
	}
	
	

	/**
	 * Removes the node at the given position. Returns the data in the node.
	 * @param pos; the position in the list to remove the data.
	 * @return data; the data stored in the node to be removed.
	 *  @throws IndexOutOfBoundsException; if pos is out of range (pos < 0 || pos >= size).
	 */
	public T remove(int pos) {
		if(pos < 0 || pos >= size) {
			throw new IndexOutOfBoundsException("The index entered is not in the list.");
		}
		else if(isEmpty()) {
			return null;
		}
		else {
			DLLNode node = findNode(pos);
			return removeNode(node);
		}
	}

	/**
	 * Returns the number of comparisons made in the last run of
	 * the contains method.
	 * @return compareCount; the number of comparisons in the contains method.
	 */
	public int getLastCompareCount() {
		return compareCount;
	}

//	/**
//	 * Uses the add method from above to add the object to the queue.
//	 * @param dat; the data to be added to the queue.
//	 * @return true;
//	 */
//	public boolean add(Object dat) {
//		return add(dat);
//	}

	/**
	 * Uses the add method from above to add the object to the queue.
	 * @param data; the data to be added to the queue.
	 * @return true;
	 * @throws NullPointerException; if the data entered is null.
	 */
	public boolean offer(T data) {
		if(data != null) {
			return add(data);
		}
		else {
			throw new NullPointerException("You cannot add a null value to the list.");
		}
	}

	/**
	 * Removes the first object in the list.
	 * @return the first object that was in the list.
	 * @throws NoSuchElementException; if the lsit is empty.
	 */
	public T remove() {
		if(!isEmpty()) {
			return remove(0);
		}
		else {
			throw new NoSuchElementException("There is nothing in the list to remove.");
		}
	}

	/**
	 * Removes and returns the head of the list.
	 * @return data; the data stored at the front of the queue.
	 */
	public T poll() {
		if(isEmpty()) {
			return null;
		}
		else {
			DLLNode temp = head;
			remove(0);
			return temp.data;
		}
	}

	/**
	 * Returns the front of the queue, but does not remove it.
	 * @returns data; data stored in the front of the queue.
	 * @throws NoSuchElementException; if the list is empty.
	 */
	public T element() {
		if(!isEmpty()) {
			return head.data;
		}
		else {
			throw new NoSuchElementException("There is nothing in the list.");
		}
	}	

	/**
	 * Returns the data stored at the front of the queue, unless
	 * the queue is empty, then it returns null
	 * @returns data; data stored in the front of the queue.
	 */
	public T peek() {
		if(isEmpty()) {
			return null;
		}
		else {
			return head.data;
		}
	}
	
	public int intValue()
	{
		return 0;
	}

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new DLList<String>();
		list.add("Emily");
		list.add("John");
		list.add("Nadine");
		list.add(1, "Tiffany");
		list.add(2, "Jake");
		list.add(3, "Bob");
		for(String name : list) {
			System.out.println(name);
		}
	}
	
	//Methods not used/written:
	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Didn't want to write this method, but it's supposed to add everything from 
	 * another collection to the list, but I don't want to do that and it's not
	 * required so I'm not going to.
	 * @throws UnsupportedOperationException;
	 */
	public boolean addAll(Collection c) {
		String s = "This list does not allow you to add everything from a Collection, Sorry.";
		throw new UnsupportedOperationException(s);
	}

	@Override
	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

}
