
/**
 * This class will allow the user to input their answers to 
 * the math questions without it being an issue for the game.
 * 
 * @author Tiffany Jansen 
 * @version 06.03.2017
 */
public class Number
{
    private Game game;

    /**
     * Constructor for objects of class Numbers.
     * @param newGame; The game to be used.
     */
    public Number(Game newGame)
    {  
        game = newGame;
    }
    
    /**
     * Checks the actual answer of the problem to the input given
     *              by the player.
     * @param input; the input from the player.
     * @return the answer of the problem in relation to the input.
     */
    public int answer(int input)
    {
        return game.checkAnswer(input);        
    }
}
