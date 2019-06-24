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
 * @version 04.29.2017
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private VirtualMap map;
    private GameGUI gui;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {        
        gui = new GameGUI();
        map = new VirtualMap();
        createRooms();
        parser = new Parser(gui);
    }
    

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, transporter, livingRoom, cafeteria;
              
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        transporter = new TransporterRoom("The transporter room");
        livingRoom = new TransporterRoom("The living room, where all the people hang out");
        cafeteria = new TransporterRoom("The cafeteria, where people go to eat");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);
        theater.setExit("north", transporter);

        pub.setExit("east", outside);
        pub.setExit("south", livingRoom);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("north", cafeteria);
        
        //add rooms to map
        map.addRoom(outside);
        map.addRoom(theater);
        map.addRoom(pub);
        map.addRoom(lab);
        map.addRoom(office);
        map.addRoom(transporter);
        map.addRoom(livingRoom);
        map.addRoom(cafeteria);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        gui.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        gui.println("Welcome to the World of Zuul!");
        gui.println("World of Zuul is a new, incredibly boring adventure game.");
        gui.println("");
        gui.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

//    Make this into a Dialog Box for when people choose the Help Menu
//
//     /**
//      * Print out some help information.
//      * Here we print some stupid, cryptic message and a list of the 
//      * command words.
//      */
//     private void printHelp() 
//     {
//         gui.println("You are lost. You are alone. You wander");
//         gui.println("around at the university.");
//         gui.println("");
//         gui.println("Your command words are:");
//         parser.showCommands();
//     }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            gui.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            gui.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
        /**
     * MAIN method that is required for the lab... and 
     * everything else we do now... 
     */
    public static void main(String[] args)
    {
        Game g = new Game();
        g.play();
    }

}
