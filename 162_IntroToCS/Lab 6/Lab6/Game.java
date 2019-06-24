import javax.swing.*;
import java.util.List;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Tiffany Jansen
 * @version 05.08.2017
 */

public class Game 
{
    private Room currentRoom;
    private VirtualMap map;
    private GameGUI gui;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {        
        map = new VirtualMap();
        createRooms();
        gui = new GameGUI(this);
    }

    /**
     * Create all the rooms, link their exits together, and creates the 
     * map for.
     */
    private void createRooms()
    {
        Room outside, naturalScience, bellamy, academicSupport, administration, cambell, library;
        Room libraryTransport, dorms, computing, maske, math, newEdTransport, newEd, todd;
        Room westHouse, wuc, wucInside, wucArt, wucPool, oMA;

        //Description Strings that are too long:
        String b = "outside Bellamy Hall, also known as the Humanities and Social Sciences building";
        
        // create the rooms (16)
        outside = new Room("outside the main entrance of Western Oregon University", new ImageIcon("OutsideWOU.jpg"), new ImageIcon("Outside_Map.jpg"));
        naturalScience = new Room("outside the Natural Science building at WOU", new ImageIcon("Natural_Sciences.jpg"), new ImageIcon("Natural_Sciences_Map.jpg"));        
        bellamy = new Room(b, new ImageIcon("Bellamy_Hall.jpg"), new ImageIcon("Bellamy_Hall_Map.jpg"));
        academicSupport = new Room("outside the Academic Support building at WOU", new ImageIcon("Academic_Support.jpg"), new ImageIcon("Academic_Support_Map.jpg"));
        administration = new Room("outside of the Administration building at WOU", new ImageIcon("Administration.jpg"),  new ImageIcon("Administration_Map.jpg"));
        cambell = new Room("outside of Cambell Hall, where all the art classes are at W", new ImageIcon("Cambell_Hall.jpg"), new ImageIcon("Cambell_Hall_Map.jpg"));
        library = new Room("outside of Hamserly Library", new ImageIcon("Hamserly_Library.jpg"), new ImageIcon("Library_Map.jpg"));
        dorms = new Room("outside of Heritage Hall, where some of the dorms are located at WOU", new ImageIcon("Heritage_Hall.jpg"),  new ImageIcon("Heritage_Hall_Map.jpg"));
        computing = new Room("outside of the Instructional Technology Center, where the Computer Science classes are at WOU", new ImageIcon("ITC.jpg"), new ImageIcon("ITC_Map.jpg"));
        maske = new Room("outside of Maske Hall", new ImageIcon("Maske_Hall.jpg"), new ImageIcon("Maske_Hall_Map.jpg"));
        math = new Room("outside of the Math and Nursing Building", new ImageIcon("Math_Nursing.jpg"), new ImageIcon("Math_Map.jpg"));
        newEd = new Room("outside of the Richard Woodcock Education Center", new ImageIcon("New_Education.jpg"), new ImageIcon("New_Ed_Map.jpg"));
        todd = new Room("outside of Todd Hall", new ImageIcon("Todd_Hall.jpg"), new ImageIcon("Todd_Hall_Map.jpg"));
        westHouse = new Room("outside of the West House", new ImageIcon("West_House.jpg"), new ImageIcon("West_House_Map.jpg"));
        wuc = new Room("outside of the Werner University Center, this is where a lot of students go to get coffee", new ImageIcon("WUC.jpg"), new ImageIcon("WUC_Map.jpg"));
        wucInside = new Room("inside of the Werner University Center", new ImageIcon("WUC_Inside.jpg"), new ImageIcon("WUC_Inside_Map.jpg"));
        oMA = new Room("outside of the Oregon Military Academy", new ImageIcon("OMA.jpg"), new ImageIcon("OMA_Map.jpg"));
        
        //create the Transporter Rooms (4)
        libraryTransport = new TransporterRoom("inside of Hamserly Library", new ImageIcon("Hamserly_Library_Inside.jpg"), new ImageIcon("Library_Inside_Map.jpg"));
        newEdTransport = new TransporterRoom("inside of the education center", new ImageIcon("New_Ed_Inside.jpg"), new ImageIcon("New_Ed_Inside_Map.jpg"));
        wucArt = new TransporterRoom("where people like to show off their artwork", new ImageIcon("WUC_Art.jpg"), new ImageIcon("WUC_Art_Map.jpg"));
        wucPool = new TransporterRoom("where people like to go relax and chill", new ImageIcon("WUC_Pool.jpg"), new ImageIcon("WUC_Pool_Map.jpg"));

        // initialise room exits
        outside.setExit("east", naturalScience);
        outside.setExit("west", westHouse);
        
        westHouse.setExit("east", outside);
        
        naturalScience.setExit("west", outside);
        naturalScience.setExit("south", academicSupport);
        naturalScience.setExit("east", bellamy);
        
        academicSupport.setExit("north", naturalScience);
        academicSupport.setExit("east", administration);
        
        bellamy.setExit("west", naturalScience);
        bellamy.setExit("south", administration);
        bellamy.setExit("east", cambell);
        
        administration.setExit("west", academicSupport);
        administration.setExit("north", bellamy);
        administration.setExit("east", computing);
        
        cambell.setExit("west", bellamy);        
        cambell.setExit("east", todd);
        
        computing.setExit("west", administration);
        computing.setExit("east", library);
        computing.setExit("north", todd);
        
        todd.setExit("west", cambell);
        todd.setExit("east", wuc);  
        todd.setExit("south", computing);
        
        library.setExit("west", computing);
        library.setExit("south", maske);
        library.setExit("north", libraryTransport);
        library.setExit("east", newEd);
        
        wuc.setExit("west", todd);
        wuc.setExit("north", wucInside);
        wuc.setExit("east", maske);
        
        maske.setExit("west", wuc);
        maske.setExit("south", library);
        maske.setExit("east", math);
        
        wucInside.setExit("west", wucPool);
        wucInside.setExit("east", wucArt);
        wucInside.setExit("south", wuc);
        
        math.setExit("west", maske);
        math.setExit("east", dorms);
        math.setExit("south", newEd);
        
        dorms.setExit("west", math);
        dorms.setExit("south", oMA);
        
        newEd.setExit("north", math);
        newEd.setExit("south", newEdTransport);
        newEd.setExit("west", library);
        newEd.setExit("east", oMA);
        
        oMA.setExit("west", newEd);
        oMA.setExit("north", dorms);               

        //add rooms to map
        map.addRoom(outside);
        map.addRoom(naturalScience);
        map.addRoom(westHouse);
        map.addRoom(academicSupport);
        map.addRoom(bellamy);
        map.addRoom(administration);
        map.addRoom(cambell);
        map.addRoom(computing);
        map.addRoom(todd);
        map.addRoom(library);
        map.addRoom(wuc);
        map.addRoom(wucInside);
        map.addRoom(maske);
        map.addRoom(math);
        map.addRoom(newEd);
        map.addRoom(dorms);
        map.addRoom(oMA);
        
        map.addRoom(newEdTransport);
        map.addRoom(wucArt);
        map.addRoom(wucPool);
        map.addRoom(libraryTransport);
        
        currentRoom = outside;         // start game outside        
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();        
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gui.println("Welcome to the Western Oregon University!");
        gui.println("We hope you enjoy your visit.");
        gui.println("");
        gui.println(currentRoom.getLongDescription());
        gui.println("");
    }
    // implementations of user commands:

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * @param command.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            gui.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        //Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            gui.println("There is no door!");
            gui.println("");

        }
        else {
            currentRoom = nextRoom;
            gui.println(currentRoom.getLongDescription());
            gui.println("");
        }
    }

    /**
     * Get the current room so we can make a dialog box with the info in the GUI.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * MAIN method that is required for the lab... and 
     * everything else we do now... 
     */
    public static void main(String[] args)
    {
        Game g = new Game();
    }

}
