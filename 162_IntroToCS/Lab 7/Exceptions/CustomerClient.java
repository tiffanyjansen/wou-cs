
/**
 * The customer that wishes to use their account.
 * 
 * @author Tiffany Jansen
 * @version 05.15.2017
 */
public class CustomerClient
{
    private AccountServer account;

    /**
     * Constructor for objects of class CustomerClient
     */
    public CustomerClient(AccountServer a)
    {
        account = a;
    }

    /**
     * Allows the customer to deposit money into the account.
     * @param deposit The amount they would like to deposit.
     */
    public void customerDeposit(int deposit)
    {
        try{
            account.deposit(deposit);
        }
        catch(Exception e){
            System.err.println(e);
        }
        printDetails();
    }    

    /**
     * Allows the customer to withdrawl money into the account.
     * @param withdrawl The amount they would like to withdrawl.
     */
    public void customerWithdrawl(int withdrawl)
    {
        try{
            account.withdraw(withdrawl);
        }
        catch(InvalidWithdrawlException e){
            System.out.println(e);
        }
        catch(Exception e){
            System.err.println(e);
        }
        printDetails();
    }

    /**
     * Print statement.
     */
    private void printDetails()
    {
        System.out.println("You now have " + account.getAmount() + " in your account.");
        System.out.println();
    }
}
