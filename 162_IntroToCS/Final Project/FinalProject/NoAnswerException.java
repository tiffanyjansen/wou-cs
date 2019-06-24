/**
 * This is the exception for when people try to cheat and just 
 * roll the dice without answering the math question.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
*/
public class NoAnswerException extends Exception
{
    private String key;
    
    /**
     * Constructor for objects of class NoNegativeAnswersException
     * @param newKey; the key for the excpetion
     */
    public NoAnswerException(String newKey)
    {
        key = newKey;
    }
    
    /**
     * Accessor method for the key of the exception.
     * @return key; the key for the exception.
     */
    public String getKey()
    {
        return key;
    }
    
    /**
     * The toString method to override the one in the Object class
     * @return the String representation of the exception
     */
    public String toString()
    {
        return "You must input an answer first. " + key;
    }
}
