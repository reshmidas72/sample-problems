import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MemoryGame {

    private static final int GRID_SIZE = 4; // 4x4 grid
    private static final String HIDDEN_CARD = "*";

    private String[][] board;
    private List<String> cards;
    private boolean[][] revealed;

    public MemoryGame() {
        board = new String[GRID_SIZE][GRID_SIZE];
        revealed = new boolean[GRID_SIZE][GRID_SIZE];
        cards = new ArrayList<>();
        initializeCards();
        shuffleAndPlaceCards();
    }

    private void initializeCards() {
        // Create pairs of cards (e.g., A, A, B, B, etc.)
        String[] cardValues = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (String value : cardValues) {
            cards.add(value);
            cards.add(value);
        }
    }

    private void shuffleAndPlaceCards() {
        Collections.shuffle(cards);
        int cardIndex = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j] = cards.get(cardIndex++);
                revealed[i][j] = false; // All cards start hidden
            }
        }
    }

    public void printBoard(boolean showAll) {
        System.out.print("  ");
        for (int j = 0; j < GRID_SIZE; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                if (showAll || revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print(HIDDEN_CARD + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (!revealed[i][j]) {
                    return false; // Not all cards are revealed
                }
            }
        }
        return true; // All cards are revealed, game over
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;

        System.out.println("Welcome to the Memory Game!");

        while (!isGameOver()) {
            printBoard(false); // Display the board with hidden cards
            attempts++;

            System.out.println("Attempt " + attempts);
            System.out.print("Enter row and column for first card (e.g., 0 1): ");
            int r1 = scanner.nextInt();
            int c1 = scanner.nextInt();

            // Validate input
            if (r1 < 0 || r1 >= GRID_SIZE || c1 < 0 || c1 >= GRID_SIZE || revealed[r1][c1]) {
                System.out.println("Invalid selection or card already revealed. Try again.");
                continue;
            }

            revealed[r1][c1] = true;
            printBoard(false); // Show the first flipped card

            System.out.print("Enter row and column for second card (e.g., 2 3): ");
            int r2 = scanner.nextInt();
            int c2 = scanner.nextInt();

            // Validate input
            if (r2 < 0 || r2 >= GRID_SIZE || c2 < 0 || c2 >= GRID_SIZE || revealed[r2][c2] || (r1 == r2 && c1 == c2)) {
                System.out.println("Invalid selection or card already revealed/same as first card. Try again.");
                revealed[r1][c1] = false; // Hide the first card again if second selection is invalid
                continue;
            }

            revealed[r2][c2] = true;
            printBoard(false); // Show both flipped cards

            if (board[r1][c1].equals(board[r2][c2])) {
                System.out.println("Match found!");
            } else {
                System.out.println("No match. Cards will be hidden again.");
                // Briefly show the cards before hiding them (can add a delay here)
                try {
                    Thread.sleep(1500); // Pause for 1.5 seconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                revealed[r1][c1] = false;
                revealed[r2][c2] = false;
            }
        }

        System.out.println("Congratulations! You won in " + attempts + " attempts!");
        scanner.close();
    }

    public static void main(String[] args) {
        MemoryGame game = new MemoryGame();
        game.play();
    }
}
