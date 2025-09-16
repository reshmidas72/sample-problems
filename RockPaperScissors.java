import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        String[] choices = {"Rock", "Paper", "Scissors"};
        int userChoice, computerChoice;

        System.out.println("=== ✊✋✌ Rock, Paper, Scissors Game ===");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Rock");
            System.out.println("2. Paper");
            System.out.println("3. Scissors");
            System.out.println("4. Exit");
            System.out.print("Your choice: ");
            userChoice = sc.nextInt();

            if (userChoice == 4) {
                System.out.println("👋 Thanks for playing!");
                break;
            }

            if (userChoice < 1 || userChoice > 4) {
                System.out.println("⚠ Invalid choice. Try again.");
                continue;
            }

            computerChoice = rand.nextInt(3) + 1;
            System.out.println("You chose: " + choices[userChoice - 1]);
            System.out.println("Computer chose: " + choices[computerChoice - 1]);

            if (userChoice == computerChoice) {
                System.out.println("🤝 It's a draw!");
            } else if ((userChoice == 1 && computerChoice == 3) ||
                       (userChoice == 2 && computerChoice == 1) ||
                       (userChoice == 3 && computerChoice == 2)) {
                System.out.println("🎉 You win!");
            } else {
                System.out.println("💻 Computer wins!");
            }
        }

        sc.close();
    }
}
