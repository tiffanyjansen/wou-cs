import java.util.Random;
import java.util.ArrayList;
/**
 * This is the main class of the whole game. It does everything, 
 * except create the GUI. Most of it's methods are private, since 
 * the user only needs to use one or two of the methods.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Game
{
    private String setting;
    private Random ran;
    private ArrayList<MathQuestion> questions;
    private BoardGUI board;
    private MathQuestion problem;
    private int firstInt;
    private int secondInt;

    /**
     * Constructor for objects of class Game
     */
    public Game()
    {
        ran = new Random();
        setting = "easy";
        questions = new ArrayList<MathQuestion>();
        addMath();
        board = new BoardGUI(this);
        printWelcome();
    }
    
    /**
     * The Main method, I'm not sure if it's required... but I thought
     * it would be good to have in there.
     * @param String[] args
     */
    public static void main(String[] args)
    {
        Game game = new Game();
    }

    /**
     * Adds all the different operations to the list to access them later. 
     * Allows for less "if" statements in the future.
     */
    private void addMath()
    {
        questions.add(new Addition());
        questions.add(new Subtraction());
        questions.add(new Multiplication());
        questions.add(new Division());
    }

    /**
     * Prints the welcome method to get the game started.
     */
    public void printWelcome()
    {
        board.println("Hello, welcome to the Incredibly Boring Math Game. Right now your setting is set to easy.");
        board.println("To change your setting just type the setting you wish to change it to. Your options are:"  
            + " easy, medium, or hard.");
    }

    /**
     * Sets the setting of the game.
     * @param newSetting; the new setting of the game.
     */
    public void setSetting(String newSetting)
    {
        setting = newSetting;
    }

    /**
     * Gets the setting of the game and prints it into the things
     * to see if it actually worked.
     */
    public void getSetting()
    {
        board.println("The new setting is: " + setting);
    }

    /**
     * Prints out the question that the person needs to answer.
     * Solves the given problem and returns the integer to be used in 
     * another method to decide what the player should do.
     */
    public void showProblem()
    {
        firstInt = MathQuestion.chooseInt();
        secondInt = MathQuestion.chooseInt();
        int num = 0;
        if(setting.equals("easy")){
            num = ran.nextInt(2);
        }
        else if(setting.equals("medium")){
            num = ran.nextInt(3);
        }
        else if(setting.equals("hard")){
            num = ran.nextInt(4);
        }        
        boolean successful = false;
        while(!successful){
            try{
                board.println(questions.get(num).askQuestion(firstInt, secondInt));
                problem = questions.get(num); 
                successful = true;
            }
            catch(NoNegativeAnswersException e){
                int newInt = firstInt;
                firstInt = secondInt;
                secondInt = firstInt;
            }
            catch(NoDividingZeroException e){
                secondInt = MathQuestion.chooseInt();
            }
           catch(NoDecimalAnswersException e){
                secondInt = MathQuestion.chooseInt();
            }
            catch(Exception e){
                System.err.println(e);
            }
        }
    }

    /**
     * Checks to see if the answer to the given problem is correct. This will also 
     * call the showProblem() method so we don't have to worry about it in the Player
     *                                  class.
     * @param answer; the answer inputted by the user.
     * @return back; spaces to move backwards if required.
     */
    public int checkAnswer(int answer)
    {
        int back = 0;
        if(answer == problem.solve(firstInt, secondInt))
        {
            board.println(answer + " is correct.");
        }
        else{            
            board.println(answer + " is incorrect. The correct anwer was " +  problem.solve(firstInt, secondInt)
                + ".");
            back = 1 + ran.nextInt(3);
            board.println("Move back " + back + " spaces.");
            board.moveBack(back);
        }
        return back;
    }

}