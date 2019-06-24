<<<<<<< HEAD

public class Matrix {
	//fields for the matrix operations.
	private int[][] matrix;  
	private int[][] parenthesis;
	private int[][] matrixTwo;
	private int[][] parenthesisTwo;
	
	/**
	 * Constructor for the matrix stuff.
	 * Automatically calls the DP version of the MCM.
	 * Automatically calls the M version of the MCM.
	 * @param array; the p values for the matrices.
	 */
	public Matrix(int[] array) {
		matrix = new int[array.length + 1][array.length+ 1]; //matrix for DP MCM
		matrixTwo = new int[array.length+ 1][array.length+ 1]; //matrix for Memoization MCM
		parenthesis = new int[array.length+ 1][array.length+ 1]; //parenthesis for DP MCM
		parenthesisTwo = new int[array.length+ 1][array.length+ 1]; //parenthesis for Memoization MCM
		matrixChainMult(array); //Find the MCM using DP
		matrixChainMultTwo(array); //Find the MCM using Memoization
	}
	/**
	 * The Dynamic Programming version of the MCM.
	 * @param array; the p value for the matrices.
	 */
	public void matrixChainMult(int[] array) {
		int length = array.length; //a variable used so we don't have to type so much later.
		for(int i = 0; i <= length; i++) {
			matrix[i][i] = 0; //set diagonals to zero.
		}
		for(int l = 2; l <= length; l++) { //the distance between i and j.
			for(int i = 1; i <= length - l; i++) { //the "starting" point.
				int j = i + l - 1; //the "ending" point.
				matrix[i][j] = Integer.MAX_VALUE; //MAX_VALUE for infinity.
				for(int k = i; k < j; k++) { //the possible k-values.
					//find the possible values for the [i][j]th spot.
					int q = (matrix[i][k]) + (matrix[k+1][j]) + (array[i-1]*array[k]*array[j]);
					if(q < matrix[i][j]) { 
						matrix[i][j] = q; //puts the smallest possible value into our matrix.
						parenthesis[i][j] = k; 
						//puts the k-value in a matrix to know where to put our parenthesis.
					}
				}
			}
		}
	}
	/**
	 * The Memoization version of the MCM. 
	 * @param array; the p value for the matrices.
	 */
	public void matrixChainMultTwo(int[] array) {
		int length = array.length - 1; //hold the value of the length of the array - 1
		for(int i = 1; i <= length; i++) {
			for(int j = i; j <= length; j++) {
				matrixTwo[i][j] = -1; //put -1 everywhere in the matrix.
			}
		}
		matrixTwo[1][length] = lookUpChain(matrixTwo, array, 1, length); //find the [1][length] spot.
	}
	/**
	 * The helper method for the Memoization version of the MCM.
	 * @param mat; The matrix used to store the data.
	 * @param array; the p values for the matrices.
	 * @param i; the starting index we are looking for.
	 * @param j; the ending index we are looking for.
	 */
	private int lookUpChain(int[][] mat, int[] array, int i, int j){
		if(mat[i][j] != -1) {
			return mat[i][j];//if we already know the answer, return it.
		}
		if(i == j) {
			mat[i][j] = 0; //the diagonal of the matrix is 0. (when i == j the min is 0.)
		}
		else {
			for(int k = i; k <= (j - 1); k++) { //the possible k-values.
				//find the possible values of [i][j] using recurssion.
				int q = lookUpChain(mat, array, i, k) + lookUpChain(mat, array, k+1, j) + (array[i-1] * array[k] * array[j]);
				if(mat[i][j] == -1 || q < mat[i][j]) { //check if the spot has been initialized and if q is the smallest value.
					mat[i][j] = q; //set the [i][j]th spot to q
					parenthesisTwo[i][j] = k; //keep track of the k-value for the parenthesis.
				}
			}
		}
		return mat[i][j]; //return answer.
	}
	/**
	 * The main method to run everything.
	 * @param args; the required thing.
	 */
	public static void main(String[] args) {
		int[] array = {30, 4, 8, 5, 10, 25, 15};
		Matrix m = new Matrix(array);
		System.out.println("The DP Version of the Matrix Chain Multiplication gives us:");
		System.out.println("The minnimum number of operations is:" + m.matrix[1][6]);
		System.out.println("The first parenthesis go after the " + m.parenthesis[1][6] + "st matrix");
		System.out.println("The second parenthesis go after the " + m.parenthesis[2][6] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesis[2][5] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesis[2][4] + "rd matrix");
		System.out.println("The parenthesis would look like:");
		System.out.println("         A((((BC)D)E)F)");
		System.out.println(" ");
		System.out.println("The Memoization Version of the Matrix Chain Multiplication gives us:");
		System.out.println("The minnimum number of operations is:" + m.matrixTwo[1][6]);
		System.out.println("The first parenthesis go after the " + m.parenthesisTwo[1][6] + "st matrix");
		System.out.println("The second parenthesis go after the " + m.parenthesisTwo[2][6] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesisTwo[2][5] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesisTwo[2][4] + "rd matrix");
		System.out.println("The parenthesis would look like:");
		System.out.println("         A((((BC)D)E)F)");
	}
}
=======

public class Matrix {
	//fields for the matrix operations.
	private int[][] matrix;  
	private int[][] parenthesis;
	private int[][] matrixTwo;
	private int[][] parenthesisTwo;
	
	/**
	 * Constructor for the matrix stuff.
	 * Automatically calls the DP version of the MCM.
	 * Automatically calls the M version of the MCM.
	 * @param array; the p values for the matrices.
	 */
	public Matrix(int[] array) {
		matrix = new int[array.length + 1][array.length+ 1]; //matrix for DP MCM
		matrixTwo = new int[array.length+ 1][array.length+ 1]; //matrix for Memoization MCM
		parenthesis = new int[array.length+ 1][array.length+ 1]; //parenthesis for DP MCM
		parenthesisTwo = new int[array.length+ 1][array.length+ 1]; //parenthesis for Memoization MCM
		matrixChainMult(array); //Find the MCM using DP
		matrixChainMultTwo(array); //Find the MCM using Memoization
	}
	/**
	 * The Dynamic Programming version of the MCM.
	 * @param array; the p value for the matrices.
	 */
	public void matrixChainMult(int[] array) {
		int length = array.length; //a variable used so we don't have to type so much later.
		for(int i = 0; i <= length; i++) {
			matrix[i][i] = 0; //set diagonals to zero.
		}
		for(int l = 2; l <= length; l++) { //the distance between i and j.
			for(int i = 1; i <= length - l; i++) { //the "starting" point.
				int j = i + l - 1; //the "ending" point.
				matrix[i][j] = Integer.MAX_VALUE; //MAX_VALUE for infinity.
				for(int k = i; k < j; k++) { //the possible k-values.
					//find the possible values for the [i][j]th spot.
					int q = (matrix[i][k]) + (matrix[k+1][j]) + (array[i-1]*array[k]*array[j]);
					if(q < matrix[i][j]) { 
						matrix[i][j] = q; //puts the smallest possible value into our matrix.
						parenthesis[i][j] = k; 
						//puts the k-value in a matrix to know where to put our parenthesis.
					}
				}
			}
		}
	}
	/**
	 * The Memoization version of the MCM. 
	 * @param array; the p value for the matrices.
	 */
	public void matrixChainMultTwo(int[] array) {
		int length = array.length - 1; //hold the value of the length of the array - 1
		for(int i = 1; i <= length; i++) {
			for(int j = i; j <= length; j++) {
				matrixTwo[i][j] = -1; //put -1 everywhere in the matrix.
			}
		}
		matrixTwo[1][length] = lookUpChain(matrixTwo, array, 1, length); //find the [1][length] spot.
	}
	/**
	 * The helper method for the Memoization version of the MCM.
	 * @param mat; The matrix used to store the data.
	 * @param array; the p values for the matrices.
	 * @param i; the starting index we are looking for.
	 * @param j; the ending index we are looking for.
	 */
	private int lookUpChain(int[][] mat, int[] array, int i, int j){
		if(mat[i][j] != -1) {
			return mat[i][j];//if we already know the answer, return it.
		}
		if(i == j) {
			mat[i][j] = 0; //the diagonal of the matrix is 0. (when i == j the min is 0.)
		}
		else {
			for(int k = i; k <= (j - 1); k++) { //the possible k-values.
				//find the possible values of [i][j] using recurssion.
				int q = lookUpChain(mat, array, i, k) + lookUpChain(mat, array, k+1, j) + (array[i-1] * array[k] * array[j]);
				if(mat[i][j] == -1 || q < mat[i][j]) { //check if the spot has been initialized and if q is the smallest value.
					mat[i][j] = q; //set the [i][j]th spot to q
					parenthesisTwo[i][j] = k; //keep track of the k-value for the parenthesis.
				}
			}
		}
		return mat[i][j]; //return answer.
	}
	/**
	 * The main method to run everything.
	 * @param args; the required thing.
	 */
	public static void main(String[] args) {
		int[] array = {30, 4, 8, 5, 10, 25, 15};
		Matrix m = new Matrix(array);
		System.out.println("The DP Version of the Matrix Chain Multiplication gives us:");
		System.out.println("The minnimum number of operations is:" + m.matrix[1][6]);
		System.out.println("The first parenthesis go after the " + m.parenthesis[1][6] + "st matrix");
		System.out.println("The second parenthesis go after the " + m.parenthesis[2][6] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesis[2][5] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesis[2][4] + "rd matrix");
		System.out.println("The parenthesis would look like:");
		System.out.println("         A((((BC)D)E)F)");
		System.out.println(" ");
		System.out.println("The Memoization Version of the Matrix Chain Multiplication gives us:");
		System.out.println("The minnimum number of operations is:" + m.matrixTwo[1][6]);
		System.out.println("The first parenthesis go after the " + m.parenthesisTwo[1][6] + "st matrix");
		System.out.println("The second parenthesis go after the " + m.parenthesisTwo[2][6] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesisTwo[2][5] + "th matrix");
		System.out.println("The third parenthesis go after the " + m.parenthesisTwo[2][4] + "rd matrix");
		System.out.println("The parenthesis would look like:");
		System.out.println("         A((((BC)D)E)F)");
	}
}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
