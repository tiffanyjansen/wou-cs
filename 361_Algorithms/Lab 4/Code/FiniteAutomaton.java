<<<<<<< HEAD
/**
 * The Finite Automaton class for Lab 4 of CS 361
 * @author Tiffany Jansen
 * @version 5.21.2018
 *
 */
public class FiniteAutomaton {
	
	/**
	 * This is the "fast way" of coding this Machine. I did it
	 * the other way too so we can make sure that this works, 
	 * which it does.
	 * @param x; the string we wish to match.
	 * @return true; if the string is accepted by the machine.
	 */
	public boolean dFA(String x) {
		char[] s = x.toCharArray(); //converts the string into a list of characters.
		if(s[0] == ' ') { //if the string is the empty string return false.
			return false;
		}
		if(s[0] == s[s.length - 1]) { //if the first and last character are the same return true.
			return true;
			//I know this is true because we did this example in class as a warm up. The language
			//of this "machine" is B = {x|x starts with and ends with the same character}.
		}
		else { //otherwise return false.
			return false;
		}
	}
	/**
	 * The "long way" of doing the DFA. Actually going from
	 * state to state.
	 * @param x; the string we are trying to match.
	 * @return true; if the string is accepted by the machine.
	 */
	public boolean longDFA(String x) {
		char[] y = x.toCharArray(); //convert the string into a list of characters.
		if(y[0] == 'a') {
			return dFAQ1(y, 1); //if the first character is 'a' go to Q1
		}
		else if(y[0] == 'b') {
			return dFAR1(y, 1); //if the first character is 'b' go to R1
		}
		else {
			return false; //if the first character is neither, return false.
		}
	}
	/**
	 * This is the Q1 state (It's an accept state of our machine)	
	 * @param q; the string in a list of characters.
	 * @param start; the index of the next character to check.
	 * @return true; if we end on this state.
	 */
	private boolean dFAQ1(char[] q, int start) {
		if(start == q.length) {
			return true; //if we have reached the end of our string return true.
		}
		else if(q[start] == 'a') {
			return dFAQ1(q, start + 1); //if the next char is 'a' go to Q1
		}
		else {
			return dFAQ2(q, start + 1); //if the next char is 'b' go to Q2
		}
		
	}
	/**
	 * This is the Q2 state
	 * @param q; the string as a list of characters.
	 * @param start; the next index to check.
	 * @return false; if we end on this state.
	 */
	private boolean dFAQ2(char[] q, int start) {
		if(start == q.length) {
			return false; //if we have reached the end of our string return false.
		}
		else if(q[start] == 'a') {
			return dFAQ1(q, start + 1); //if the next char is 'a' go to Q1
		}
		else {
			return dFAQ2(q, start + 1); //if the next char is 'b' go to Q2
		}
	}
	/**
	 * This is the R1 state (It's an accept state of our machine)	
	 * @param r; the string in a list of characters.
	 * @param start; the index of the next character to check.
	 * @return true; if we end on this state.
	 */
	private boolean dFAR1(char[] r, int start) {
		if(start == r.length) {
			return true; //if we have reached the end of our string return true.
		}
		else if(r[start] == 'a') {
			return dFAR2(r, start + 1); //if the next char is 'a' go to R2
		}
		else {
			return dFAR1(r, start + 1); //if the next char is 'b' go to R1
		}
	}
	/**
	 * This is the R2 state
	 * @param r; the string as a list of characters.
	 * @param start; the next index to check.
	 * @return false; if we end on this state.
	 */
	private boolean dFAR2(char[] r, int start) {
		if(start == r.length) {
			return false; //if we have reached the end of our string return false.
		}
		else if(r[start] == 'a') {
			return dFAR2(r, start + 1); //if the next char is 'a' go to R2
		}
		else {
			return dFAR1(r, start + 1); //if the next char is 'b' go to R1
		}
	}
	
	public static void main(String args[]) {
		FiniteAutomaton fa = new FiniteAutomaton();
		String x = "ababa";
		System.out.print("1. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = "baba";
		System.out.print("2. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = "aababaab";
		System.out.print("3. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = "babaabaaabb";
		System.out.print("4. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = " ";
		System.out.print("5. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
	}
}
=======
/**
 * The Finite Automaton class for Lab 4 of CS 361
 * @author Tiffany Jansen
 * @version 5.21.2018
 *
 */
public class FiniteAutomaton {
	
	/**
	 * This is the "fast way" of coding this Machine. I did it
	 * the other way too so we can make sure that this works, 
	 * which it does.
	 * @param x; the string we wish to match.
	 * @return true; if the string is accepted by the machine.
	 */
	public boolean dFA(String x) {
		char[] s = x.toCharArray(); //converts the string into a list of characters.
		if(s[0] == ' ') { //if the string is the empty string return false.
			return false;
		}
		if(s[0] == s[s.length - 1]) { //if the first and last character are the same return true.
			return true;
			//I know this is true because we did this example in class as a warm up. The language
			//of this "machine" is B = {x|x starts with and ends with the same character}.
		}
		else { //otherwise return false.
			return false;
		}
	}
	/**
	 * The "long way" of doing the DFA. Actually going from
	 * state to state.
	 * @param x; the string we are trying to match.
	 * @return true; if the string is accepted by the machine.
	 */
	public boolean longDFA(String x) {
		char[] y = x.toCharArray(); //convert the string into a list of characters.
		if(y[0] == 'a') {
			return dFAQ1(y, 1); //if the first character is 'a' go to Q1
		}
		else if(y[0] == 'b') {
			return dFAR1(y, 1); //if the first character is 'b' go to R1
		}
		else {
			return false; //if the first character is neither, return false.
		}
	}
	/**
	 * This is the Q1 state (It's an accept state of our machine)	
	 * @param q; the string in a list of characters.
	 * @param start; the index of the next character to check.
	 * @return true; if we end on this state.
	 */
	private boolean dFAQ1(char[] q, int start) {
		if(start == q.length) {
			return true; //if we have reached the end of our string return true.
		}
		else if(q[start] == 'a') {
			return dFAQ1(q, start + 1); //if the next char is 'a' go to Q1
		}
		else {
			return dFAQ2(q, start + 1); //if the next char is 'b' go to Q2
		}
		
	}
	/**
	 * This is the Q2 state
	 * @param q; the string as a list of characters.
	 * @param start; the next index to check.
	 * @return false; if we end on this state.
	 */
	private boolean dFAQ2(char[] q, int start) {
		if(start == q.length) {
			return false; //if we have reached the end of our string return false.
		}
		else if(q[start] == 'a') {
			return dFAQ1(q, start + 1); //if the next char is 'a' go to Q1
		}
		else {
			return dFAQ2(q, start + 1); //if the next char is 'b' go to Q2
		}
	}
	/**
	 * This is the R1 state (It's an accept state of our machine)	
	 * @param r; the string in a list of characters.
	 * @param start; the index of the next character to check.
	 * @return true; if we end on this state.
	 */
	private boolean dFAR1(char[] r, int start) {
		if(start == r.length) {
			return true; //if we have reached the end of our string return true.
		}
		else if(r[start] == 'a') {
			return dFAR2(r, start + 1); //if the next char is 'a' go to R2
		}
		else {
			return dFAR1(r, start + 1); //if the next char is 'b' go to R1
		}
	}
	/**
	 * This is the R2 state
	 * @param r; the string as a list of characters.
	 * @param start; the next index to check.
	 * @return false; if we end on this state.
	 */
	private boolean dFAR2(char[] r, int start) {
		if(start == r.length) {
			return false; //if we have reached the end of our string return false.
		}
		else if(r[start] == 'a') {
			return dFAR2(r, start + 1); //if the next char is 'a' go to R2
		}
		else {
			return dFAR1(r, start + 1); //if the next char is 'b' go to R1
		}
	}
	
	public static void main(String args[]) {
		FiniteAutomaton fa = new FiniteAutomaton();
		String x = "ababa";
		System.out.print("1. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = "baba";
		System.out.print("2. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = "aababaab";
		System.out.print("3. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = "babaabaaabb";
		System.out.print("4. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
		x = " ";
		System.out.print("5. ");
		System.out.println("The string '" + x + "' returned " + fa.dFA(x));
		System.out.println("Long Way: The string '" + x + "' returned " + fa.longDFA(x));
	}
}
>>>>>>> b043ed8e66bd34faa2d31c148b2c53e52360b373
