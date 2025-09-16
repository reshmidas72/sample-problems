import java.util.Scanner;

public class SudokuGame {

    private static final int GRID_SIZE = 9;
    private static int[][] board; // Represents the Sudoku board

    public static void main(String[] args) {
        initializeBoard(); // You can set up a pre-defined puzzle here
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Sudoku!");

        while (true) {
            printBoard();
            System.out.println("Enter row (1-9), column (1-9), and value (1-9), or 's' to solve, or 'q' to quit:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("Exiting Sudoku. Goodbye!");
                break;
            } else if (input.equalsIgnoreCase("s")) {
                if (solveSudoku()) {
                    System.out.println("Sudoku solved!");
                    printBoard();
                } else {
                    System.out.println("No solution exists for the current board.");
                }
                break; // Exit after solving
            }

            try {
                String[] parts = input.split(" ");
                if (parts.length != 3) {
                    System.out.println("Invalid input format. Please enter row, column, and value separated by spaces.");
                    continue;
                }
                int row = Integer.parseInt(parts[0]) - 1; // Adjust for 0-indexed array
                int col = Integer.parseInt(parts[1]) - 1;
                int value = Integer.parseInt(parts[2]);

                if (isValidMove(row, col, value)) {
                    board[row][col] = value;
                    if (isGameWon()) {
                        System.out.println("Congratulations! You solved the Sudoku!");
                        printBoard();
                        break;
                    }
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers for row, column, and value.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Row and column must be between 1 and 9.");
            }
        }
        scanner.close();
    }

    // Initializes the Sudoku board with a sample puzzle (0 for empty cells)
    private static void initializeBoard() {
        board = new int[][]{
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    }

    // Prints the current state of the Sudoku board
    private static void printBoard() {
        for (int r = 0; r < GRID_SIZE; r++) {
            if (r % 3 == 0 && r != 0) {
                System.out.println("---------------------");
            }
            for (int c = 0; c < GRID_SIZE; c++) {
                if (c % 3 == 0 && c != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[r][c] == 0 ? ". " : board[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Checks if a move is valid according to Sudoku rules
    private static boolean isValidMove(int row, int col, int value) {
        if (value < 1 || value > 9 || board[row][col] != 0) {
            return false; // Value out of range or cell already filled
        }

        // Check row
        for (int c = 0; c < GRID_SIZE; c++) {
            if (board[row][c] == value) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < GRID_SIZE; r++) {
            if (board[r][col] == value) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int startRow = row - (row % 3);
        int startCol = col - (col % 3);
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (board[r][c] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    // Checks if the game is won (all cells filled and valid)
    private static boolean isGameWon() {
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                if (board[r][c] == 0) {
                    return false; // Not all cells filled
                }
            }
        }
        // A more robust check would involve re-validating the entire board,
        // but for a simple game, filling correctly implies validity.
        return true;
    }

    // Sudoku solver using backtracking
    private static boolean solveSudoku() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (board[row][col] == 0) { // Find an empty cell
                    for (int num = 1; num <= 9; num++) {
                        if (isValidMove(row, col, num)) {
                            board[row][col] = num; // Try placing a number
                            if (solveSudoku()) { // Recursively try to solve
                                return true;
                            } else {
                                board[row][col] = 0; // Backtrack if current path doesn't lead to a solution
                            }
                        }
                    }
                    return false; // No number works for this cell
                }
            }
        }
        return true; // All cells filled, puzzle solved
    }
}
