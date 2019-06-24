<<<<<<< HEAD
public class SLList {
	private Node head;
	
	public SLList() {
		head = null;
	}	
	class Node{
		public Node next;
		public int data;
		public Node(Node n, int d) {
			next = n;
			data = d;
		}
		public Node(int d) {
			next = null;
			data = d;
		}
	}
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean add(int data) {
		if(isEmpty()) {
			head = new Node(data);
			return true;
		}
		Node node = new Node(data);
		addNext(head, node);
		return true;
	}
	private Node addNext(Node node, Node newNode) {
		while(node.next != null && node.next.data < newNode.data) {
			return addNext(node.next, newNode);
		}
		if(node.next == null) {
			node.next = newNode;
			return newNode;
		}
		else {
			Node moveNode = node.next;
			node.next = newNode;
			newNode.next = moveNode;
			return newNode;
		}
	}
	public int length() {
		return findLength(head);
	}
	private int findLength(Node node) {
		while(node.next != null) {
			return findLength(node.next) + 1;
		}
		return 1;
	}
	public int remove() {
		if(length() == 0) {
			System.err.println("There are no more items to be removed.");;
		}
		Node removalNode = head;
		head = head.next;
		return removalNode.data;
	}
}

=======
public class SLList {
	private Node head;
	
	public SLList() {
		head = null;
	}	
	class Node{
		public Node next;
		public int data;
		public Node(Node n, int d) {
			next = n;
			data = d;
		}
		public Node(int d) {
			next = null;
			data = d;
		}
	}
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean add(int data) {
		if(isEmpty()) {
			head = new Node(data);
			return true;
		}
		Node node = new Node(data);
		addNext(head, node);
		return true;
	}
	private Node addNext(Node node, Node newNode) {
		while(node.next != null && node.next.data < newNode.data) {
			return addNext(node.next, newNode);
		}
		if(node.next == null) {
			node.next = newNode;
			return newNode;
		}
		else {
			Node moveNode = node.next;
			node.next = newNode;
			newNode.next = moveNode;
			return newNode;
		}
	}
	public int length() {
		return findLength(head);
	}
	private int findLength(Node node) {
		while(node.next != null) {
			return findLength(node.next) + 1;
		}
		return 1;
	}
	public int remove() {
		if(length() == 0) {
			System.err.println("There are no more items to be removed.");;
		}
		Node removalNode = head;
		head = head.next;
		return removalNode.data;
	}
}

>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
