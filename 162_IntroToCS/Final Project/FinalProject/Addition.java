
/**
 * Returns the correct answer for any Addition problems, it also
 * returns the question to be asked of the game.
 * Used in any setting.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Addition extends MathQuestion
{  

    /**
     *The constructor... It has nothing in it, but that's because the
     *object is not made to be created and used. It is meant to allow
     *    for Inheritance and make the coding a little easier.
     */
    public Addition()
    {        
    }
    
    /**
     * Asks the addition question.
     * @param a; the first integer to be added.
     * @param b; the second integer to be added.
     * @return question; the question to be asked.
     */
    public String askQuestion(int a, int b)
    {
        return ("What is " + a + " + " + b + "?");
    }
    
    /**
     * Solves the addition problem. Returns the correct answer.
     * @param a. The first number to be used.
     * @param b. The second number to be used.
     * @return a+b. The two numbers added together.
     */
    public int solve(int a, int b)
    {
        return a + b;
    }
}
