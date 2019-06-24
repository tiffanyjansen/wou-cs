import java.awt.*;
import javax.swing.*;

/**
 * This is the class that holds all of the spaces and keeps track
 * of how many there are. They are all numbered so when a player
 * is on the space they will know how many more they have. The goal is 
 * to have the image of the space be a certain color with a number
 * attatched to it.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Space
{
    private JLabel space;
    private ImageIcon icon;
    
    /**
     * Constructor for the Spaces. It adds one to the SPACES field
     * so we can keep track of that, it also assigns a number to each space
     *      and creates the space to be used on the game board.
     */
    public Space()
    {      
        icon = new ImageIcon("space.png");
        space = new JLabel(icon);
    }
    
    /**
     * Accessor method for the space.
     * @return JLabel; the JLabel of the space that was created.
     */
    public JLabel getSpace()
    {
        return space;
    }
    
    /**
     * Changes the image of the space.
     * @param token; the new ImageIcon of the space.
     */
    public void setSpace(ImageIcon token)
    {
        icon = token;
        space.setIcon(icon);
    }
}
