
/**
 * Transporter room to transport the player to a random room.
 * (Hopefully...)(It works!!)
 * 
 * @author Tiffany Jansen 
 * @version 04.17.2017
 */
public class TransporterRoom extends Room
{
    private VirtualMap map;
    
    /**
     * Constructor for objects of class TransporterRoom
     * @param des Description of the transporter room.
     */
    public TransporterRoom(String des)
    {
        super(des);
        map = new VirtualMap();
    }
    
    /**
     * Return a random room, independent of the direction
     * parameter.
     * @param direction Ignored.
     * @return A random room.
     */
    public Room getExit(String direction)
    {
        return map.findRandomRoom();
    }
    
    /**
     * Return a description of the room in the form:
     *      You are in the kitchen.
     *      There are no exits.
     * @return A long description of this room.
     */
    public String getLongDescription()
    {            
        return "You are in " + getShortDescription().toLowerCase()+ ".\n" + "There are no specific exits.";
    }
    
}
