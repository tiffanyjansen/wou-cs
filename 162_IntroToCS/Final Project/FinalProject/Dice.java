import java.util.Random;
/**
 * The dice are used in the game to decide how far each player moves for each turn.
 * All it does is generate a random integer between 1 and 12.
 * 
 * @author Tiffany Jansen
 * @version 06.02.2017
 */
public class Dice
{
    
    /**
     * The dice for the game are created here.
     */
    public Dice()
    {
    }
    
    /**
     * Returns a random number simulating the rolling of dice.
     * @return the number that was "rolled" by the dice.
     */
    public int rollDice()
    {
        Random random = new Random();
        int num = 1 + random.nextInt(12);
        return num;
    }
}
