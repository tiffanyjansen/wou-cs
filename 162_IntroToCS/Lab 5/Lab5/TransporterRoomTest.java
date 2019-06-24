

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TransporterRoomTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class TransporterRoomTest
{
    private TransporterRoom Transport;

    /**
     * Default constructor for test class TransporterRoomTest
     */
    public TransporterRoomTest()
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
        Transport = new TransporterRoom("The transporter room");
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
    public void testGetLongDescription()
    {
        assertEquals("You are in the transporter room.\nThere are no specific exits.", Transport.getLongDescription());
    }
}

