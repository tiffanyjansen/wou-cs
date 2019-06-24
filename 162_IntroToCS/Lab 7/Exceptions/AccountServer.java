
/**
 * The account. It holds all the money and checks
 * to make sure no one overdraws on their account
 * and so they don't try to deposit or withdraw
 * a negative amount. 
 * @author Tiffany Jansen 
 * @version 05.15.2017
 */
public class AccountServer
{
    private int money;   

    /**
     * Constructor for objects of class AccountServer
     */
    public AccountServer()
    {
        money = 0;
    }
    
    /**
     * Deposit money into the account
     * @param deposit Money to be deposited
     * @throws IllegalArgumentException
     */
    public void deposit(int deposit)
    {
        if(deposit < 0){
            throw new IllegalArgumentException("You cannot deposit " + deposit + ".");
        }
        money += deposit;
    }
    
    /**
     * Withdraw money from the account
     * @param withdraw Money to be withdrawn
     * @throws IllegalArgumentException
     * @throws InvalidWithdrawlException
     */
    public void withdraw(int withdraw) throws InvalidWithdrawlException
    {
        if(money < withdraw){
            String s = "You cannot withdraw " + withdraw + ". You do not have enough money in the account.";
            throw new InvalidWithdrawlException(s);
        }
        if(withdraw < 0){
            throw new IllegalArgumentException("You cannot withdraw " + withdraw + ".");
        }
        money -= withdraw;
    }
    
    /**
     * @return amount in account
     */
    public int getAmount()
    {
        return money;
    }
}
