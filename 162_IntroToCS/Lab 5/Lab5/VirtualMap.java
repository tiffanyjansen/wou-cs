import java.util.ArrayList;
import java.util.Random;
/**
 * Map of all the rooms in our game.
 * 
 * @author Tiffany Jansen
 * @version 04.17.2017
 */
public class VirtualMap
{
    private static ArrayList<Room> rooms;

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
    
}
