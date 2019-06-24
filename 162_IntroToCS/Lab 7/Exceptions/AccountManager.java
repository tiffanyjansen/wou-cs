
/**
 * The Manager of the account for the customer.
 * 
 * @author Tiffany Jansen 
 * @version 05.15.2017
 */
public class AccountManager
{
        
    /**
     * Constructor for objects of class AccountManager
     */
    public AccountManager()
    {        
    }
    
    public static void main(String[] args)
    {
        AccountServer account = new AccountServer();
        CustomerClient customer = new CustomerClient(account);
        System.out.println("Welcome to the Bank.");
        System.out.println("You currently have " + account.getAmount() + " dollars.");
        System.out.println();
        System.out.println("*** Start Using The Account ***");
        customer.customerDeposit(10000);
        customer.customerWithdrawl(500);        
        customer.customerDeposit(-29);
        customer.customerWithdrawl(100);        
        customer.customerWithdrawl(-90);
        customer.customerWithdrawl(99999999);        
        customer.customerDeposit(1000);
        customer.customerWithdrawl(-25); 
        customer.customerDeposit(9600);
        System.out.println("Thank you for coming. Your final balance is: " + account.getAmount());
    }
}
