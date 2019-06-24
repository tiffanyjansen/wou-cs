package edu.wou.cs260.SpellChecker;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

public class DLList<Object> implements List<Object>, CompareCount, Queue<Object>{

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
		private DLLNode previous;
		private DLLNode next;
		private Object data;
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
		public DLLNode(Object d) {
			this(null, d, null);
		}
		/**
		 * Non-Default Constructor. Sets all the fields in the class.
		 * @param p; the previous Node.
		 * @param d; the object to be passed into the new Node.
		 * @param n; the next Node.
		 */
		public DLLNode(DLLNode p, Object d, DLLNode n) {
			previous = p;
			data = d;
			next = n;
		}
		/**
		 * Get the previous Node.
		 * @return previous; the Node previous to the Node in question.
		 */
		public DLLNode getPrev() {
			return previous;
		}
		/**
		 * Get the next Node.
		 * @return next; the Node next to the Node in question.
		 */
		public DLLNode getNext() {
			return next;
		}
		/**
		 * Get the data stored in the Node.
		 * @return data; the data stored in the given Node.
		 */
		public Object getData() {
			return data;
		}
		/**
		 * Set a new previous Node.
		 * @param newNode; the new Node to be put into the previous field.
		 */
		public void setPrev(DLLNode newNode) {
			previous = newNode;
		}
		/**
		 * Set a new next Node.
		 * @param newNode; the new Node to be put into the next field.
		 */
		public void setNext(DLLNode newNode) {
			next = newNode;
		}
		/**
		 * Set new data into the Node.
		 * @param newData; the new data to be put into the data field.
		 */
		public void setData(Object newData) {
			data = newData;
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
		if(tail == null){
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
	 */
	public boolean contains(Object data) {
		compareCount = 0;
		if(head.getData().equals(data) || tail.getData().equals(data)) {
			compareCount++;
			return true;
		}
		else {
			DLLNode temp = head;
			for(int i = 2; i<= size; i++) {
				temp = temp.getNext();
				compareCount++;
				if(temp.getData().equals(data)) {
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
				node = node.getNext();
				return newNode.getData();
			}
			
		}
		Iterate it = new Iterate();
		return it;
	}

	/**
	 * Turns our list into an array of the data and returns the array.
	 * @return arrayO; array of data type Object created from the list.
	 */
	public Object[] toArray() {
		Object[] arrayO = new Object[size];
		arrayO[0] = head.getData();
		DLLNode temp = head;
		for(int i = 2; i <= size; i++) {
			temp = temp.getNext();
			arrayO[i-1] = temp.getData();
		}
		return arrayO;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds a new node to the end of the list.
	 * @param temp; the new node to be added to the end
	 * @return true;
	 */
	public boolean add(Object data) {
		DLLNode temp = new DLLNode(data);
		if(isEmpty()) {
			head = temp;
			tail = temp;
			size++;
			return true;
		}
		else {
			temp.setPrev(tail);
			tail.setNext(temp);
			tail = temp;
			size++;
			return true;
		}
	}

	/**
	 * Removes the node with the given data in it.
	 * returns true; if node was removed.
	 */
	public boolean remove(Object data) {
		DLLNode temp = null;
		if(head.getData().equals(data)) {
			temp = head;
			head = head.getNext();
			temp = null;
			head.setPrev(null);
			size--;
			return true;
		}
		else if(tail.getData().equals(data)) {
			temp = tail;
			temp = null;
			tail = tail.getPrev();
			tail.setNext(null);
			size--;
			return true;
		}
		else {
			temp = head;
			for(int i = 2; i < size; i++) {
				temp = temp.getNext();
				if(temp.getData().equals(data)) {
					temp.getNext().setPrev(temp.getPrev());
					temp.getPrev().setNext(temp.getNext());
					temp = null;
					size--;
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
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
	 */
	public Object get(int pos) {
		if(pos > size || pos == 0) {
			return null;
		}
		else if(pos == 1) {
			return head.getData();s
		}
		else if(pos == size) {
			return tail.getData();
		}
		else {
			DLLNode temp = head;
			for(int i = 2; i < size; i++) {
				temp = temp.getNext();
				if(i == pos) {
					return temp.getData();
				}
			}
		}
		return null;
	}

	@Override
	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds a new node to the list with the given data in the given position.
	 * @param pos; the position to put the new node.
	 * @param data; the data to be put into the new node.
	 */
	public void add(int pos, Object data) {
		DLLNode temp = new DLLNode(data);
		if(pos == 1) {
			head.setPrev(temp);
			temp.setNext(head);
			head = temp;
			size++;
		}
		else if(pos >= size + 1) {
			tail.setNext(temp);
			temp.setPrev(tail);
			tail = temp;
			size++;
		}
		else {
			DLLNode node = head;
			for(int i = 2; i <= size; i++) {
				node = node.getNext();
				if(i == pos) {
					temp.setPrev(node);
					temp.setNext(node.getNext());
					node.setNext(temp);
					size++;
				}
			}
		}
	}

	/**
	 * Removes the node at the given position. Returns the data in the node.
	 * @param pos; the position in the list to remove the data.
	 * @return data; the data stored in the node to be removed.
	 */
	public Object remove(int pos) {
		if(pos == 1) {
			DLLNode retNode = head;
			if(tail.equals(head)) {
				tail = null;
				head = null;
				size --;
				return retNode.getData();
			}
			head = head.getNext();
			head.setPrev(null);
			size--;
			return retNode.getData();
		}
		else if(pos == size) {
			DLLNode retNode = tail;
			tail = tail.getPrev();
			tail.setNext(null);
			size--;
			return retNode.getData();
		}
		else if(pos > size) {
			return null;			
		}
		else {
			DLLNode temp = head;
			for(int i = 2; i < size; i++) {
				temp = temp.getNext();
				if(i == pos) {
					temp.getNext().setPrev(temp.getPrev());
					temp.getPrev().setNext(temp.getNext());
					return temp.getData();
				}
			}
		}
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

	/**
	 * Returns the number of comparisons made in the last run of
	 * the contains method.
	 * @return compareCount; the number of comparisons in the contains method.
	 */
	public int getLastCompareCount() {
		return compareCount;
	}

	/**
	 * Uses the add method from above to add the object to the queue.
	 * @param dat; the data to be added to the queue.
	 * @return true;
	 */
	public boolean add(Object dat) {
		return add(dat);
	}

	/**
	 * Uses the add method from above to add the object to the queue.
	 * @param data; the data to be added to the queue.
	 * @return true;
	 */
	public boolean offer(Object data) {
		return add(data);
	}

	/**
	 * Removes the first object in the list.
	 * @return the first object that was in the list.
	 */
	public Object remove() {
		return remove(1);
	}

	/**
	 * Removes and returns the head of the list.
	 * @return data; the data stored at the front of the queue.
	 */
	public Object poll() {
		if(isEmpty()) {
			return null;
		}
		else {
			DLLNode temp = head;
			remove(1);
			return temp.getData();
		}
	}

	/**
	 * Returns the front of the queue, but does not remove it.
	 * @returns data; data stored in the front of the queue.
	 */
	public Object element() {
		return head.getData();
	}

	/**
	 * Returns the data stored at the front of the queue, unless
	 * the queue is empty, then it returns null
	 * @returns data; data stored in the front of the queue.
	 */
	public Object peek() {
		if(isEmpty()) {
			return null;
		}
		else {
			return head.getData();
		}
	}

	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		Queue<String> queue = new DLList<String>();
		queue.add("Abby");
		queue.add("Steven");
		queue.add("Gregg");
		queue.remove();
		queue.remove();
		queue.remove();
		queue.add("Tiffany");
		queue.add("Nadine");
		queue.add("Emily");
		queue.add("Sonja");
		queue.add("Josh");
		queue.remove();
		queue.peek();
		for(String name : queue) {
			System.out.println(name);
		}
	}
}
