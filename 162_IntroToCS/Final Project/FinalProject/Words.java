import java.util.HashMap;
/**
 * These are the keywords used for the Game. Some are 
 * just numbers and the others are for setting the Setting 
 * of the game. I am hoping that this class whill allow for
 * people to actually input stuff.
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public class Words
{    
    private HashMap<String, SettingWord> settings;
    private Number number;
    private Game game;
    private BoardGUI board;

    /**
     * Creates the HashMap and calls the fillMap() method
     *          to fill the map with the settings.
     * @param newGame; the game to be used.
     * @param newBoard; the GUI to be used.
     */
    public Words(Game newGame, BoardGUI newBoard)
    {
        settings = new HashMap<String, SettingWord>();
        fillMap();
        game = newGame;
        board = newBoard;
        number = new Number(newGame);
    }

    /**
     * Fills the HashMap with the settings allowed.
     */
    private void fillMap()
    {
        for(SettingWord setting : SettingWord.values()){
            settings.put(setting.toString(), setting);
        }
    }

    /**
     * Gets the word that was inputed and decides whether 
     * it's a settingWord or number, then returns whatever is 
     *                      required.
     * @param word; The word the user inputted.
     * @return boolean; Whether or not the input was useful.
     */
    public boolean getInput(String word)
    {
        try{
            boolean num = false;
            if(settings.get(word) != null){
                game.setSetting(word);
                game.getSetting();
                num = true;
            }

            else if(Integer.valueOf(word) > -1){
                int input = (int) Integer.parseInt(word); 
                number.answer(input);
                num = true;
            }
            return num;
        }
        catch(NumberFormatException e){
            board.println("The word you entered was not recognized.");
            return false;
        }
    }
}
