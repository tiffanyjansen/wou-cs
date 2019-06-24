<<<<<<< HEAD
import java.util.*;

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
	private int[] sortedRadix;
	private int[] sortedBucket;
	private int[] maxTen;
	private Testing test;
	/**
	 * The Constructor for Sorts that sets up the lists.
	 */
	public Sorts(){
		reader = new Reader("lab3_data.txt");
		numbers = reader.getList();
		sortedMerge = new int[numbers.length];
		sortedQuick = numbers;
		sortedRadix = new int[numbers.length];
		sortedBucket = numbers;
		maxTen = new int[10];
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
		//sortedQuick = list;
		sortedMerge = null;
		//sortedRadix = null;
		//sortedBucket = null;
		maxTen = new int[10];
		for(int i = 1000; i <= reset.length; i = i*10) {
			int[] testList = new int[i];
			for(int j = 0; j < i; j++) {
				testList[j] = list[j];
			}
			testMerge(testList, i);
			//testQuick(testList, i);
			//testRadix(testList, i);
			//testBucket(testList, i);
			testMaxTen(testList, i);
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
		System.out.println("It took quick sort " + (endTimeQuick - startTimeQuick) + " milliseconds to sort the list of size "+ num + ".");
	}
	/**
	 * This method solely tests radix sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testRadix(int[] list, int num) {
		long startTimeRadix = test.time();
		radixSort(list);
		long endTimeRadix = test.time();
		if(test.flgIsSorted(sortedRadix)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		System.out.println("It took radix sort " + (endTimeRadix - startTimeRadix) + " milliseconds to sort the list of size " + num + ".");
	}
	/**
	 * This method solely tests bucket sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testBucket(int[] list, int num) {
		long startTimeBucket = test.time();
		bucketSort(list, 25);
		long endTimeBucket = test.time();
		if(test.flgIsSorted(sortedBucket)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		System.out.println("It took bucket sort " + (endTimeBucket - startTimeBucket) + " milliseconds to sort the list of size "+ num + ".");
	}
	/**
	 * This method solely tests finding the max ten method. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testMaxTen(int[] list, int num) {
		long startTimeMax = test.time();
		findMaxTen(list, 0, list.length - 1);
		long endTimeMax = test.time();
		System.out.println("It took " + (endTimeMax - startTimeMax) + " milliseconds to find the max ten for the list of size "+ num + ".");
	}
	/**
	 * This is the Radix Sort. I tried to use the pseudocode from
	 * the book, but there just wasn't enough there so i went to Geeks
	 * for Geeks and went through their code until I understood what was
	 * happening.
	 * @param list; the list we are trying to sort.
	 */
	public void radixSort(int[] list) {
		//****https://www.geeksforgeeks.org/radix-sort/****
		//Website where I found the code.
		int max = findMax(list); //Finds the biggest number in the list.
		sortedRadix = list; //sets the final sorted list to the original.
		//When I was first doing this i kept sorting the original list by digit
		//and it wasn't sorting in order, so I improvised. Now it sorts the sortedRadix
		//list until it's actually sorted.
		for(int exp = 1; (max/exp) > 0; exp = exp*10) {//The for loop. This goes by powers of 
			//tens. So it starts at 1 and increases until the max number divided by the power of 10
			//is less than or equal to 0, because then you've done all of the digits of the largest 
			//number, no reason to continue.
			countingSort(sortedRadix, exp); //Uses a stable sort(counting sort) to sort each digit.
		}
	}
	/**
	 * This method literally just finds the max number in the list.
	 * @param list; the list we are going to sort.
	 * @return max; the max number in our list.
	 */
	private int findMax(int[] list) {
		int max = list[0];//initialize max as the first number in the list.
		for(int i = 1; i<list.length; i++) {//decided which number is bigger.
			if(list[i] > max) {
				max = list[i];//if the number in the list is bigger, then set max to that number.
			}
		}
		return max;
	}
	/**
	 * Counting sort. I first coded this to sort the list, and then it got complicated
	 * when we got into using it with the radix sort, so i got some help from Geeks for
	 * Geeks. I'm going to explain it line by line in the code too. 
	 * @param list; The list we are sorting.
	 * @param exp; the power of 10 we are sorting at.
	 */
	private void countingSort(int[] list, int exp) {
		int[] sortedArray = new int[list.length]; //create a new array that will become the sorted one.
		//(In the book it's called B[].)
		int[] countingArray = new int[10]; //creates the array that is going to count the number
		//of occurrences of everything. (In the book it's called C[].)
		for(int i = 0; i < 10; i++) {
			countingArray[i] = 0; //Initialize everything to 0.
		}
		for(int i = 0; i < list.length; i++) {
			int x = list[i]/exp; //Finds the value of the list at the current power of 10 we are working at.
			//Example: if our number is 12500 and we are sorting at the 100s then we would have:
			// 12500/100 = 125. The "5" is the important digit.
			countingArray[x%10] += 1; //Finds the remainder of x when divided by 10 and then uses that as the 
			//index and adds one to that. 
			//Example: as above, x = 125, then x%10 = 5, then countingArray[5] += 1. Because there is another
			//100s digit with the number 5.
		}
		for(int j = 1; j < 10; j++) {
			countingArray[j] = countingArray[j] + countingArray[j-1]; //Adds the previous elements so the
			//indexes are correct.
		}
		for(int k = list.length - 1; k >= 0; k--) {
			int x = (list[k]/exp); //Same thing as above.
			sortedArray[(countingArray[x%10] - 1)] = list[k]; //This is also the same, except we have to 
			//subtract one because our array starts at 0 and goes to length - 1. 
			countingArray[x%10] -= 1; //Decrements the spot so we don't have 2 numbers in the same spot.
		}
		sortedRadix = sortedArray; //set the sortedRadix array as the newly sorted array.
	}
	/**
	 * This is the bucket sort method. The pseudocode in the book wasn't very
	 * helpful and Geeks for Geeks didn't really have anything useful either 
	 * so I did some more searching until I found this one. It works and everything.
	 * Everything will be explained more below.
	 * @param list; The list we want to be sorted.
	 * @param bucketSize; The max size of each bucket.
	 */
	public void bucketSort(int[] list, int bucketSize) {
		//****http://www.growingwiththeweb.com/2015/06/bucket-sort.html****
		//This is the website where I found my code, since the pseudocode
		//in the book was very vague and hard to convert to Java.
		int min = findMin(list); //find the min
		int max = findMax(list); //find the max
		int bCount = (max - min) / bucketSize + 1; //Find the number of buckets needed
		// Calculate the range of the data and then divide by the bucket size plus 1 to 
		// account for the 0 index at the beginning.
		List<List<Integer>> buckets = new ArrayList<List<Integer>>(bCount); //Create our list of buckets
		for(int i = 0; i < bCount; i++) {
			buckets.add(new ArrayList<Integer>()); //create ArrayLists to go into the original list.
		}
		
		//Distribute array into buckets.
		for(int i = 0; i < list.length; i++) {
			buckets.get((list[i] - min) / bucketSize).add(list[i]);
		}
		
		//Sort buckets and place back into starting array.
		int current = 0;
		for(int i = 0; i < buckets.size(); i++) {
			int[] bucketArray = new int[buckets.get(i).size()];
			bucketArray = toArray(buckets.get(i)); //Convert the list to an array.
			bucketArray = sort(bucketArray); //sort it.
			for(int j = 0; j < bucketArray.length; j++) {
				list[current] = bucketArray[j]; //Place the data into the correct spot
				current++; //The correct spot.
			}
		}
		//Make sure our list ends up being placed in the sorted one.
		sortedBucket = list;
	}
	/**
	 * Finds the minimum value in the list.
	 * @param list; The list that we need to minimum from
	 * @return min; The minimum number in the list.
	 */
	private int findMin(int[] list) {
		int min = list[0];
		for(int i = 1; i < list.length; i++) {
			if(list[i] < min) {
				min = list[i];
			}
		}
		return min;
	}
	/**
	 * Convert the list into an array.
	 * @param list; The list we need converted.
	 * @return array; The array version of our list.
	 */
	private int[] toArray(List<Integer> list) {
		int[] retList = new int[list.size()];
		for(int i = 0; i< list.size(); i++) {
			retList[i] = list.get(i);
		}
		return retList;
	}
	/**
	 * Sorting method for the lists. *Insertion Sort*
	 * I used the pseudocode from the book for this one
	 * since it was straight forward. Just had to fix
	 * some indexing issues.
	 * @param list; The list we want to sort
	 * @return sortedList; The sorted list.
	 */
	private int[] sort(int[] list) {
		for(int j = 1; j < list.length; j++) {
			int key = list[j];
			int i = j-1;
			while(i >=0 && list[i] > key) {
				list[i + 1] = list[i];
				i = i-1;
			}
			list[i + 1] = key;
		}
		return list;
	}
	/**
	 * This method finds the max 10 using a Divide and Conquer 
	 * approach. It is very similar to Merge Sort.
	 * @param list; The list we want to find the max of.
	 * @param start; the starting index.
	 * @param end; the ending index.
	 */
	public void findMaxTen(int[] list, int start, int end) {
		//This part is a copy of merge sort. (I literally copied
		// and pasted this part).
		if(start < end) {
			int mid = (start + end)/2;
			findMaxTen(list, start, mid);
			findMaxTen(list, mid + 1, end);
			list = findMaxHelp(list, start, mid, end);
			
			//This part takes the list and adds it to our maxTen
			// list since we only want the top 10 values in it.
			if((end-start) < 10) {
				int j = 0;
				for(int i = start; i < end; i++) {
					maxTen[j] = list[i];
					j++;
				}
			}
			else {
				int k = start;
				for(int i = 0; i < 10; i++) {
					maxTen[i] = list[k];
					k++;
				}
			}
		}
	}
	/**This is the helper method for the find max, it is a copy
	 * of the auxMerge method I wrote above only this time it has
	 * a > sign instead of <=.
	 * @param list; the list we are "sorting".
	 * @param start; the starting index.
	 * @param mid; the middle index.
	 * @param end; the ending index.
	 * @return list; the "sorted" list.
	 */
	private int[] findMaxHelp(int[] list, int start, int mid, int end) {
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
		while(i < lenLeft && j < lenRight) {
			if(left[i] > right[j]) { //Checks to decide which list has the larger item first.
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
	 * The main method that is used to run everything.
	 * @param args;
	 */
	public static void main(String args[]) {
		Sorts sort = new Sorts();
//		long startTimeM = sort.test.time();
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		long endTimeM = sort.test.time();
//		System.out.println("Is the list sorted after Merge Sort? " + sort.test.flgIsSorted(sort.sortedMerge));
//		long startTimeR = sort.test.time();
//		sort.radixSort(sort.numbers);
//		long endTimeR = sort.test.time();
//		System.out.println("Is the list sorted after Radix Sort? " + sort.test.flgIsSorted(sort.sortedRadix));
//		long startTimeB = sort.test.time();
//		sort.bucketSort(sort.sortedBucket, 25);
//		long endTimeB = sort.test.time();
//		System.out.println("Is the list sorted after Bucket Sort? " + sort.test.flgIsSorted(sort.sortedBucket));
//		System.out.println(" ");
//		System.out.println("Merge sort took " + (endTimeM - startTimeM) + " milliseconds to sort the list.");
//		System.out.println("Radix sort took " + (endTimeR - startTimeR) + " milliseconds to sort the list.");
//		System.out.println("Bucket sort took " + (endTimeB - startTimeB) + " milliseconds to sort the list.");
		for(int i = 1; i <= 3; i++) {
			System.out.println(" ");
			System.out.println("******************** Testing Number " + i + " ********************");
			sort.testingStuff(sort.numbers);
		}
//		sort.findMaxTen(sort.numbers, 0, sort.numbers.length - 1);
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		int j = sort.sortedMerge.length - 1;
//		System.out.println("Finding Max Ten compared to Merge Sort Top 10");
//		for(int i = 0; i< 10; i++) {
//			System.out.println(i + ":" + sort.maxTen[i] + "               " + i + ":" + sort.sortedMerge[j]);
//			j--;
//		}
		
	}
}
=======
import java.util.*;

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
	private int[] sortedRadix;
	private int[] sortedBucket;
	private int[] maxTen;
	private Testing test;
	/**
	 * The Constructor for Sorts that sets up the lists.
	 */
	public Sorts(){
		reader = new Reader("lab3_data.txt");
		numbers = reader.getList();
		sortedMerge = new int[numbers.length];
		sortedQuick = numbers;
		sortedRadix = new int[numbers.length];
		sortedBucket = numbers;
		maxTen = new int[10];
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
		//sortedQuick = list;
		sortedMerge = null;
		//sortedRadix = null;
		//sortedBucket = null;
		maxTen = new int[10];
		for(int i = 1000; i <= reset.length; i = i*10) {
			int[] testList = new int[i];
			for(int j = 0; j < i; j++) {
				testList[j] = list[j];
			}
			testMerge(testList, i);
			//testQuick(testList, i);
			//testRadix(testList, i);
			//testBucket(testList, i);
			testMaxTen(testList, i);
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
		System.out.println("It took quick sort " + (endTimeQuick - startTimeQuick) + " milliseconds to sort the list of size "+ num + ".");
	}
	/**
	 * This method solely tests radix sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testRadix(int[] list, int num) {
		long startTimeRadix = test.time();
		radixSort(list);
		long endTimeRadix = test.time();
		if(test.flgIsSorted(sortedRadix)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		System.out.println("It took radix sort " + (endTimeRadix - startTimeRadix) + " milliseconds to sort the list of size " + num + ".");
	}
	/**
	 * This method solely tests bucket sort. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testBucket(int[] list, int num) {
		long startTimeBucket = test.time();
		bucketSort(list, 25);
		long endTimeBucket = test.time();
		if(test.flgIsSorted(sortedBucket)) {
			System.out.println("The list is sorted.");
		}
		else {
			System.out.println("The list is not sorted.");
		}
		System.out.println("It took bucket sort " + (endTimeBucket - startTimeBucket) + " milliseconds to sort the list of size "+ num + ".");
	}
	/**
	 * This method solely tests finding the max ten method. It prints out all the information
	 * we want to the console.
	 * @param list; The list we are testing/sorting.
	 * @param num; The number of elements currently in our list.
	 */
	private void testMaxTen(int[] list, int num) {
		long startTimeMax = test.time();
		findMaxTen(list, 0, list.length - 1);
		long endTimeMax = test.time();
		System.out.println("It took " + (endTimeMax - startTimeMax) + " milliseconds to find the max ten for the list of size "+ num + ".");
	}
	/**
	 * This is the Radix Sort. I tried to use the pseudocode from
	 * the book, but there just wasn't enough there so i went to Geeks
	 * for Geeks and went through their code until I understood what was
	 * happening.
	 * @param list; the list we are trying to sort.
	 */
	public void radixSort(int[] list) {
		//****https://www.geeksforgeeks.org/radix-sort/****
		//Website where I found the code.
		int max = findMax(list); //Finds the biggest number in the list.
		sortedRadix = list; //sets the final sorted list to the original.
		//When I was first doing this i kept sorting the original list by digit
		//and it wasn't sorting in order, so I improvised. Now it sorts the sortedRadix
		//list until it's actually sorted.
		for(int exp = 1; (max/exp) > 0; exp = exp*10) {//The for loop. This goes by powers of 
			//tens. So it starts at 1 and increases until the max number divided by the power of 10
			//is less than or equal to 0, because then you've done all of the digits of the largest 
			//number, no reason to continue.
			countingSort(sortedRadix, exp); //Uses a stable sort(counting sort) to sort each digit.
		}
	}
	/**
	 * This method literally just finds the max number in the list.
	 * @param list; the list we are going to sort.
	 * @return max; the max number in our list.
	 */
	private int findMax(int[] list) {
		int max = list[0];//initialize max as the first number in the list.
		for(int i = 1; i<list.length; i++) {//decided which number is bigger.
			if(list[i] > max) {
				max = list[i];//if the number in the list is bigger, then set max to that number.
			}
		}
		return max;
	}
	/**
	 * Counting sort. I first coded this to sort the list, and then it got complicated
	 * when we got into using it with the radix sort, so i got some help from Geeks for
	 * Geeks. I'm going to explain it line by line in the code too. 
	 * @param list; The list we are sorting.
	 * @param exp; the power of 10 we are sorting at.
	 */
	private void countingSort(int[] list, int exp) {
		int[] sortedArray = new int[list.length]; //create a new array that will become the sorted one.
		//(In the book it's called B[].)
		int[] countingArray = new int[10]; //creates the array that is going to count the number
		//of occurrences of everything. (In the book it's called C[].)
		for(int i = 0; i < 10; i++) {
			countingArray[i] = 0; //Initialize everything to 0.
		}
		for(int i = 0; i < list.length; i++) {
			int x = list[i]/exp; //Finds the value of the list at the current power of 10 we are working at.
			//Example: if our number is 12500 and we are sorting at the 100s then we would have:
			// 12500/100 = 125. The "5" is the important digit.
			countingArray[x%10] += 1; //Finds the remainder of x when divided by 10 and then uses that as the 
			//index and adds one to that. 
			//Example: as above, x = 125, then x%10 = 5, then countingArray[5] += 1. Because there is another
			//100s digit with the number 5.
		}
		for(int j = 1; j < 10; j++) {
			countingArray[j] = countingArray[j] + countingArray[j-1]; //Adds the previous elements so the
			//indexes are correct.
		}
		for(int k = list.length - 1; k >= 0; k--) {
			int x = (list[k]/exp); //Same thing as above.
			sortedArray[(countingArray[x%10] - 1)] = list[k]; //This is also the same, except we have to 
			//subtract one because our array starts at 0 and goes to length - 1. 
			countingArray[x%10] -= 1; //Decrements the spot so we don't have 2 numbers in the same spot.
		}
		sortedRadix = sortedArray; //set the sortedRadix array as the newly sorted array.
	}
	/**
	 * This is the bucket sort method. The pseudocode in the book wasn't very
	 * helpful and Geeks for Geeks didn't really have anything useful either 
	 * so I did some more searching until I found this one. It works and everything.
	 * Everything will be explained more below.
	 * @param list; The list we want to be sorted.
	 * @param bucketSize; The max size of each bucket.
	 */
	public void bucketSort(int[] list, int bucketSize) {
		//****http://www.growingwiththeweb.com/2015/06/bucket-sort.html****
		//This is the website where I found my code, since the pseudocode
		//in the book was very vague and hard to convert to Java.
		int min = findMin(list); //find the min
		int max = findMax(list); //find the max
		int bCount = (max - min) / bucketSize + 1; //Find the number of buckets needed
		// Calculate the range of the data and then divide by the bucket size plus 1 to 
		// account for the 0 index at the beginning.
		List<List<Integer>> buckets = new ArrayList<List<Integer>>(bCount); //Create our list of buckets
		for(int i = 0; i < bCount; i++) {
			buckets.add(new ArrayList<Integer>()); //create ArrayLists to go into the original list.
		}
		
		//Distribute array into buckets.
		for(int i = 0; i < list.length; i++) {
			buckets.get((list[i] - min) / bucketSize).add(list[i]);
		}
		
		//Sort buckets and place back into starting array.
		int current = 0;
		for(int i = 0; i < buckets.size(); i++) {
			int[] bucketArray = new int[buckets.get(i).size()];
			bucketArray = toArray(buckets.get(i)); //Convert the list to an array.
			bucketArray = sort(bucketArray); //sort it.
			for(int j = 0; j < bucketArray.length; j++) {
				list[current] = bucketArray[j]; //Place the data into the correct spot
				current++; //The correct spot.
			}
		}
		//Make sure our list ends up being placed in the sorted one.
		sortedBucket = list;
	}
	/**
	 * Finds the minimum value in the list.
	 * @param list; The list that we need to minimum from
	 * @return min; The minimum number in the list.
	 */
	private int findMin(int[] list) {
		int min = list[0];
		for(int i = 1; i < list.length; i++) {
			if(list[i] < min) {
				min = list[i];
			}
		}
		return min;
	}
	/**
	 * Convert the list into an array.
	 * @param list; The list we need converted.
	 * @return array; The array version of our list.
	 */
	private int[] toArray(List<Integer> list) {
		int[] retList = new int[list.size()];
		for(int i = 0; i< list.size(); i++) {
			retList[i] = list.get(i);
		}
		return retList;
	}
	/**
	 * Sorting method for the lists. *Insertion Sort*
	 * I used the pseudocode from the book for this one
	 * since it was straight forward. Just had to fix
	 * some indexing issues.
	 * @param list; The list we want to sort
	 * @return sortedList; The sorted list.
	 */
	private int[] sort(int[] list) {
		for(int j = 1; j < list.length; j++) {
			int key = list[j];
			int i = j-1;
			while(i >=0 && list[i] > key) {
				list[i + 1] = list[i];
				i = i-1;
			}
			list[i + 1] = key;
		}
		return list;
	}
	/**
	 * This method finds the max 10 using a Divide and Conquer 
	 * approach. It is very similar to Merge Sort.
	 * @param list; The list we want to find the max of.
	 * @param start; the starting index.
	 * @param end; the ending index.
	 */
	public void findMaxTen(int[] list, int start, int end) {
		//This part is a copy of merge sort. (I literally copied
		// and pasted this part).
		if(start < end) {
			int mid = (start + end)/2;
			findMaxTen(list, start, mid);
			findMaxTen(list, mid + 1, end);
			list = findMaxHelp(list, start, mid, end);
			
			//This part takes the list and adds it to our maxTen
			// list since we only want the top 10 values in it.
			if((end-start) < 10) {
				int j = 0;
				for(int i = start; i < end; i++) {
					maxTen[j] = list[i];
					j++;
				}
			}
			else {
				int k = start;
				for(int i = 0; i < 10; i++) {
					maxTen[i] = list[k];
					k++;
				}
			}
		}
	}
	/**This is the helper method for the find max, it is a copy
	 * of the auxMerge method I wrote above only this time it has
	 * a > sign instead of <=.
	 * @param list; the list we are "sorting".
	 * @param start; the starting index.
	 * @param mid; the middle index.
	 * @param end; the ending index.
	 * @return list; the "sorted" list.
	 */
	private int[] findMaxHelp(int[] list, int start, int mid, int end) {
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
		while(i < lenLeft && j < lenRight) {
			if(left[i] > right[j]) { //Checks to decide which list has the larger item first.
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
	 * The main method that is used to run everything.
	 * @param args;
	 */
	public static void main(String args[]) {
		Sorts sort = new Sorts();
//		long startTimeM = sort.test.time();
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		long endTimeM = sort.test.time();
//		System.out.println("Is the list sorted after Merge Sort? " + sort.test.flgIsSorted(sort.sortedMerge));
//		long startTimeR = sort.test.time();
//		sort.radixSort(sort.numbers);
//		long endTimeR = sort.test.time();
//		System.out.println("Is the list sorted after Radix Sort? " + sort.test.flgIsSorted(sort.sortedRadix));
//		long startTimeB = sort.test.time();
//		sort.bucketSort(sort.sortedBucket, 25);
//		long endTimeB = sort.test.time();
//		System.out.println("Is the list sorted after Bucket Sort? " + sort.test.flgIsSorted(sort.sortedBucket));
//		System.out.println(" ");
//		System.out.println("Merge sort took " + (endTimeM - startTimeM) + " milliseconds to sort the list.");
//		System.out.println("Radix sort took " + (endTimeR - startTimeR) + " milliseconds to sort the list.");
//		System.out.println("Bucket sort took " + (endTimeB - startTimeB) + " milliseconds to sort the list.");
		for(int i = 1; i <= 3; i++) {
			System.out.println(" ");
			System.out.println("******************** Testing Number " + i + " ********************");
			sort.testingStuff(sort.numbers);
		}
//		sort.findMaxTen(sort.numbers, 0, sort.numbers.length - 1);
//		sort.auxMergeSort(sort.numbers, 0, (sort.numbers.length - 1));
//		int j = sort.sortedMerge.length - 1;
//		System.out.println("Finding Max Ten compared to Merge Sort Top 10");
//		for(int i = 0; i< 10; i++) {
//			System.out.println(i + ":" + sort.maxTen[i] + "               " + i + ":" + sort.sortedMerge[j]);
//			j--;
//		}
		
	}
}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
