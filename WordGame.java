package finalProject;

/**
 *
 * @author anurag CMSY-167-N001
 * this is a command line game which allow users to guess letters in a mystery word,
 * one letter at a time. 
 * If the user is able to guess every letter in the mystery word, 
 * the user will win. 
 * If the user runs out of guess attempts, the user will lose.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.InputMismatchException;

public class WordGame
{
    // constants
    private static final int SIX_LETTER_WORD = 6;
    private static final int EIGHT_LETTER_WORD = 8;
    private static final int TEN_LETTER_WORD = 10;
    
    private static final int BASIC = 1;
    private static final int MEDIUM = 2;
    private static final int DIFFICULT = 3;
    
    private static final int TEN_ATTEMPTS= 10;
    private static final int SEVEN_ATTEMPTS= 7;
    private static final int FOUR_ATTEMPTS= 4;
    
    public static void main(String [] args)
    {
        Scanner input = new Scanner(System.in);
        
        List<String> sixLetters = new ArrayList<>();
        List<String> eightLetters = new ArrayList<>();
        List<String> tenLetters = new ArrayList<>();
        
        
        List<GameStat> winnerInfo = new ArrayList<>(); // holds the winning games
        
        //This function divides the words into three separate List based on their length
        wordSeperator(sixLetters, eightLetters,tenLetters);
         
        
        levelsOfGame(input,sixLetters, eightLetters,tenLetters,winnerInfo);
        
        System.out.println();
        
        System.out.println("Thank you so much for playing the hangman!!!!\n");
        
        input.close();
        
    } // end of main
     
    /**
     * 
     * @param sixLetters
     * @param eightLetters
     * @param tenLetters 
     * 
     * This function loads the list of words and divide them into three separate Lists
     * based on word length:
     * one List of six-letter words,
     * one of eight-letter words,
     * and one of ten-letter words.
     */
    public static void wordSeperator(List<String> sixLetters, 
                            List<String> eightLetters  ,List<String> tenLetters)
    {
        List<String> words = new ArrayList<>();
        
        try(Scanner input = new Scanner(Paths.get("hangmanWords.txt")))
        {
            while(input.hasNext())
            {
                words.add(input.nextLine());
            }
        }
        catch(IOException e)
        {
            System.out.println("Problem encountered. Terminating the program");
        }
        
        words.forEach((String word) ->
        {
            if(word.length() == SIX_LETTER_WORD)
            {
                sixLetters.add(word.toLowerCase());
            }
            else if(word.length() == EIGHT_LETTER_WORD)
            {
                eightLetters.add(word.toLowerCase());
            }
            else
            {
                tenLetters.add(word.toLowerCase());
            }
        }); 
    } // end of function word seperator
    
    /**
     * 
     * @param input
     * @return choice
     * this function displays the menu and lets the user select a difficulty level
     */
    public static int menu(Scanner input)
    {
        int choice = 0;
        boolean isValid = false;
        
        System.out.println("select a difficulty level");
        System.out.println("1 - Basic: 10 letter word, 10 guess attempts");
        System.out.println("2 - Medium: 8 letter word, 7 guess attempts");
        System.out.println("3 - Difficult: 6 letter word, 4 guess attempts");
        
        System.out.println();
        
        do
        {
            try
            {
                choice = input.nextInt();
                Boolean withInRange = (choice >=1 && choice <=3); 

                while(!withInRange) // validation
                {
                    System.out.println("Invalid Choice!\nPlease try again!\n");
                    System.out.println("select a difficulty level!!!!");
                    choice = input.nextInt();
                    withInRange = (choice >=1 && choice <=3);
                }
                isValid = true;
            }
            catch(InputMismatchException e)
            {
                System.out.println("Invalid Choice!\nPlease try again!\n");
                System.out.println("select a difficulty level!!!!");
                input.nextLine(); // flushing the garbage input
            }
            
        }while(!isValid);
        
        return choice;
        
    } // end of function menu
   
    /**
     * 
     * @param words
     * @param winnerInfo
     * @param numTries 
     * this function shows the status of the mystery word, Text indicating the 
     * number of guesses remaining, letters that are already guessed, and 
     * prompts the user to input the next letter
     */
    public static void guessLetter(List<String> words,List<GameStat> winnerInfo, int numTries)
    {
        SecureRandom random = new SecureRandom();
        Scanner input =  new Scanner(System.in);
        
        boolean guessed;
        
        ArrayList<Character> usedChars = new ArrayList<>(); // to store used chars
        
        int randomInt = random.nextInt(words.get(0).length()); // generates random number from 0 upto the last index 
        String randomWord = (words.get(randomInt)); // generates random word

        char [] randomWordToChar = randomWord.toCharArray();
        
        //System.out.println(randomWordToChar);
       
        char[] guess = new char[randomWordToChar.length]; // The array that holds the user's guess 
        Arrays.fill(guess,'_'); // '_' will be later replaced by the correct letters if the guess is correct. 
        
        System.out.println("Let's begin...");
        
        System.out.print("\nGuess the word: ");
        
        do
        {
           
            
            displayChar(guess); // printing the char array
            
            System.out.println("\n\n-------------------------------------------"
                             + "----------------------------------------------");


            System.out.printf("\nYou have %d remaining guesses", (numTries));
            System.out.printf("\nYou have already guessed the following letters: %s%n", usedChars);

            System.out.print("\nEnter next letter: ");
            String userInput = input.next().toLowerCase();
           
            boolean validChar = isValid(usedChars,userInput); // validating the char
            
            boolean printed = false; // to indicate whether the guess is correct or not
            
            System.out.println();
          
            if(validChar)
            {
               usedChars.add(userInput.charAt(0));
               
               for(int j = 0; j < guess.length; j++)
               {
                   if(randomWordToChar[j] == userInput.charAt(0))
                   {  
                        guess[j] = userInput.charAt(0);
                  
                        if(!printed) // prints the following statement just once
                                    // even if there are multiple matches
                        {
                           System.out.println("Correct guess!\n");
                           printed = true;
                        }
                   }
               }
            }
            if(!printed || !validChar)
            {
                numTries--;
            }
           
            
            guessed = (Arrays.equals(guess,randomWordToChar)? true : false);
            
        }while (numTries != 0 && !guessed);
      
        // if the word is guessed correctly, a obj of the class GameStat will 
        // be created, and it will be added to the winnerInfo list
        if(guessed) 
        {
            GameStat winner = new GameStat();
            
            winner.setWord(randomWord);
            winner.setLettersGuessed(usedChars);
            winner.setTrialRemaining(numTries);
            
            winnerInfo.add(winner);
            
            displayChar(guess);
            
            System.out.println("\n\n-------------------------------------------"
                             + "----------------------------------------------");
            
            System.out.print("\nCongratulation You got it!!!\n");
        }
        else
        {
            displayChar(guess);
        
            System.out.println("\n\n-------------------------------------------"
                             + "----------------------------------------------");
            
            System.out.printf("\nYou have %d remaining guesses%n",numTries);
            System.out.print("Sorry, you ran out of tries\n");
            System.out.print("\nThe correct word is: ");
            
            displayChar(randomWordToChar);
            
            System.out.println();
        } 
    }  // end of functions guessLetter
    
    /**
     * 
     * @param usedChars
     * @param userInput
     * @return a bool value indicating whether the input is valid or not
     * input is not valid if it is more than one letter, less than one letter,
     * if it is already guessed, or if it's not a letter
     */
    private static boolean isValid(ArrayList<Character> usedChars, String userInput)
    {
        boolean validChar = true;
        boolean alreadyGuessed = false;
        
        Collections.sort(usedChars); // Sorted the collection to use the binary search in a later step
        
        //This if statement checks whether the user's input is a letter or a digit,
        //if it's a single character or not, and prints the appropriate message
        if((userInput.isBlank()) || userInput.length() != 1 
                                 || !(Character.isLetter(userInput.charAt(0))))
        {
            if(userInput.isBlank())
            {
                System.out.println("\nYour guess is less than one letter");
            }
            else if (!(Character.isLetter(userInput.charAt(0))))
            {
                System.out.println("\nYour guess is not a letter");
            }
            else
            {
                System.out.println("\nYour guess is more than one letter");
            }
            
            validChar = false;
        }
        else if(Collections.binarySearch(usedChars, userInput.charAt(0)) >= 0)
        {
            System.out.print("\nLetter "+userInput+" is already guessed!!\n");
            alreadyGuessed = true;
        }
           
        return (!alreadyGuessed && validChar) ? true : false; 
        
    } // end of function isValid
    
    // prints the char array
    public static void displayChar(char[] words)
    {
        for (char a : words)
        {
            System.out.print(a+" ");
        }
    } // end of function displayChar
    
    /**
     * 
     * @param input
     * @param sixLetters
     * @param eightLetters
     * @param tenLetters
     * @param winnerInfo 
     * 
     * This function uses the switch statement to call the guessLetter method 
     * with the correct arguments based on the user's input.
     * Also, users can choose to play again after completing each session, 
     * and if the user quits the game, the winning games will be saved in a file. 
     */
    public static void levelsOfGame(Scanner input, List<String> sixLetters, 
                            List<String> eightLetters ,List<String> tenLetters,
                            List<GameStat> winnerInfo)
    {
        boolean playAgain;
        
        do
        {
            int choice = menu(input);

            switch(choice)
            {
                case BASIC:
                {
                    System.out.println("Is that all you've got?");
                    guessLetter(tenLetters,winnerInfo,TEN_ATTEMPTS);
                    break;
                }
                case MEDIUM:
                {
                    System.out.println("Don't hurt yourself.");
                    guessLetter(eightLetters, winnerInfo, SEVEN_ATTEMPTS);
                    break;
                }
                case DIFFICULT:
                {
                    System.out.println("Are you sure you can handle it?");
                    guessLetter(sixLetters, winnerInfo, FOUR_ATTEMPTS);
                    break;
                }
                default:
                {
                    System.out.println("Invalid Choice!!!");
                }
            }
            
            System.out.print("\nDo you want to play again (Y/N): ");
            char willContinue = input.next().toLowerCase().charAt(0);
            
            while(willContinue != 'y' && willContinue != 'n')
            {
                System.out.println("Invalid!!!!!"
                                  +"Please type Y or N");
                
                System.out.print("\nDo you want to play again (Y/N): ");
                willContinue = input.next().toLowerCase().charAt(0);
            }
            
            playAgain = (willContinue == 'y') ? true : false;

            System.out.println();
            
        }while(playAgain);
        
        saveGame(winnerInfo); // if the user quits the game, the winning games will be saved
        
    } // end of function levelsOfGame
    
    /**
     * 
     * @param <T>
     * @param winnerInfo 
     * this function saves the winning games to a file
     */
    public static <T> void saveGame(List<T> winnerInfo)
    {
        try(Formatter output = new Formatter("winningGames.txt"))
        {
            output.format("%s", winnerInfo);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Error Encountered. Program terminating!!!");
        }
    } // end of function saveGame 
    
} // end of class words
