<<<<<<< HEAD
import java.io.*;
/**
 * CS 361: Lab 1
 * This is the Reader class. It just reads the file for the numbers.
 * It also gives me access to the array with a getList method.
 * @author Tiffany Jansen
 * @version 1.0
 */
public class Reader {
	//Field for the class. (Literally just the list of the numbers in an array.)
	private int[] list;
	/**
	 * Constructor for the class.
	 * @param filename; The file we wish to read.
	 */
	public Reader(String filename){
		list = new int[10000000];
		readFile(filename);
	}
	/**
	 * Reads the file.
	 * @param filename; The filename of the file we are trying to read.
	 */
	private void readFile(String filename) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String num = reader.readLine();//I found all of this in my CS 161/162 textbook.
			int i = 0;
			while(num != null){
				list[i] = Integer.parseInt(num); //Takes the line read, converts it to an int and then adds it to my list.
				//https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
				//This is what I used to convert my list of Strings to integers.
				//The parseInt method just converts the String to an int. Basically just removes the quotes.
				num = reader.readLine(); //Reads next line.
				i++;
			}
			reader.close(); //Closes the reader.
		}
		catch(FileNotFoundException e) {
			System.err.println("Unable to open " + filename); //Just error handling.
		}
		catch(IOException e) {
			System.err.println("A problem was encountered reading " + filename); //Just error handling.
		}
	}
	/**
	 * Adds all of the numbers in the list.
	 * @return total; The sum of the entire list.
	 */
	public long addList() {
		long total = 0;
		for(int i = 0; i < list.length; i++) {
			total = total + list[i]; //Adds everything in the list together.
		}
		return total; //Returns the sum.
	}
	/**
	 * Gets the list of numbers and returns it.
	 * @return list; The list of the numbers that are in our file.
	 */
	public int[] getList(){
		return list; //Returns the list.
	}
	/**
	 * Main method for checking things out and running everything.
	 * @param args
	 */
	public static void main(String[] args) {
		Reader reader = new Reader("lab1_data.txt"); //Creates a new reader with the file we want to read.
		System.out.println("The sum of the numbers is " + reader.addList()); //Prints the sum of the list for testing purposes.
	}
}
=======
import java.io.*;
/**
 * CS 361: Lab 1
 * This is the Reader class. It just reads the file for the numbers.
 * It also gives me access to the array with a getList method.
 * @author Tiffany Jansen
 * @version 1.0
 */
public class Reader {
	//Field for the class. (Literally just the list of the numbers in an array.)
	private int[] list;
	/**
	 * Constructor for the class.
	 * @param filename; The file we wish to read.
	 */
	public Reader(String filename){
		list = new int[10000000];
		readFile(filename);
	}
	/**
	 * Reads the file.
	 * @param filename; The filename of the file we are trying to read.
	 */
	private void readFile(String filename) {
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String num = reader.readLine();//I found all of this in my CS 161/162 textbook.
			int i = 0;
			while(num != null){
				list[i] = Integer.parseInt(num); //Takes the line read, converts it to an int and then adds it to my list.
				//https://stackoverflow.com/questions/5585779/how-do-i-convert-a-string-to-an-int-in-java
				//This is what I used to convert my list of Strings to integers.
				//The parseInt method just converts the String to an int. Basically just removes the quotes.
				num = reader.readLine(); //Reads next line.
				i++;
			}
			reader.close(); //Closes the reader.
		}
		catch(FileNotFoundException e) {
			System.err.println("Unable to open " + filename); //Just error handling.
		}
		catch(IOException e) {
			System.err.println("A problem was encountered reading " + filename); //Just error handling.
		}
	}
	/**
	 * Adds all of the numbers in the list.
	 * @return total; The sum of the entire list.
	 */
	public long addList() {
		long total = 0;
		for(int i = 0; i < list.length; i++) {
			total = total + list[i]; //Adds everything in the list together.
		}
		return total; //Returns the sum.
	}
	/**
	 * Gets the list of numbers and returns it.
	 * @return list; The list of the numbers that are in our file.
	 */
	public int[] getList(){
		return list; //Returns the list.
	}
	/**
	 * Main method for checking things out and running everything.
	 * @param args
	 */
	public static void main(String[] args) {
		Reader reader = new Reader("lab1_data.txt"); //Creates a new reader with the file we want to read.
		System.out.println("The sum of the numbers is " + reader.addList()); //Prints the sum of the list for testing purposes.
	}
}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
