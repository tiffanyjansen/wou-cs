import java.util.Scanner;
import java.awt.*;
import javax.swing.*;
import java.applet.Applet;

/**
 * All the GUI code of Western Oregon University will be put into this class (Eventually)...
 * 
 * @author Tiffany Jansen
 * @version 05.01.2017
 */
public class GameGUI
{
    //Scanner for input stuff.
    private Scanner reader;

    //GUI Objects.
    private JFrame frame;
    private JLabel imageLabel;
    private JTextArea textDisplay;
    private JTextField textInput;

    /**
     * GameGUI Constructor. Makes objects of the GUI of the game.
     */
    public GameGUI()
    {
        reader = new Scanner(System.in);
        createGUI();       
    }

    /**
     * Creates the GUI and the layout and everything else.
     */
    private void createGUI()
    {
        frame = new JFrame("Western Oregon University");

        createMenu(frame);
        //Get the top level panel from the JFrame  
        Container contentPane = frame.getContentPane();
        //Set the layout style for the panel
        contentPane.setLayout(new BorderLayout());

        //Puts the Image of the outside of WOU into the GUI.
        imageLabel = new JLabel(new ImageIcon("OutsideWOU.jpg"));
        contentPane.add(imageLabel, BorderLayout.NORTH);

        //creates the TextDisplay.
        textDisplay = new JTextArea(15, 40);
        textDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        //creates the TextInput AND an inputPanel.
        JPanel inputPanel = new JPanel(new GridLayout());
        createButtons(inputPanel);
        contentPane.add(inputPanel, BorderLayout.SOUTH);

        //Make GUI visible       
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates Menu.
     */
    private void createMenu(JFrame frame)
    {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        //create File Menu with Save and Exit menuItems.
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        //create Help Menu with Help menuItem.
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu); 

        JMenuItem helpMenuItem = new JMenuItem("Help");
        helpMenu.add(helpMenuItem);
    }

    /**
     * Create the buttons for the movement of our character.
     */
    private void createButtons(JPanel panel)
    {
        JButton eastButton = new JButton("Go East");
        panel.add(eastButton);
        JButton northButton = new JButton("Go North");
        panel.add(northButton);
        JButton westButton = new JButton("Go West");
        panel.add(westButton);
        JButton southButton = new JButton("Go South");
        panel.add(southButton);
    }

    /**
     * Get's the line that the people type into our game.
     * @return Next line
     */
    public String nextLine()
    {
        return reader.nextLine();
    }

    /**
     * Prints out the string in a line.
     */
    public void println(String s)
    {
        textDisplay.append(s + "\n");
        textDisplay.setCaretPosition(textDisplay.getText().length());

        System.out.println(s);
    }

    /**
     * Prints out the string.
     */
    public void print(String s)
    {
        textDisplay.append(s);
        textDisplay.setCaretPosition(textDisplay.getText().length());

        System.out.print(s);
    }

}
