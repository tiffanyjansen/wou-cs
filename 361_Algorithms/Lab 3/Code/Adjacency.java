<<<<<<< HEAD
import java.util.*;

public class Adjacency {
	
	private LinkedList<Node>[] list; //The adjacency list
	private int[][] matrix; //The adjacency matrix
		
	@SuppressWarnings("unchecked")
	public Adjacency() {
		matrix = new int[14][14];
		list = new LinkedList[14];
		initialize();
	}
	public class Node{
		private int visit;
		private int distance;
		private char vertex;
		private Node prev;
		public Node(int d, Node p, char v) {
			visit = -1;
			distance = d;
			prev = p;
			vertex = v;
		}
		public Node(int d, char v) {
			this(d, null, v);
		}
		public Node(char v) {
			this(-1, null, v);
		}
		public int getIndex(char c) {
			switch(c) {
			case 'A': return 0;
			case 'B': return 1;
			case 'C': return 2;
			case 'D': return 3;
			case 'E': return 4;
			case 'F': return 5;
			case 'G': return 6;
			case 'H': return 7;
			case 'I': return 8;
			case 'J': return 9;
			case 'K': return 10;
			case 'L': return 11;
			case 'M': return 12;
			case 'N': return 13;
			default: return -1;
			}
		}
	}
	/**
	 * Sets everything in the matrix to 0, so there isn't any
	 * data in it before we start.
	 * @param m; the matrix we wish to fill with 0s.
	 * @return m; the matrix filled with 0s.
	 */
	private int[][] init(int[][] m) {
		for(int i = 0; i < m.length; i++) {
			for(int j = 0; j < m.length; j++) {
				m[i][j] = 0;
			}
		}
		return m;
	}
	/**
	 * Initialize the matrix so it has all of the edges in it.
	 * I used the graph and converted all of the letters to numbers
	 * (A=0, B=1, C=2, ..., M=13) This way I could use the indexes
	 * to more easily do the search.
	 */
	private void initialize() {
		//Initialize list
		for(int i =  0; i<matrix.length; i++) {
			list[i] = new LinkedList<Node>();
		}
		//Creates the nodes for each letter so we can mark
		//Them when we have visited them. 
		Node[] vertices = new Node[14];

		Node A = new Node('A');
		Node B = new Node('B');
		Node C = new Node('C');
		Node D = new Node('D');
		Node E = new Node('E');
		Node F = new Node('F');
		Node G = new Node('G');
		Node H = new Node('H');
		Node I = new Node('I');
		Node J = new Node('J');
		Node K = new Node('K');
		Node L = new Node('L');
		Node M = new Node('M');
		Node N = new Node('N');
		
		vertices[0] = A;
		vertices[1] = B;
		vertices[2] = C;
		vertices[3] = D;
		vertices[4] = E;
		vertices[5] = F;
		vertices[6] = G;
		vertices[7] = H;
		vertices[8] = I;
		vertices[9] = J;
		vertices[10] = K;
		vertices[11] = L;
		vertices[12] = M;
		vertices[13] = N;

		//Edges for A
		list[0].add(B);
		list[0].add(D);
				
		//Edges for B
		list[1].add(A);
		list[1].add(C);
				
		//Edges for C
		list[2].add(B);
		list[2].add(M);
		list[2].add(N);
		
		//Edges for D
		list[3].add(A);
		list[3].add(E);
		list[3].add(F);
		list[3].add(G);
		list[3].add(N);

		//Edges for E
		list[4].add(D);	
		list[4].add(F);
		
		//Edges for F
		list[5].add(D);
		list[5].add(E);
		list[5].add(H);
		
		//Edges for G
		list[6].add(D);
		list[6].add(H);
		list[6].add(J);
		
		//Edges for H
		list[7].add(F);
		list[7].add(G);
		list[7].add(I);
		list[7].add(K);
		
		//Edges for I
		list[8].add(H);
		list[8].add(J);
			
		//Edges for J
		list[9].add(G);
		list[9].add(I);
		list[9].add(K);
		list[9].add(N);
			
		//Edges for K
		list[10].add(H);
		list[10].add(J);
		list[10].add(L);
		
		//Edges for L
		list[11].add(K);	
		list[11].add(M);
				
		//Edges for M
		list[12].add(C);
		list[12].add(L);
		list[12].add(N);
				
		//Edges for N
		list[13].add(C);
		list[13].add(D);
		list[13].add(J);
		list[13].add(M);
			
		//call the breadth first search for the list
		bFSL(vertices, A);
		
		matrix = init(matrix);
		
		//Edges for A
		matrix[0][1] = 1;
		matrix[0][3] = 1;
		
		//Edges for B
		matrix[1][0] = 1;
		matrix[1][2] = 1;
		
		//Edges for C
		matrix[2][1] = 1;
		matrix[2][12] = 1;
		matrix[2][13] = 1;
		
		//Edges for D
		matrix[3][0] = 1;
		matrix[3][4] = 1;
		matrix[3][5] = 1;
		matrix[3][6] = 1;
		matrix[3][13] = 1;
		
		//Edges for E
		matrix[4][3] = 1;	
		matrix[4][5] = 1;
		
		//Edges for F
		matrix[5][3] = 1;
		matrix[5][4] = 1;
		matrix[5][7] = 1;
		
		//Edges for G
		matrix[6][3] = 1;
		matrix[6][7] = 1;
		matrix[6][9] = 1;
		
		//Edges for H
		matrix[7][5] = 1;
		matrix[7][6] = 1;
		matrix[7][8] = 1;
		matrix[7][10] = 1;
		
		//Edges for I
		matrix[8][7] = 1;	
		matrix[8][9] = 1;
		
		//Edges for J
		matrix[9][6] = 1;
		matrix[9][8] = 1;
		matrix[9][10] = 1;
		matrix[9][13] = 1;
		
		//Edges for K
		matrix[10][7] = 1;
		matrix[10][9] = 1;
		matrix[10][11] = 1;
		
		//Edges for L
		matrix[11][10] = 1;	
		matrix[11][12] = 1;
		
		//Edges for M
		matrix[12][2] = 1;
		matrix[12][11] = 1;
		matrix[12][13] = 1;
		
		//Edges for N
		matrix[13][2] = 1;
		matrix[13][3] = 1;
		matrix[13][9] = 1;
		matrix[13][12] = 1;
	}
	/**
	 * Breadth-First Search for the Adjacency List.
	 * This will print out all of the necessary data and stuff.
	 * I used the pseudocode from the book to code this.
	 * @param vertices; The vertices in our graph.
	 * @param start; The starting vertex.
	 */
	public void bFSL(Node[] vertices, Node start) {
		start.visit = 0; //set visit for the start vertex as an intermediary integer.
		start.distance = 0; //set distance for the start vertex as 0.
		start.prev = null; //set previous for the start vertex as null.
		System.out.println("We started at vertex " + start.vertex + ".");
		System.out.println("It's distance from the start is 0.");
		System.out.println(" "); //All of the info for the start vertex printed.
		LinkedList<Node> queue = new LinkedList<Node>(); //create a queue. 
		//Since we don't want a priority queue, we are using a linked list which
		//includes all of the queue methods.
		queue.add(start); //add the start vertex to our queue.
		while(queue.peek() != null) { //while the queue is not empty...
			Node node = queue.poll(); //remove the head of the queue.
			for(Node vertex : list[node.getIndex(node.vertex)]) { //for every vertex
				//in the list that is in the sport of the vertex.
				if(vertex.visit == -1) { //check to see if the vertex has been visited.
					vertex.visit = 0; //set visit for the vertex as an intermediary integer.
					vertex.distance = node.distance + 1; //set the distance of the vertex to 
					//the distance of the previous node plus one.
					vertex.prev = node; //set the previous node as the node we removed from the queue.
					System.out.println("The next vertex we visited is " + vertex.vertex + ".");
					System.out.println("It's distance from the start is " + vertex.distance + ".");
					System.out.println("It's predecessor is " + vertex.prev.vertex + ".");
					//print all of the info for the vertex we just visited.
					queue.add(vertex); //add the vertex we have visited to our queue.
					System.out.println(" ");
				}
			}
			node.visit = 1; //set the visit to the final visit to show we have looked
			//at all of the vertices that are adjacent to it. 
		}
	}
	/**
	 * The Breadth-First Search for the adjacency matrix.
	 * @return retMatrix; The matrix with the adjacencies for the BFS.
	 */
	public int[][] bFSM() {
		int[][] retMatrix = new int[14][14];
		retMatrix = init(retMatrix); //initialize our return matrix as 0.
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		//The queue with the vertices that have been added.
		PriorityQueue<Integer> vertices = new PriorityQueue<Integer>();
		//The queue with the vertices we have visited.
		int i = 0; //set the start matrix as 0.
		queue.add(i); //add i to our queue. 
		for(int j = 1; j < matrix.length; j++) {
			if(matrix[i][j] == 1) { 
				retMatrix[i][j] = 1; //add the adjacencies from the start
				//matrix.
				queue.add(j);//add the vertices we have added to our BFS.
				vertices.add(j); //add it to the visited queue.
			}
			vertices.add(i);//add the one we just checked to the vertices queue.
		}
		while(queue.peek() != null) { //check to see if queue is empty.
			i = queue.poll(); //remove the first thing from the queue.
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[i][j] == 1 && !vertices.contains(j)) {
					//check to see if there is an edge there. Check to see if
					//the vertex is in our vertices queue or not.
					retMatrix[i][j] = 1; //add it to our return matrix.
					queue.add(j); //add it to the queue.
					vertices.add(j); //add it to the visited queue.
				}
			}
		}
		return retMatrix; //return the return matrix.
	}
	/**
	 * The main method used to run the program.
	 * @param args;
	 */
	public static void main(String args[]) {
		Adjacency a = new Adjacency();
		int[][] ret = a.bFSM();
		//Print out the edges in the matrix.
		System.out.println("The edges for the adjacency matrix BFS");
		for(int i = 0; i < ret.length; i++) {
			for(int j = 0; j < ret.length; j++) {
				if(ret[i][j] == 1) {
					System.out.println("One of the edges is [" + i + "][" + j + "]" );
				}
			}
		}
	}
=======
import java.util.*;

public class Adjacency {
	
	private LinkedList<Node>[] list; //The adjacency list
	private int[][] matrix; //The adjacency matrix
		
	@SuppressWarnings("unchecked")
	public Adjacency() {
		matrix = new int[14][14];
		list = new LinkedList[14];
		initialize();
	}
	public class Node{
		private int visit;
		private int distance;
		private char vertex;
		private Node prev;
		public Node(int d, Node p, char v) {
			visit = -1;
			distance = d;
			prev = p;
			vertex = v;
		}
		public Node(int d, char v) {
			this(d, null, v);
		}
		public Node(char v) {
			this(-1, null, v);
		}
		public int getIndex(char c) {
			switch(c) {
			case 'A': return 0;
			case 'B': return 1;
			case 'C': return 2;
			case 'D': return 3;
			case 'E': return 4;
			case 'F': return 5;
			case 'G': return 6;
			case 'H': return 7;
			case 'I': return 8;
			case 'J': return 9;
			case 'K': return 10;
			case 'L': return 11;
			case 'M': return 12;
			case 'N': return 13;
			default: return -1;
			}
		}
	}
	/**
	 * Sets everything in the matrix to 0, so there isn't any
	 * data in it before we start.
	 * @param m; the matrix we wish to fill with 0s.
	 * @return m; the matrix filled with 0s.
	 */
	private int[][] init(int[][] m) {
		for(int i = 0; i < m.length; i++) {
			for(int j = 0; j < m.length; j++) {
				m[i][j] = 0;
			}
		}
		return m;
	}
	/**
	 * Initialize the matrix so it has all of the edges in it.
	 * I used the graph and converted all of the letters to numbers
	 * (A=0, B=1, C=2, ..., M=13) This way I could use the indexes
	 * to more easily do the search.
	 */
	private void initialize() {
		//Initialize list
		for(int i =  0; i<matrix.length; i++) {
			list[i] = new LinkedList<Node>();
		}
		//Creates the nodes for each letter so we can mark
		//Them when we have visited them. 
		Node[] vertices = new Node[14];

		Node A = new Node('A');
		Node B = new Node('B');
		Node C = new Node('C');
		Node D = new Node('D');
		Node E = new Node('E');
		Node F = new Node('F');
		Node G = new Node('G');
		Node H = new Node('H');
		Node I = new Node('I');
		Node J = new Node('J');
		Node K = new Node('K');
		Node L = new Node('L');
		Node M = new Node('M');
		Node N = new Node('N');
		
		vertices[0] = A;
		vertices[1] = B;
		vertices[2] = C;
		vertices[3] = D;
		vertices[4] = E;
		vertices[5] = F;
		vertices[6] = G;
		vertices[7] = H;
		vertices[8] = I;
		vertices[9] = J;
		vertices[10] = K;
		vertices[11] = L;
		vertices[12] = M;
		vertices[13] = N;

		//Edges for A
		list[0].add(B);
		list[0].add(D);
				
		//Edges for B
		list[1].add(A);
		list[1].add(C);
				
		//Edges for C
		list[2].add(B);
		list[2].add(M);
		list[2].add(N);
		
		//Edges for D
		list[3].add(A);
		list[3].add(E);
		list[3].add(F);
		list[3].add(G);
		list[3].add(N);

		//Edges for E
		list[4].add(D);	
		list[4].add(F);
		
		//Edges for F
		list[5].add(D);
		list[5].add(E);
		list[5].add(H);
		
		//Edges for G
		list[6].add(D);
		list[6].add(H);
		list[6].add(J);
		
		//Edges for H
		list[7].add(F);
		list[7].add(G);
		list[7].add(I);
		list[7].add(K);
		
		//Edges for I
		list[8].add(H);
		list[8].add(J);
			
		//Edges for J
		list[9].add(G);
		list[9].add(I);
		list[9].add(K);
		list[9].add(N);
			
		//Edges for K
		list[10].add(H);
		list[10].add(J);
		list[10].add(L);
		
		//Edges for L
		list[11].add(K);	
		list[11].add(M);
				
		//Edges for M
		list[12].add(C);
		list[12].add(L);
		list[12].add(N);
				
		//Edges for N
		list[13].add(C);
		list[13].add(D);
		list[13].add(J);
		list[13].add(M);
			
		//call the breadth first search for the list
		bFSL(vertices, A);
		
		matrix = init(matrix);
		
		//Edges for A
		matrix[0][1] = 1;
		matrix[0][3] = 1;
		
		//Edges for B
		matrix[1][0] = 1;
		matrix[1][2] = 1;
		
		//Edges for C
		matrix[2][1] = 1;
		matrix[2][12] = 1;
		matrix[2][13] = 1;
		
		//Edges for D
		matrix[3][0] = 1;
		matrix[3][4] = 1;
		matrix[3][5] = 1;
		matrix[3][6] = 1;
		matrix[3][13] = 1;
		
		//Edges for E
		matrix[4][3] = 1;	
		matrix[4][5] = 1;
		
		//Edges for F
		matrix[5][3] = 1;
		matrix[5][4] = 1;
		matrix[5][7] = 1;
		
		//Edges for G
		matrix[6][3] = 1;
		matrix[6][7] = 1;
		matrix[6][9] = 1;
		
		//Edges for H
		matrix[7][5] = 1;
		matrix[7][6] = 1;
		matrix[7][8] = 1;
		matrix[7][10] = 1;
		
		//Edges for I
		matrix[8][7] = 1;	
		matrix[8][9] = 1;
		
		//Edges for J
		matrix[9][6] = 1;
		matrix[9][8] = 1;
		matrix[9][10] = 1;
		matrix[9][13] = 1;
		
		//Edges for K
		matrix[10][7] = 1;
		matrix[10][9] = 1;
		matrix[10][11] = 1;
		
		//Edges for L
		matrix[11][10] = 1;	
		matrix[11][12] = 1;
		
		//Edges for M
		matrix[12][2] = 1;
		matrix[12][11] = 1;
		matrix[12][13] = 1;
		
		//Edges for N
		matrix[13][2] = 1;
		matrix[13][3] = 1;
		matrix[13][9] = 1;
		matrix[13][12] = 1;
	}
	/**
	 * Breadth-First Search for the Adjacency List.
	 * This will print out all of the necessary data and stuff.
	 * I used the pseudocode from the book to code this.
	 * @param vertices; The vertices in our graph.
	 * @param start; The starting vertex.
	 */
	public void bFSL(Node[] vertices, Node start) {
		start.visit = 0; //set visit for the start vertex as an intermediary integer.
		start.distance = 0; //set distance for the start vertex as 0.
		start.prev = null; //set previous for the start vertex as null.
		System.out.println("We started at vertex " + start.vertex + ".");
		System.out.println("It's distance from the start is 0.");
		System.out.println(" "); //All of the info for the start vertex printed.
		LinkedList<Node> queue = new LinkedList<Node>(); //create a queue. 
		//Since we don't want a priority queue, we are using a linked list which
		//includes all of the queue methods.
		queue.add(start); //add the start vertex to our queue.
		while(queue.peek() != null) { //while the queue is not empty...
			Node node = queue.poll(); //remove the head of the queue.
			for(Node vertex : list[node.getIndex(node.vertex)]) { //for every vertex
				//in the list that is in the sport of the vertex.
				if(vertex.visit == -1) { //check to see if the vertex has been visited.
					vertex.visit = 0; //set visit for the vertex as an intermediary integer.
					vertex.distance = node.distance + 1; //set the distance of the vertex to 
					//the distance of the previous node plus one.
					vertex.prev = node; //set the previous node as the node we removed from the queue.
					System.out.println("The next vertex we visited is " + vertex.vertex + ".");
					System.out.println("It's distance from the start is " + vertex.distance + ".");
					System.out.println("It's predecessor is " + vertex.prev.vertex + ".");
					//print all of the info for the vertex we just visited.
					queue.add(vertex); //add the vertex we have visited to our queue.
					System.out.println(" ");
				}
			}
			node.visit = 1; //set the visit to the final visit to show we have looked
			//at all of the vertices that are adjacent to it. 
		}
	}
	/**
	 * The Breadth-First Search for the adjacency matrix.
	 * @return retMatrix; The matrix with the adjacencies for the BFS.
	 */
	public int[][] bFSM() {
		int[][] retMatrix = new int[14][14];
		retMatrix = init(retMatrix); //initialize our return matrix as 0.
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		//The queue with the vertices that have been added.
		PriorityQueue<Integer> vertices = new PriorityQueue<Integer>();
		//The queue with the vertices we have visited.
		int i = 0; //set the start matrix as 0.
		queue.add(i); //add i to our queue. 
		for(int j = 1; j < matrix.length; j++) {
			if(matrix[i][j] == 1) { 
				retMatrix[i][j] = 1; //add the adjacencies from the start
				//matrix.
				queue.add(j);//add the vertices we have added to our BFS.
				vertices.add(j); //add it to the visited queue.
			}
			vertices.add(i);//add the one we just checked to the vertices queue.
		}
		while(queue.peek() != null) { //check to see if queue is empty.
			i = queue.poll(); //remove the first thing from the queue.
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[i][j] == 1 && !vertices.contains(j)) {
					//check to see if there is an edge there. Check to see if
					//the vertex is in our vertices queue or not.
					retMatrix[i][j] = 1; //add it to our return matrix.
					queue.add(j); //add it to the queue.
					vertices.add(j); //add it to the visited queue.
				}
			}
		}
		return retMatrix; //return the return matrix.
	}
	/**
	 * The main method used to run the program.
	 * @param args;
	 */
	public static void main(String args[]) {
		Adjacency a = new Adjacency();
		int[][] ret = a.bFSM();
		//Print out the edges in the matrix.
		System.out.println("The edges for the adjacency matrix BFS");
		for(int i = 0; i < ret.length; i++) {
			for(int j = 0; j < ret.length; j++) {
				if(ret[i][j] == 1) {
					System.out.println("One of the edges is [" + i + "][" + j + "]" );
				}
			}
		}
	}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
}