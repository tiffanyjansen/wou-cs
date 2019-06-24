

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class VirtualMapTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class VirtualMapTest
{
    private VirtualMap virtualM1;
    private Room Kitchen;
    private TransporterRoom transport;

    /**
     * Default constructor for test class VirtualMapTest
     */
    public VirtualMapTest()
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
        virtualM1 = new VirtualMap();
        Kitchen = new Room("the kitchen");
        transport = new TransporterRoom("the transporter room");
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
    public void testAddRoom()
    {
        virtualM1.addRoom(Kitchen);
        virtualM1.addRoom(transport);
    }
}

