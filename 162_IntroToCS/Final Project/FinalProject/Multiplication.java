
/**
 * Returns the correct answer for any Multiplication problems, it also
 * returns the questions to be used in the game.
 * Only used when the setting is set to Medium or Hard.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Multiplication extends MathQuestion
{
    
    /**
     *The constructor... It has nothing in it, but that's because the
     *object is not made to be created and used. It is meant to allow
     *    for Inheritance and make the coding a little easier.
     */
    public Multiplication()
    {
    }
    
    /**
     * Asks the multiplication question.
     * @param a; the first integer to be multiplied.
     * @param b; the second integer to be multiplied.
     * @return question; the question to be asked.
     */
    public String askQuestion(int a, int b)
    {
        return ("What is " + a + " * " + b + "?");
    }

     /**
     * Solves the multiplication problem. Returns the correct answer.
     * @param a. The first number to be used.
     * @param b. The second number to be used.
     * @return a*b. The two numbers multiplied together.
     */
    public int solve(int a, int b)
    {
        return a*b;
    }
}
