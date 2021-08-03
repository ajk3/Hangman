package finalProject;

/**
 *
 * @author anurag
 * this class holds the random word, a list of letters that are guessed, 
 * and the number of remaining tries
 */
import java.util.List;

public class GameStat
    {
        private String word;
        private List<Character> lettersGuessed;
        private int trialRemaining;

        public GameStat() { }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public List<Character> getLettersGuessed() {
            return lettersGuessed;
        }

        public void setLettersGuessed(List<Character> lettersGuessed) {
            this.lettersGuessed = lettersGuessed;
        }

        public int getTrialRemaining() {
            return trialRemaining;
        }

        public void setTrialRemaining(int trialRemaining) {
            this.trialRemaining = trialRemaining;
        }
        
        @Override
        public String toString()
        {
            return String.format("%nCorrectly guessed word: %s, "
                               + "letters guessed: %s, "
                               + "Tries remeaning: %d%n",getWord()
                                                         ,getLettersGuessed()
                                                         ,getTrialRemaining());
        }
        
    } // end of class gameInfo

    
    
    


    


