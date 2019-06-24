import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 * Map of all the rooms in our game.
 * 
 * @author Tiffany Jansen
 * @version 05.08.2017
 */
public class VirtualMap
{
    private static ArrayList<Room> rooms;
    private static ImageIcon icon = new ImageIcon("General_Map.jpg");

    /**
     * Constructor for objects of class VirtualMap
     */
    public VirtualMap()
    {
       rooms = new ArrayList<Room>();
    }

    /**
     * Add rooms to the map of our game.
     */
    public void addRoom(Room room)
    {
        rooms.add(room);
    }
    
    /**
     * Choose a random room.
     * @return A random room.
     */
    public Room findRandomRoom()
    {
        Random ran =  new Random();
        int i = ran.nextInt(rooms.size());
        Room room = rooms.get(i);
        return room;
    }
    
    /**
     * Returns the general map of the place so people can view it 
     * without it telling them EXACTLY where they are.
     * @return ImageIcon. General Map.
     */
    public static ImageIcon getIcon()
    {
        return icon;
    }
    
}
