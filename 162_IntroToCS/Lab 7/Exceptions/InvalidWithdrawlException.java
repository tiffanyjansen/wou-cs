
/**
 * Exception class needed for the lab, I'm assuming it lets you know when you 
 * try to withdrawl more money than the account has in it.
 * 
 * @author Tiffany Jansen 
 * @version 05.15.2017
 */
public class InvalidWithdrawlException extends Exception
{
    private String key;
    /**
     * Store the details in error.
     * @param newKey The key that explains the error
     */
    public InvalidWithdrawlException(String newKey)
    {
        key = newKey;
    }

    /**
     * @return The key of error
     */
    public String getKey()
    {
        return key;
    }
    
    /**
     * @return A diagnostic string containing the key in error.
     */
    public String toString()
    {
        return key;
    }
}
