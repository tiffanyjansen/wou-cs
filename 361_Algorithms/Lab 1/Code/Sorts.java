<<<<<<< HEAD
/**
 * CS 361: Lab 1
 * Sorts: A collection of all the sorts we will be doing.
 * So far it has Merge Sort.
 * @author Tiffany Jansen
 * @version 1.0
 */
public class Sorts {
	//The fields used in the class.
	private Reader reader;
	private int[] numbers;
	private int[] sortedMerge;
	private int[] sortedQuick;
	private Testing test;
	/**
	 * The Constructor for Sorts that sets up the lists.
	 */
	public Sorts(){
		reader = new Reader("lab1_data.txt");
		numbers = reader.getList();
		sortedMerge = new int[numbers.length];
		sortedQuick = numbers;
		test = new Testing();
	}
	/**
	 * The merge sort method that sorts the list using merge sort.
	 * Used the pseudocode from the book, just had to switch things because
	 * of the weird "arrays start at 1" thing.
	 * @param list; The list we are trying to sort.
	 * @param start; The starting index.
	 * @param end; The ending index.
	 */
	public void auxMergeSort(int[] list, int start, int end) {
		if(start < end) {
			int mid = (start + end)/2;
			auxMergeSort(list, start, mid); //Calls mergeSort on 1st half of list.
			auxMergeSort(list, mid + 1, end);//Calls mergeSort on 2nd half of list.
			sortedMerge = auxMerge(list, start, mid, end);//Does the actual "sorting" part.
		}
	}
	/**
	 * Merges the left and right side together to form the sorted list.
	 * Used mostly the pseudocode from the book, with edits for the indeces.
	 * I also used Geeks for Geeks for some of it because some of the pseudocode
	 * wasn't working correctly. *Explained more below*
	 * @param list; The list we are sorting.
	 * @param start; The starting index.
	 * @param mid; The mid of the two indexes.
	 * @param end; The ending index.
	 * @return list; The sorted list.
	 */
	private int[] auxMerge(int[] list, int start, int mid, int end) {
		int lenLeft = mid - start + 1;
		int lenRight = end - mid;
		int[] left = new int[lenLeft]; //Creates left Array
		int[] right = new int[lenRight]; //Creates right Array
		for(int i = 0; i < lenLeft; i++) {
			left[i] = list[start + i]; //Fills left array with 1st half of list.
		}
		for(int j = 0; j < lenRight; j++) {
			right[j] = list[mid + j + 1]; //Fills right array with 2nd half of list.
		}
		int i = 0;
		int j = 0;
		int k = start;
		
		//https://www.geeksforgeeks.org/merge-sort/
		//The code in the book was a for loop and when I tried to use it
		//it kept giving me an indexOutOfBoundsException, so I gave up and used
		//the while loops from Geeks for Geeks.
		
		while(i < lenLeft && j < lenRight) {
			if(left[i] <= right[j]) { //Checks to decide which list has the smaller item first.
				list[k] = left[i]; 
				i++;
			}
			else {
				list[k] = right[j];
				j++;
			}
			k++;
		}
		//Fills the rest of the list with what is left from whichever list still has stuff.
		while(i < lenLeft) {
			list[k] = left[i]; 
			i++;
			k++;
		}
		while(j < lenRight) {
			list[k] = right[j];
			j++;
			k++;
		}
		return list;
	}
	/**
	 * The quick sort method that sorts the list using quick sort.
	 * Used pseudocode from the book, again just editing the index problem.
	 * @param list; The list we are trying to sort.
	 * @param start; The starting index.
	 * @param end; The ending index.
	 */
	public void auxQuickSort(int[] list, int start, int end) {
		if(start < end) {
			int done = auxPartition(list, start, end);
			auxQuickSort(list, start, done - 1);
			auxQuickSort(list, done + 1, end);
		}
	}
	/**
	 * Partitions the list into "less than" and "greater than" sections.
	 * Used as a helper method for quick sort.
	 * Used the pseudocode from the book with some minor adjustments.
	 * @param list; The list we are partitioning.
	 * @param start; The starting index.
	 * @param end; The ending index.
	 * @return pivot; The number that the list was pivoted around.
	 */
	private int auxPartition(int[] list, int start, int end) {
		int mid =(start + end)/2;
		list = auxExchange(list, mid, end);
		int pivot = start;
		for(int j = start; j <= end - 1; j++) {
			if(list[j] <= list[end]) {
				list = auxExchange(list, pivot, j);
				pivot++;
			}
		}
		list = auxExchange(list, pivot, end);
		return pivot;
	}
	/**
	 * This method was just used to exchange places in the list so I didn't
	 * have to write it in the partition code 3 times. 
	 * @param list; The list we are sorting.
	 * @param first; The index of the first number to switch.
	 * @param second; The index of the second number to switch.
	 * @return list; The list with the two numbers switched.
	 */
	private int[] auxExchange(int[] list, int first, int second) {
		int newFirst = list[second];
		list[second] = list[first];
		list[first] = newFirst;
		return list;
	}
	/**
	 * This method does the splitting of the arrays into pieces so we can 
	 * do #5 of the lab. It splits the array and then calls the auxiliary testing 
	 * methods for each sort.
	 * @param list; The list we are testing.
	 */
	public void testingStuff(int[] list) {
		int[] reset = list;
		sortedQuick = list;
		sortedMerge = null;
		for(int i = 1000; i <= reset.length; i = i*10) {
			int[] testList = new int[i];
			for(int j = 0; j < i; j++) {
				testList[j] = list[j];
			}
			testMerge(testList, i);
			testQuick(testList, i);
		}
	}
	/**
	 * This method solely tests merge sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testMerge(int[] list, int num) {
		long startTimeMerge = test.time();
		auxMergeSort(list, 0, (list.length - 1));
		if(test.flgIsSorted(sortedMerge)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		long endTimeMerge = test.time();
		System.out.println("It took merge sort " + (endTimeMerge - startTimeMerge) + " milliseconds to sort the list of size "+ num + ".");
	}
	/**
	 * This method solely tests quick sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testQuick(int[] list, int num) {
		long startTimeQuick = test.time();
		auxQuickSort(list, 0, (list.length - 1));
		if(test.flgIsSorted(sortedQuick)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		long endTimeQuick = test.time();
		System.out.println("It took quick sort " + (endTimeQuick - startTimeQuick) + " milliseconds to sort the list of size " + num + ".");
	}
	/**
	 * The main method that is used to run everything.
	 * @param args;
	 */
	public static void main(String args[]) {
		Sorts sort = new Sorts();
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		for(int i = 1000; i < 2000; i++) {
//			System.out.println(sort.sortedMerge[i]);;
//		}
//		sort.auxQuickSort(sort.numbers, 0, (sort.numbers.length - 1));
//		for(int i = 20000; i < 20500; i++) {
//			System.out.println(sort.sortedQuick[i]);;
//		}
//		System.out.println("Is the original list sorted? " + sort.test.flgIsSorted(sort.numbers));
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		sort.auxQuickSort(sort.numbers, 0, (sort.numbers.length - 1));
//		System.out.println("Is the merge sort list sorted? " + sort.test.flgIsSorted(sort.sortedMerge));
//		System.out.println("Is the quick sort list sorted? " + sort.test.flgIsSorted(sort.sortedQuick));
//		long startTimeMerge = sort.test.time();
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		System.out.println(sort.test.flgIsSorted(sort.sortedMerge));
//		long endTimeMerge = sort.test.time();
//		long startTimeQuick = sort.test.time();
//		sort.auxQuickSort(sort.numbers, 0, (sort.numbers.length - 1));
//		System.out.println(sort.test.flgIsSorted(sort.sortedQuick)); 
//		long endTimeQuick = sort.test.time();
//		System.out.println("It took merge sort " + (endTimeMerge - startTimeMerge) + " milliseconds to sort the entire list.");
//		System.out.println("It took quick sort " + (endTimeQuick - startTimeQuick) + " milliseconds to sort the entire list.");
		for(int i = 1; i <= 3; i++) {
			System.out.println(" ");
			System.out.println("************************** Testing Number " + i + " **************************");
			sort.testingStuff(sort.numbers);
		}
	}
}
=======
/**
 * CS 361: Lab 1
 * Sorts: A collection of all the sorts we will be doing.
 * So far it has Merge Sort.
 * @author Tiffany Jansen
 * @version 1.0
 */
public class Sorts {
	//The fields used in the class.
	private Reader reader;
	private int[] numbers;
	private int[] sortedMerge;
	private int[] sortedQuick;
	private Testing test;
	/**
	 * The Constructor for Sorts that sets up the lists.
	 */
	public Sorts(){
		reader = new Reader("lab1_data.txt");
		numbers = reader.getList();
		sortedMerge = new int[numbers.length];
		sortedQuick = numbers;
		test = new Testing();
	}
	/**
	 * The merge sort method that sorts the list using merge sort.
	 * Used the pseudocode from the book, just had to switch things because
	 * of the weird "arrays start at 1" thing.
	 * @param list; The list we are trying to sort.
	 * @param start; The starting index.
	 * @param end; The ending index.
	 */
	public void auxMergeSort(int[] list, int start, int end) {
		if(start < end) {
			int mid = (start + end)/2;
			auxMergeSort(list, start, mid); //Calls mergeSort on 1st half of list.
			auxMergeSort(list, mid + 1, end);//Calls mergeSort on 2nd half of list.
			sortedMerge = auxMerge(list, start, mid, end);//Does the actual "sorting" part.
		}
	}
	/**
	 * Merges the left and right side together to form the sorted list.
	 * Used mostly the pseudocode from the book, with edits for the indeces.
	 * I also used Geeks for Geeks for some of it because some of the pseudocode
	 * wasn't working correctly. *Explained more below*
	 * @param list; The list we are sorting.
	 * @param start; The starting index.
	 * @param mid; The mid of the two indexes.
	 * @param end; The ending index.
	 * @return list; The sorted list.
	 */
	private int[] auxMerge(int[] list, int start, int mid, int end) {
		int lenLeft = mid - start + 1;
		int lenRight = end - mid;
		int[] left = new int[lenLeft]; //Creates left Array
		int[] right = new int[lenRight]; //Creates right Array
		for(int i = 0; i < lenLeft; i++) {
			left[i] = list[start + i]; //Fills left array with 1st half of list.
		}
		for(int j = 0; j < lenRight; j++) {
			right[j] = list[mid + j + 1]; //Fills right array with 2nd half of list.
		}
		int i = 0;
		int j = 0;
		int k = start;
		
		//https://www.geeksforgeeks.org/merge-sort/
		//The code in the book was a for loop and when I tried to use it
		//it kept giving me an indexOutOfBoundsException, so I gave up and used
		//the while loops from Geeks for Geeks.
		
		while(i < lenLeft && j < lenRight) {
			if(left[i] <= right[j]) { //Checks to decide which list has the smaller item first.
				list[k] = left[i]; 
				i++;
			}
			else {
				list[k] = right[j];
				j++;
			}
			k++;
		}
		//Fills the rest of the list with what is left from whichever list still has stuff.
		while(i < lenLeft) {
			list[k] = left[i]; 
			i++;
			k++;
		}
		while(j < lenRight) {
			list[k] = right[j];
			j++;
			k++;
		}
		return list;
	}
	/**
	 * The quick sort method that sorts the list using quick sort.
	 * Used pseudocode from the book, again just editing the index problem.
	 * @param list; The list we are trying to sort.
	 * @param start; The starting index.
	 * @param end; The ending index.
	 */
	public void auxQuickSort(int[] list, int start, int end) {
		if(start < end) {
			int done = auxPartition(list, start, end);
			auxQuickSort(list, start, done - 1);
			auxQuickSort(list, done + 1, end);
		}
	}
	/**
	 * Partitions the list into "less than" and "greater than" sections.
	 * Used as a helper method for quick sort.
	 * Used the pseudocode from the book with some minor adjustments.
	 * @param list; The list we are partitioning.
	 * @param start; The starting index.
	 * @param end; The ending index.
	 * @return pivot; The number that the list was pivoted around.
	 */
	private int auxPartition(int[] list, int start, int end) {
		int mid =(start + end)/2;
		list = auxExchange(list, mid, end);
		int pivot = start;
		for(int j = start; j <= end - 1; j++) {
			if(list[j] <= list[end]) {
				list = auxExchange(list, pivot, j);
				pivot++;
			}
		}
		list = auxExchange(list, pivot, end);
		return pivot;
	}
	/**
	 * This method was just used to exchange places in the list so I didn't
	 * have to write it in the partition code 3 times. 
	 * @param list; The list we are sorting.
	 * @param first; The index of the first number to switch.
	 * @param second; The index of the second number to switch.
	 * @return list; The list with the two numbers switched.
	 */
	private int[] auxExchange(int[] list, int first, int second) {
		int newFirst = list[second];
		list[second] = list[first];
		list[first] = newFirst;
		return list;
	}
	/**
	 * This method does the splitting of the arrays into pieces so we can 
	 * do #5 of the lab. It splits the array and then calls the auxiliary testing 
	 * methods for each sort.
	 * @param list; The list we are testing.
	 */
	public void testingStuff(int[] list) {
		int[] reset = list;
		sortedQuick = list;
		sortedMerge = null;
		for(int i = 1000; i <= reset.length; i = i*10) {
			int[] testList = new int[i];
			for(int j = 0; j < i; j++) {
				testList[j] = list[j];
			}
			testMerge(testList, i);
			testQuick(testList, i);
		}
	}
	/**
	 * This method solely tests merge sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testMerge(int[] list, int num) {
		long startTimeMerge = test.time();
		auxMergeSort(list, 0, (list.length - 1));
		if(test.flgIsSorted(sortedMerge)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		long endTimeMerge = test.time();
		System.out.println("It took merge sort " + (endTimeMerge - startTimeMerge) + " milliseconds to sort the list of size "+ num + ".");
	}
	/**
	 * This method solely tests quick sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testQuick(int[] list, int num) {
		long startTimeQuick = test.time();
		auxQuickSort(list, 0, (list.length - 1));
		if(test.flgIsSorted(sortedQuick)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		long endTimeQuick = test.time();
		System.out.println("It took quick sort " + (endTimeQuick - startTimeQuick) + " milliseconds to sort the list of size " + num + ".");
	}
	/**
	 * The main method that is used to run everything.
	 * @param args;
	 */
	public static void main(String args[]) {
		Sorts sort = new Sorts();
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		for(int i = 1000; i < 2000; i++) {
//			System.out.println(sort.sortedMerge[i]);;
//		}
//		sort.auxQuickSort(sort.numbers, 0, (sort.numbers.length - 1));
//		for(int i = 20000; i < 20500; i++) {
//			System.out.println(sort.sortedQuick[i]);;
//		}
//		System.out.println("Is the original list sorted? " + sort.test.flgIsSorted(sort.numbers));
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		sort.auxQuickSort(sort.numbers, 0, (sort.numbers.length - 1));
//		System.out.println("Is the merge sort list sorted? " + sort.test.flgIsSorted(sort.sortedMerge));
//		System.out.println("Is the quick sort list sorted? " + sort.test.flgIsSorted(sort.sortedQuick));
//		long startTimeMerge = sort.test.time();
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		System.out.println(sort.test.flgIsSorted(sort.sortedMerge));
//		long endTimeMerge = sort.test.time();
//		long startTimeQuick = sort.test.time();
//		sort.auxQuickSort(sort.numbers, 0, (sort.numbers.length - 1));
//		System.out.println(sort.test.flgIsSorted(sort.sortedQuick)); 
//		long endTimeQuick = sort.test.time();
//		System.out.println("It took merge sort " + (endTimeMerge - startTimeMerge) + " milliseconds to sort the entire list.");
//		System.out.println("It took quick sort " + (endTimeQuick - startTimeQuick) + " milliseconds to sort the entire list.");
		for(int i = 1; i <= 3; i++) {
			System.out.println(" ");
			System.out.println("************************** Testing Number " + i + " **************************");
			sort.testingStuff(sort.numbers);
		}
	}
}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
