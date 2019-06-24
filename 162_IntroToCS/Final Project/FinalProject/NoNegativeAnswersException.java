
/**
 * This is the excpetion meant for the subtraction stuff so the 
 * user doesn't have to input a negative number. I don't really 
 * want the user to need to input negative numbers. 
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
*/
public class NoNegativeAnswersException extends Exception
{
    private String key;
    
    /**
     * Constructor for objects of class NoNegativeAnswersException
     * @param newKey; the key for the excpetion
     */
    public NoNegativeAnswersException(String newKey)
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
        return "There can be no negative answers in this project. " + key;
    }
}
