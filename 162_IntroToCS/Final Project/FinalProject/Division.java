
/**
 * Returns the correct answer for any Division problems, it also
 * returns the necessary questions that are asked of it. 
 * Only used when the setting is set to Hard.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Division extends MathQuestion
{

    /**
     *The constructor... It has nothing in it, but that's because the
     *object is not made to be created and used. It is meant to allow
     *    for Inheritance and make the coding a little easier.
     */
    public Division()
    {
    }

    /**
     * Asks the division question.
     * @param a; the first integer to be divided.
     * @param b; the second integer to be divided.
     * @throws NoDividingZeroException; when the second integer is 0.
     * @throws NoDecimalAnswersException; when the number is not divided evenly.
     * @return question; the question to be asked.
     */
    public String askQuestion(int a, int b) throws NoDividingZeroException,
    NoDecimalAnswersException
    {
        if(b == 0)
        {
            throw new NoDividingZeroException(b + " is zero and you cannot divide" + 
                "by zero.");
        }
        else if(a%b != 0){
            throw new NoDecimalAnswersException(a + " is not divisible by " + b + "," + 
                "this will result in a decimal anser.");
        }
        return ("What is " + a + " / " + b + "?");
    }

    /**
     * Solves the division problem. Returns the correct answer.
     * @param a. The first number to be used.
     * @param b. The second number to be used.
     * @return a/b; a divided by b.
     */
    public int solve(int a, int b)
    {
        return a/b;
    }
}
