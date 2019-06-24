/**
 * This is the excpetion meant for the division stuff so the
 * answer is not "does not exist". 
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
*/
public class NoDividingZeroException extends Exception
{
    private String key;
    
    /**
     * Constructor for objects of class NoNegativeAnswersException
     * @param newKey; the key for the excpetion
     */
    public NoDividingZeroException(String newKey)
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
        return "You cannot divide by 0. " + key;
    }
}
