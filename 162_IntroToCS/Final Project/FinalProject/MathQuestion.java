import java.util.Random;
/**
 * This will be in charge of all the math questions that are
 * asked throughout the game. 
 * 
 * @author Tiffany Jansen
 * @version 05.31.2017
 */
public abstract class MathQuestion
{
    Random ran = new Random();

    /**
     * Objects of this cannot be created. 
     */
    public MathQuestion()
    {
    }
    
    /**
     * Solves the given problem. Returns the correct answer.
     * @param a. The first number to be used.
     * @param b. The second number to be used.
     * @return c. The math done on the two numbers; the correct answer.
     */
    public abstract int solve(int a, int b);
    
    /**
     * Allows the specific operation to ask it's question.
     * @param a; The first number to be used.
     * @param b; The second number to be used.
     * @return question; The question that is being asked.
     */
    public abstract String askQuestion(int a, int b) throws Exception;
    
    /**
     * Chooses a random integer between 0 and 12 to be used for all 
     * the math questions. This allows for all the math questions to 
     *                  be different and random.
     * @return random integer; a random integer between 0 and 12.
     */
    public static int chooseInt()
    {
        Random ran = new Random();
        return ran.nextInt(13);
    }
}

