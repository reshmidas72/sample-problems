import java.util.Scanner;
import java.util.Random;

public class TypingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String[] words = {"java", "computer", "keyboard", "program", "developer", "algorithm", "game", "challenge", "typing", "speed"};
        
        System.out.println("Welcome to the Typing Game!");
        System.out.println("Type the words as fast as you can. Press Enter after each word.");
        System.out.println("Game starts in 3 seconds...");
        
        try {
            Thread.sleep(3000); // Wait 3 seconds before starting
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int totalWords = 5; // Number of words per game
        int correctWords = 0;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < totalWords; i++) {
            String word = words[random.nextInt(words.length)];
            System.out.println("Type this word: " + word);
            String input = scanner.nextLine();

            if (input.equals(word)) {
                System.out.println("Correct!");
                correctWords++;
            } else {
                System.out.println("Wrong! The correct word was: " + word);
            }
        }

        long endTime = System.currentTimeMillis();
        double timeTakenSeconds = (endTime - startTime) / 1000.0;

        System.out.println("\nGame Over!");
        System.out.println("Words typed correctly: " + correctWords + "/" + totalWords);
        System.out.println("Time taken: " + timeTakenSeconds + " seconds");

        double wordsPerMinute = (correctWords / timeTakenSeconds) * 60;
        System.out.println("Typing speed: " + String.format("%.2f", wordsPerMinute) + " WPM");

        scanner.close();
    }
}
