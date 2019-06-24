import java.io.*;
import java.util.Random;

/**
 *  SortSearch sets up an array of random numbers of size listsize to test several
 *  sorting and searching algorithms which will then be instrumented to count the
 *  number of comparisons done and the execution times at various list sizes
 *  
 *  @author Mitchel Fry & Tiffany Jansen
 *  @version CS260 Lab #1, 10/04/2017
 */
public class SortSearch
{
    private int listsize = 10;
    private static final int RANGE = 100000;
    private int[] numList;
    private static Random rand;
    private long linAccu;
    private long binAccu;
    private long linTimeAccu;
    private long binTimeAccu;
  
    
    /**
     * Default constructor, just initialize the numList array 
     */
    public SortSearch()
    {
        numList = new int[listsize];
    }

    /**
     * Non-default constructor, initialize the numList array to the given size
     * 
     * @param size of array to create
     */
    public SortSearch(int size)
    {
        listsize = size;
        numList = new int[listsize];
    }

    /**
     * Fills the array with listsize integers
     */
    public void fillArray()
    {
        rand = new Random();

        for(int i = 0; i < listsize; i++) {
            numList[i] = rand.nextInt(RANGE);
        }
    }

    /**
     * Prints the entire array in indexed order
     */
    public void printArray()
    {
        for(int i = 0; i < numList.length; i++) {
            System.out.println("Element " + i + ": " + numList[i]);
        }
    }

    //================== Sorting Methods =======================      
    /**
     * Sorts the array using Insertion Sort logic
     */
    public void insertionSort()
    {
    	long beginTime = getSystemTime();
    	long insertionCounter = 0;
    	for(int i = 1; i < numList.length; i++){
            // insert numList[i] into numList[0:i-1]
            int j, t = numList[i];
            for(j = i - 1; j >= 0 && t < numList[ j]; j--){
                numList[j + 1] = numList[j]; //shuffle the empty space
                insertionCounter++;
            }
            numList[j + 1] = t; //assign t into the empty space
        }
    	long endTime = getSystemTime();
    	System.out.println("**Insertion Sort Time**");
    	System.out.println("Number of comparisons: " + insertionCounter + ".");
    	System.out.println("Time to sort: " + (endTime - beginTime) + " nanoseconds.");
    	System.out.println();
    }
 
    /**
     * Sorts the array using Insertion Sort logic
     */
    public void selectionSort()
    {
        long beginSortTime = getSystemTime();
    	long selectionCounter = 0;
    	int t;
        for(int i = 0; i < numList.length; i++){
            t = i; //Index of the smallest element in this pass
            for(int j = i; j < numList.length; j++){
                if(numList[j] < numList[t]){
                	selectionCounter++;
                    t = j;
                }
            }
            swap(i, t); //swap the values at these positions
        }
        long endSortTime = getSystemTime();
        System.out.println("**Selection Sort Stuff**");
        System.out.println("Number of comparisons: " + selectionCounter + ".");
        System.out.println("Time to sort: " + (endSortTime - beginSortTime) + " nanoseconds.");
        System.out.println();
    }

 
    /**
     * Sorts the array using Bubble Sort logic (bad way to sort)
     */
    public void bubbleSort()
    {
    	long beginSortTime = getSystemTime();
     	long bubbleCounter = 0;
        for(int out = numList.length - 1; out > 0; out--) { // outer loop (backward)
            for(int in = 0; in < out; in++) {// inner loop (forward)
                if(numList[in] > numList[ in + 1]) {// out of order?
                    swap(in, in + 1);
                    bubbleCounter++;
                }
            }
        }
        long endSortTime = getSystemTime();
        System.out.println("**Bubble Sort Stuff**");
        System.out.println("Number of comparisons: " + bubbleCounter + ".");
        System.out.println("Time to sort: " + (endSortTime - beginSortTime) + " nanoseconds.");
        System.out.println();
    }

    //================== Search Methods =======================    
    /**
     * A shell method for your linear search logic
     * 
     * @param searchNum the value to search for in the array 
     * @return returns true is searchNum is found in the array, otherwise false
     */
    public boolean containsLinear(int searchNum)
    {
    	long linSearchCounter = 0;
    	long beginLinSearchTime = getSystemTime();
    	long endLinSearchTime;
    	for(int i = 0; i < numList.length; i++){
    		linSearchCounter++;
    		if(numList[i] == searchNum){
    			endLinSearchTime = getSystemTime();
    			linAccu = linAccu + linSearchCounter;
    			linTimeAccu = linTimeAccu + (endLinSearchTime - beginLinSearchTime);
    			return true;
    		}
    	}
    	endLinSearchTime = getSystemTime();
    	linAccu = linAccu + linSearchCounter;
    	linTimeAccu = linTimeAccu + (endLinSearchTime - beginLinSearchTime);
    	return false;
    }
  
    /**
     * A shell method for your binary search logic
     * 
     * @param searchNum the value to search for in the array 
     * @return returns true is searchNum is found in the array, otherwise false
     */
    public boolean containsBinary(int searchNum)
    {
        long binSearchCounter = 0;
    	int low = 0;
        int high = numList.length - 1;
        long beginBinSearchTime = getSystemTime();
        long endBinSearchTime;
        while(low <= high){
        	int mid = (low + high) / 2;
        	binSearchCounter++;
        	if(numList[mid] == searchNum){
        		endBinSearchTime = getSystemTime();
        		binAccu = binAccu + binSearchCounter;
            	binTimeAccu = binTimeAccu + (endBinSearchTime - beginBinSearchTime);
        		return true;
        	}
        	else if(numList[mid] < searchNum){
        		low = mid + 1;
        	}
        	else{
        		high = mid - 1;
        	}
        }
        endBinSearchTime = getSystemTime();
        binAccu = binAccu + binSearchCounter;
    	binTimeAccu = binTimeAccu + (endBinSearchTime - beginBinSearchTime);
    	return false;
    }

    /**
     * This method can be used to print your results of a run.  Refactor this as appropriate for
     * what ever method you choose to collect your data points.  I would suggest writing to a 
     * comma delimited text file so that you can easily import the data into a spreadsheet for
     * graphing and analysis.
     * 
     * @param sortType A string stating the type of sort that was done
     */
    public void printSortingMetrics(String sortType, long counter)
    {
        //This is where you would the time, compares, and move counters
        System.out.println("****Emperical measures for: " + sortType);
        System.out.println("****Number of Comparisions: " + counter);
    }
    
    //============ Private utility methods ==============
    /**
     * This value is the internal system time that is the measured in
     * the number of nano-seconds since Jan. 1, 1970.
     * 
     * @return The current time of the system clock
     */
    private long getSystemTime()
    {
        return System.nanoTime();
    }

    
    /**
     * Swap routine to re-order the ith and jth array values
     *
     * @param  i   an array location to be swapped with j
     * @param  j   an array location to be swapped with i
     */
    private void swap(int i, int j)
    {
        int temp = numList[i];
        numList[i] = numList[j];
        numList[j] = temp;
    }
 
        
    //============Program entry point MAIN======================
    /**
     * Standard class method "main".  Modify this to collect the
     * empherical metrics data on the sorting and searching methods
     */
    public static void main(String[] args)
    {   	
    	for(int i = 1; i <= 10; i++) {
    		SortSearch test = new SortSearch(10000 * i);
			test.fillArray();
			test.selectionSort();
    		
			for(int k = 1; k <= 100; k++) {
    			int searchNum = rand.nextInt(RANGE);
    			test.containsBinary(searchNum);
    			test.containsLinear(searchNum);
    		}
			
			long linComparisons = test.linAccu / 100;
			long binComparisons = test.binAccu / 100;
			
			long linTime = test.linTimeAccu / 100;
			long binTime = test.binTimeAccu / 100;
			
			System.out.println("Number of elements: " + (10000*i));
			System.out.println("Average number of comparisons for Linear Search:");
			System.out.println(linComparisons);
			System.out.println("Average time in nanoseconds for Linear Search:");
			System.out.println(linTime);
			System.out.println("Average number of comparisons for Binary Search:");
			System.out.println(binComparisons);
			System.out.println("Average time in nanoseconds for Binary Search:");
			System.out.println(binTime);
			
			try {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("SearchingStats.txt", true)));
				writer.println("Number of elements: " + (10000*i));
				writer.println("Average number of comparisons for Linear Search:");
				writer.println(linComparisons);
				writer.println("Average time in nanoseconds for Linear Search:");
				writer.println(linTime);
				writer.println("Average number of comparisons for Binary Search:");
				writer.println(binComparisons);
				writer.println("Average time in nanoseconds for Binary Search:");
				writer.println(binTime);
				writer.println();
				writer.close();
			}
			catch(IOException e) {
				System.err.println(e);
			}
    	}
    }
}
