import java.util.Scanner;
import java.util.Random;

public class NumberGuess {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int number = rand.nextInt(100) + 1; // random number 1–100
        int guess = 0, attempts = 0;

        System.out.println("=== 🎲 Number Guessing Game ===");
        System.out.println("I'm thinking of a number between 1 and 100...");

        while (guess != number) {
            System.out.print("Enter your guess: ");
            guess = sc.nextInt();
            attempts++;

            if (guess > number) {
                System.out.println("Too high! Try again.");
            } else if (guess < number) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("🎉 Congratulations! You guessed the number in " + attempts + " attempts!");
            }
        }

        sc.close();
    }
}
