import javax.swing.*;
import java.awt.*;

/**
 * This class holds the token and the number of spaces
 * completed by the player.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Player
{
    private int spacesCompleted;
    private ImageIcon token;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        spacesCompleted = 0;
        token = new ImageIcon("heart.png");
    }
    
    /**
     * Accessor method for the token.
     * @return token; the player's token.
     */
    public ImageIcon getToken()
    {
        return token;
    }
    
    /**
     * Accessor method for the spacesCompleted stuff.
     * @return spacesCompleted; the spaces completed by the player.
     */
    public int getSpaces()
    {
        return spacesCompleted;
    }
    
    /**
     * The method used when the player needs to move forward.
     * @param diceRolled; the value of the dice causing the player to move.
     */
    public void moveForward(int diceRolled)
    {
        spacesCompleted = spacesCompleted + diceRolled;
    }
    
    /**
     * The method used when the player gets a math question wrong.
     * @param back; the number of spaces to move backwards.
     */
    public void moveBackward(int back)
    {
        spacesCompleted = spacesCompleted - back;
    }
    
    /**
     * Resets the spacesCompleted so you can play again.
     */
    public void resetSpaces()
    {
        spacesCompleted = 0;
    }

}
