import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * All the GUI code of Western Oregon University will be put into this class (Eventually)...
 * 
 * @author Tiffany Jansen
 * @version 05.08.2017
 */
public class GameGUI
{
    //Static fileChooser to choose the files
    private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));

    //GUI Objects.
    private JFrame frame;
    private JLabel imageLabel;
    private JTextArea textDisplay; 
    private JPanel imagePanel;
    private Container contentPane;

    private Game game;

    /**
     * GameGUI Constructor. Makes objects of the GUI of the game.
     */
    public GameGUI(Game g)
    {
        game = g;
        ImageIcon icon = g.getCurrentRoom().getIcon();
        createGUI(icon);       
    }

    /**
     * Creates the GUI. Puts the image of the current room into the 
     * image panel so that the pictures are consistent with the 
     * game.
     * @param ImageIcon. Image of the Current Room.
     */
    private void createGUI(ImageIcon icon)
    {
        frame = new JFrame("Western Oregon University");

        createMenu(frame);
        //Get the top level panel from the JFrame  
        contentPane = frame.getContentPane();
        //Set the layout style for the panel
        contentPane.setLayout(new BorderLayout());

        //Puts the Image of the outside of WOU into the GUI.
        imagePanel = new JPanel(new BorderLayout());       
        imageLabel = new JLabel(icon);
        createButtons();
        contentPane.add(imagePanel, BorderLayout.NORTH);

        //creates the TextDisplay.
        textDisplay = new JTextArea(15, 40);
        textDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //Make GUI visible       
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates Menu.
     * @param JFrame. The frame from the createGUI().
     */
    private void createMenu(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //create File Menu with Save, Quit, and Open menuItems.
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem playMenuItem = new JMenuItem("Play");
        fileMenu.add(playMenuItem);

        JMenuItem quitMenuItem = new JMenuItem("Quit");
        fileMenu.add(quitMenuItem);        

        quitMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) 
                {
                    quit(); 
                }
            });
        playMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    playGame();
                }
            });

        //create Help Menu with Help, About, and ViewMap menuItem.
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu); 

        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenu.add(helpMenuItem);

        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        JMenuItem mapMenuItem = new JMenuItem("View Map");
        helpMenu.add(mapMenuItem);

        JMenuItem viewMenuItem = new JMenuItem("View Location");
        helpMenu.add(viewMenuItem);

        helpMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    help();
                }
            });
        aboutMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    about();
                }
            });      
        viewMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    viewLocation();
                }
            });
        mapMenuItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    viewMap();
                }
            }); 
    }

    /**
     * Create the buttons for the movement of our character.
     */
    public void createButtons()
    {              
        JButton eastButton = new JButton("Go East");        
        eastButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    goEast();
                }
            });

        JButton northButton = new JButton("Go North");          
        northButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    goNorth();
                }
            });                

        JButton westButton = new JButton("Go West");         
        westButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    goWest();
                }
            });

        JButton southButton = new JButton("Go South");      
        southButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    goSouth();
                }
            });

        imagePanel.add(eastButton, BorderLayout.EAST);        
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(westButton, BorderLayout.WEST);
        imagePanel.add(northButton, BorderLayout.NORTH);
        imagePanel.add(southButton, BorderLayout.SOUTH);
    }

    /**
     * Prints out the string in a line.
     */
    public void println(String s)
    {
        textDisplay.append(s + "\n");
        textDisplay.setCaretPosition(textDisplay.getText().length());
    }

    /**
     * Prints out the string.
     */
    public void print(String s)
    {
        textDisplay.append(s);
        textDisplay.setCaretPosition(textDisplay.getText().length());
    }

    //Action Methods
    /**
     * Quits the program.
     */
    private void quit()
    {
        JOptionPane.showMessageDialog(frame, "Bye! See you next time!", "WOU", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    /**
     * Shows a Dialog box with importatnt information about where the player
     * is located.
     */
    private void about()
    {
        String s = game.getCurrentRoom().getLongDescription();
        JOptionPane.showMessageDialog(frame, s, "WOU", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clears the textDisplay and starts playing the game.
     */
    private void playGame()
    {
        textDisplay.setText(null);
        game.play();
    }

    /**
     * Shows the map with the player's location highlighted.
     */
    private void viewLocation()
    {
        JFrame f = new JFrame();
        f.getContentPane().add(new JLabel(game.getCurrentRoom().getMap()));
        f.setVisible(true);
        f.pack();
    } 

    /**
     * Shows the map.
     */
    private void viewMap()
    {
        JFrame f = new JFrame();
        f.getContentPane().add(new JLabel(VirtualMap.getIcon()));
        f.setVisible(true);
        f.pack();
    }

    /**
     * Shows important information that may be needed if the player needs
     * help.
     */
    private void help()
    {
        String helpString = "You are lost.\n You wander around the University.\n";
        helpString += "Use the buttons around the picture to exit.\n";
        helpString += game.getCurrentRoom().getExitString() + ".\n";
        helpString += "You can use the menu bar to save, quit, or open a previous game.\n";
        helpString += "Good luck!!\n";       
        JOptionPane.showMessageDialog(frame, helpString, "WOU", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Allows the playerr to go East.
     */
    private void goEast()
    {
        Command command = new Command("go", "east");
        game.goRoom(command); 
        changeImage();
    }

    /**
     * Allows the playerr to go West.
     */
    private void goWest()
    {
        Command command = new Command("go", "west");
        game.goRoom(command);  
        changeImage();
    }

    /**
     * Allows the playerr to go North.
     */
    private void goNorth()
    {
        Command command = new Command("go", "north");
        game.goRoom(command); 
        changeImage();
    }

    /**
     * Allows the playerr to go South.
     */
    private void goSouth()
    {
        Command command = new Command("go", "south");        
        game.goRoom(command);  
        changeImage();
    }

    /**
     * Changes the image of the image panel to be consistent with 
     * the Room the player is currently in.
     */
    private void changeImage()
    {
        imageLabel.setIcon(game.getCurrentRoom().getIcon());
        frame.pack();
    }
}