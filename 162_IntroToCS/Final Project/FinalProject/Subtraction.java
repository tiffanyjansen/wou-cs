
/**
 * Returns the correct answer for any Subtraction problems, it also
 * returns the question that is being asked of it.
 * Used in any setting.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Subtraction extends MathQuestion
{
  
    /**
     *The constructor... It has nothing in it, but that's because the
     *object is not made to be created and used. It is meant to allow
     *    for Inheritance and make the coding a little easier.
     */
    public Subtraction()
    {
        
    }
    
    /**
     * Asks the subraction question.
     * @param a; the first integer to be subracted.
     * @param b; the second integer to be subtracted.
     * @throws NoNegativeAnswersException when a < b.
     * @return question; the question to be asked.
     */
    public String askQuestion(int a, int b) throws NoNegativeAnswersException
    {
        if(b > a){
            throw new NoNegativeAnswersException(a + "-" + b + " will give" +
                                            "a negative number.");
        }
        return ("What is " + a + " - " + b + "?");
    }

     /**
     * Solves the subraction problem. Returns the correct answer.
     * @param a. The first number to be used.
     * @param b. The second number to be used.
     * @return a-b. The second number subracted from the first.
     */
    public int solve(int a, int b)
    {
        return a-b;
    }
}
