import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import javax.swing.text.*;
import javax.swing.event.*;

/**
 * It will do a lot of the heavy lifting. It contains all the 
 * menu items and all the GUI stuff. One of the most important 
 * classes of the project.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class BoardGUI
{
    private JTextArea display;
    private JTextField input;
    private Game game;
    private Words word;
    private Dice dice;
    private Player player;
    private ArrayList<Space> spaces;
    private boolean inputtedAnswer;
    private JFrame frame;
    private JProgressBar progress;
    private JPanel board;

    /**
     * Constructor that contructs the entire GUI. 
     * Only needs to be created once.
     * @param newGame; The game to be used.
     */
    public BoardGUI(Game newGame)
    {
        player = new Player();
        createGUI();
        game = newGame;
        word = new Words(newGame, this);
        dice = new Dice();
        inputtedAnswer = true;
    }

    /**
     * Creates the GUI and contains all the methods to create it.
     */
    private void createGUI()
    {
        frame = new JFrame("The Incredibly Boring Math Game");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        board = createBoard();
        contentPane.add(board, BorderLayout.CENTER);
        
        JColorChooser chooser = new JColorChooser();
        Color boardColor = chooser.showDialog(board, "Choose Your Background Color", Color.white);
        board.setBackground(boardColor);
        
        display = new JTextArea(4, 45);
        display.setEditable(false);
        JScrollPane scroll = new JScrollPane(display);       
        contentPane.add(scroll, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout());
        JButton diceButton = new JButton("Roll Dice", new ImageIcon("dice.jpg"));
        diceButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    try{
                        rollDice();
                    }
                    catch(NoAnswerException ex){
                        println("Please enter an answer before rolling the dice again.");
                    }
                }
            });
        textPanel.add(diceButton);
        input = new JTextField(10);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.addActionListener(new ActionListener() { 
                public void actionPerformed(ActionEvent e){
                    getInput();
                }
            });
        textPanel.add(input);
        contentPane.add(textPanel, BorderLayout.SOUTH);
        
        progress = new JProgressBar(SwingConstants.VERTICAL, 0, 40);
        progress.setStringPainted(true);
        progress.setBackground(Color.white);
        progress.setForeground(Color.green);
        progress.setString("This is your progress in the game.");
        contentPane.add(progress, BorderLayout.EAST);

        createMenu(frame);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the board. Returns the JPanel with everything that 
     * is needed for the board. This way the code for the makeGUI 
     *                  isn't too long.
     * @return JPanel; The JPanel with the board.
     */
    private JPanel createBoard()
    {
        JPanel panel = new JPanel();        
        spaces = new ArrayList<Space>();

        GridBagConstraints con = new GridBagConstraints();

        panel.setLayout(new GridBagLayout());
        for(int y = 1; y < 10; y++){
            con.gridx = 1;
            con.gridy = y;
            Space space = new Space();
            if(y == 1){
                space.setSpace(player.getToken());
            }
            spaces.add(space);
            panel.add(space.getSpace(), con);
        }
        for(int x = 1; x < 12; x++){
            con.gridx = x;
            con.gridy = 10;
            Space space = new Space();
            spaces.add(space);
            panel.add(space.getSpace(), con);
        }
        for(int y = 10; y > 1; y--){
            con.gridx = 12;
            con.gridy = y;
            Space space = new Space();
            spaces.add(space);
            panel.add(space.getSpace(), con);
        }
        for(int x = 12; x > 1; x--){
            con.gridx = x;
            con.gridy = 1;
            Space space = new Space();
            spaces.add(space);
            panel.add(space.getSpace(), con);
        }

        JLabel image = new JLabel(new ImageIcon("math.jpg"));
        con.gridx = 4;
        con.gridy = 3;
        con.gridwidth = 6;
        con.gridheight = 6;
        panel.add(image, con);

        return panel;
    }

    /**
     * Creates the Menu. Takes a JFrame to make sure that the same 
     * frame that is used for the Main GUI is also used for the Menu.
     * @param JFrame; the frame used for the GUI.
     */
    private void createMenu(JFrame frame)
    {
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);

        JMenu file = new JMenu("File");
        menu.add(file);

        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        open.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) 
                {
                    open();
                }
            });
        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        save.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    save();
                }
            });
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    quit();
                }
            });
        file.add(quit);

        JMenu help = new JMenu("Help");
        menu.add(help);

        JMenuItem helpItem = new JMenuItem("Help");
        help.add(helpItem);
        helpItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    help();
                }
            });
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        about.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    about();
                }
            });
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    reset();
                }
            }); 
        help.add(reset);
    }

    /**
     * Prints all the information by line into the display.
     * @param string; The string we want to be added to the display.
     */
    public void println(String string)
    {
        display.append(" " + string + "\n");
        display.setCaretPosition(display.getText().length());
    }

    /**
     * Resets the space on the game board.
     */
    private void resetOriginal()
    {
        int reset = player.getSpaces();
        spaces.get(reset).setSpace(new ImageIcon("space.png"));
    }

    /**
     * Moves the player backwards when they get a math question
     * wrong.
     * @param back; the number of spaces to move back.
     */
    public void moveBack(int back)
    {
        resetOriginal();
        player.moveBackward(back);
        if(player.getSpaces() < 0)
        {
            player.resetSpaces();
            spaces.get(player.getSpaces()).setSpace(player.getToken());
        }
        else{
            player.moveBackward(back);
            spaces.get(player.getSpaces()).setSpace(player.getToken());
        }
        progress.setValue(player.getSpaces()); 
    }

    //Action Events
    /**
     * Closes the program.
     */
    private void quit()
    {
        System.exit(0);
    }

    /**
     * Rolls the dice and gets the game started.
     * @throws NoAnswerException when you haven't answered the question yet.
     */
    private void rollDice() throws NoAnswerException
    {      
        try{
            if(!inputtedAnswer){
                throw new NoAnswerException("You have not answered the question yet.");
            }

            resetOriginal(); 

            int rolled = dice.rollDice();
            player.moveForward(rolled);

            int pSpace = player.getSpaces();
            progress.setValue(pSpace);
            if(pSpace > 40){            
                println("You have won the game. Would you like to play again? If so, please choose" + 
                    " Reset in the Help menu.");
            }
            else{
                println("You rolled a: " + rolled + ". Now you get to answer a math" +
                    " question.");
                spaces.get(pSpace).setSpace(player.getToken());
                game.showProblem(); 
                inputtedAnswer = false;
            }    
        }
        catch(Exception e){
            println("You cannot roll again once you have won, please choose Reset in the help menu.");
        }
    }

    /**
     * Gets the input from the line where people type their stuff.
     * @return String; the string the user typed into the line.
     */
    private String getInput()
    {  
        String stringLine = input.getText();
        inputtedAnswer = word.getInput(stringLine);
        input.setText("");
        return stringLine;
    }

    /**
     * Opens the mathGame text file and appends it into the display.
     */
    private void open()
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("mathGame.txt"));
            println(" ");
            println("The file you opened will be read below: ");
            String read = reader.readLine();
            while(read != null){
                println(read);
                read = reader.readLine();
            }
            reader.close();
        }
        catch(FileNotFoundException e){
            System.err.println("An error has occured.");
        }        catch(IOException e){
            System.err.println("An error has occured.");
        }        
    }

    /**
     * Saves the text from the display into the mathGame text file.
     * @return yes; whether or not the save thing was successful.
     */
    private boolean save()
    {
        boolean yes = false;
        try{
            FileWriter writer = new FileWriter("mathGame.txt");
            writer.write(display.getText());
            writer.close();
            yes = true;
        }
        catch(IOException e){
            System.err.println("An error has occured.");
        }
        return yes;
    }

    /**
     * Creates an Information Message to help the user figure out what to
     * do next.
     */
    private void help()
    {
        String help = "Have you rolled the dice?\n" + "Maybe you forgot to input an answer.\n" + 
            "Try inputting an answer or rolling the dice.\n" + "Good luck!"; 
        JOptionPane.showMessageDialog(frame, help, "The Incredibly Boring Math Game", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Creates an Information Message to help the user figure out what the 
     * game is about.
     */
    private void about()
    {
        String about = "This is the Incredibly Boring Math Game Version 1.2.\n" + "Created by: Tiffany Jansen\n" +
            "Class created for: CS 162.\n" + "I hope you enjoy playing the game.";
        JOptionPane.showMessageDialog(frame, about, "The Incredibly Boring Math Game", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Resets the game to the original settings so you can play again
     * if you would like.
     */
    private void reset()
    {
        if(player.getSpaces() < 40){
            resetOriginal();
        }
        println("");
        spaces.get(0).setSpace(player.getToken());
        player.resetSpaces();
        progress.setValue(0);
        game.printWelcome();
    }

}
