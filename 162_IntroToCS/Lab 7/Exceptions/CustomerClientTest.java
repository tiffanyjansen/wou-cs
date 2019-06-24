

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CustomerClientTest.
 *
 * @author  Tiffany Jansen
 * @version 05.15.2017
 */
public class CustomerClientTest
{
    private AccountServer accountS1;
    private CustomerClient customer1;

    /**
     * Default constructor for test class CustomerClientTest
     */
    public CustomerClientTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        accountS1 = new AccountServer();
        customer1 = new CustomerClient(accountS1);
        customer1.customerDeposit(1000);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testDeposit()
    {
        customer1.customerDeposit(150);
        customer1.customerDeposit(-25);
    }

    @Test
    public void testWithdrawl()
    {
        customer1.customerWithdrawl(200);
        customer1.customerWithdrawl(-25);
        customer1.customerWithdrawl(8000);
    }
}


