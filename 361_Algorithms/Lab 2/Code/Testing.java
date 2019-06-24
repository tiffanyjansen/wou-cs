<<<<<<< HEAD
/**
 * CS 361: Lab 1
 * This class is used for Testing stuff.
 * Used mostly by sorts to do all the testing.
 * I was going to try to do all the testing here, but it wasn't
 * working the way I wanted to. It got really complicated.
 * @author Tiffany Jansen
 * @version 1.0
 */
public class Testing {
	/**
	 * Tests to see if the list is sorted or not. I came up
	 * with all of this by myself. It took me a while. I kind of
	 * tried to use the merge sort as an example, except I only had
	 * to check the first half... idk. 
	 * @param list; The list we are checking.
	 * @return true if the list is sorted, false otherwise.
	 */
	public boolean flgIsSorted(int[] list) {
		boolean isSorted;
		if(list.length > 1) {
			int mid = list.length/2;
			int[] newList = new int[mid];
			for(int i = 0; i < mid; i++) { //Splits the list.
				newList[i] = list[i];      //But only uses 1st 1/2 of it.
			}	//"If one half is sorted then the other half is sorted."
			flgIsSorted(newList);          //Calls the method again to keep splitting it.
			isSorted = checkSort(list, mid); //Checks if list of length > 1 is sorted.
		}
		else {
			isSorted = true; //"If length == 1, then the list is sorted."
		}
		return isSorted;
	}
	/**
	 * This method is the helper method that actually checks to make
	 * sure the list is sorted. It checks the first half of the numbers
	 * and the next one and returns true if it's sorted and false otherwise.	
	 * @param list; The list we are checking.
	 * @param mid; The middle of the list.
	 * @return true if the list is sorted, false otherwise.
	 */
	private boolean checkSort(int[] list, int mid) {
		int i = 0;
		boolean isSorted = true; 
		while(isSorted && i < mid) {
			if(list[i] > list[i + 1]) {//Compares every element to the one after it.
				isSorted = false;//If the one that's supposed to be less is greater, the list isn't sorted.
			}//"If the last element in the first half is less than the first element of the second half
			//then the list is sorted."
			i++;
		}
		return isSorted;
	}
	/**
	 * Uses System.nanoTime() to find the time in nanoseconds and 
	 * then converts it to milliseconds. 
	 * @return time in milliseconds.
	 */
	public long time() {
		//https://docs.oracle.com/javase/8/docs/api/
		//This is just the java docs site I used to look up System.nanoTime() since
		//I had never used it before.
		long time = System.nanoTime();
		//https://www.calculateme.com/Time/Nanoseconds/ToMilliseconds.htm
		//1 Millisecond = 1,000,000 Nanoseconds
		//I used the website about to find out to convert from nanoseconds to milliseconds.
		//Since i couldn't multiply by 0.000001 I had to divide instead.
		//If you try multiplying you get an error because it thinks the output becomes a double.
		return (time/1000000);
	}
}
=======
/**
 * CS 361: Lab 1
 * This class is used for Testing stuff.
 * Used mostly by sorts to do all the testing.
 * I was going to try to do all the testing here, but it wasn't
 * working the way I wanted to. It got really complicated.
 * @author Tiffany Jansen
 * @version 1.0
 */
public class Testing {
	/**
	 * Tests to see if the list is sorted or not. I came up
	 * with all of this by myself. It took me a while. I kind of
	 * tried to use the merge sort as an example, except I only had
	 * to check the first half... idk. 
	 * @param list; The list we are checking.
	 * @return true if the list is sorted, false otherwise.
	 */
	public boolean flgIsSorted(int[] list) {
		boolean isSorted;
		if(list.length > 1) {
			int mid = list.length/2;
			int[] newList = new int[mid];
			for(int i = 0; i < mid; i++) { //Splits the list.
				newList[i] = list[i];      //But only uses 1st 1/2 of it.
			}	//"If one half is sorted then the other half is sorted."
			flgIsSorted(newList);          //Calls the method again to keep splitting it.
			isSorted = checkSort(list, mid); //Checks if list of length > 1 is sorted.
		}
		else {
			isSorted = true; //"If length == 1, then the list is sorted."
		}
		return isSorted;
	}
	/**
	 * This method is the helper method that actually checks to make
	 * sure the list is sorted. It checks the first half of the numbers
	 * and the next one and returns true if it's sorted and false otherwise.	
	 * @param list; The list we are checking.
	 * @param mid; The middle of the list.
	 * @return true if the list is sorted, false otherwise.
	 */
	private boolean checkSort(int[] list, int mid) {
		int i = 0;
		boolean isSorted = true; 
		while(isSorted && i < mid) {
			if(list[i] > list[i + 1]) {//Compares every element to the one after it.
				isSorted = false;//If the one that's supposed to be less is greater, the list isn't sorted.
			}//"If the last element in the first half is less than the first element of the second half
			//then the list is sorted."
			i++;
		}
		return isSorted;
	}
	/**
	 * Uses System.nanoTime() to find the time in nanoseconds and 
	 * then converts it to milliseconds. 
	 * @return time in milliseconds.
	 */
	public long time() {
		//https://docs.oracle.com/javase/8/docs/api/
		//This is just the java docs site I used to look up System.nanoTime() since
		//I had never used it before.
		long time = System.nanoTime();
		//https://www.calculateme.com/Time/Nanoseconds/ToMilliseconds.htm
		//1 Millisecond = 1,000,000 Nanoseconds
		//I used the website about to find out to convert from nanoseconds to milliseconds.
		//Since i couldn't multiply by 0.000001 I had to divide instead.
		//If you try multiplying you get an error because it thinks the output becomes a double.
		return (time/1000000);
	}
}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
