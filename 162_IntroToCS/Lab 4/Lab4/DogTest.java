

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DogTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DogTest
{
    private Dog Spot;

    /**
     * Default constructor for test class DogTest
     */
    public DogTest()
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
        Spot = new Dog("Spot", "black", "Stacia");
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
    public void testIsAlive()
    {
        Spot.printAge(15);
        assertEquals(false, Spot.isAlive());
    }

    @Test
    public void testOwner()
    {
        assertEquals("Stacia", Spot.getOwner());
    }
}



