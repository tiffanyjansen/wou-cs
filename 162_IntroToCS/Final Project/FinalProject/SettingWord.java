
/**
 * These are the words for the settings used in the game.
 * This will allow for the user to set their own setting 
 * instead of the game just being set to "easy."
 * 
 * @author Tiffany Jansen
 * @version 06.03.2017
 */
public enum SettingWord
{
    EASY("easy"), MEDIUM("medium"), HARD("hard");
    
    private String setting;

    /**
     * Constructor for objects of class SettingWords
     * @param newSetting; the setting to be set.
     */
    SettingWord(String newSetting)
    {
       setting = newSetting;
    }

    /**
     * The toString() method to override the one in the Object
     * class.
     * @return setting; The setting. 
     */
    public String toString()
    {
       return setting;
    }
}
