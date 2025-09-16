import java.util.*;

public class SlidingPuzzle {

    // Dimensions of the puzzle board
    private static final int M = 2; // Rows
    private static final int N = 3; // Columns

    // Goal state represented as a string
    private static final String GOAL = "123450";

    // Possible directions for sliding the empty tile (0)
    // {row_change, col_change}
    private static final int[][] DIRS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int solveSlidingPuzzle(int[][] initialBoard) {
        // Convert the initial board to a string representation
        StringBuilder startSb = new StringBuilder();
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                startSb.append((char) ('0' + initialBoard[i][j]));
            }
        }
        String start = startSb.toString();

        // If the initial state is already the goal, no moves needed
        if (start.equals(GOAL)) {
            return 0;
        }

        // BFS setup
        Queue<String> queue = new ArrayDeque<>(List.of(start));
        Set<String> visited = new HashSet<>(Arrays.asList(start));
        int steps = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            steps++; // Increment steps for each level of the BFS

            for (int k = 0; k < levelSize; ++k) {
                String currentBoardString = queue.poll();
                int zeroIndex = currentBoardString.indexOf('0'); // Find the empty tile

                int zeroRow = zeroIndex / N;
                int zeroCol = zeroIndex % N;

                // Explore possible moves
                for (int[] dir : DIRS) {
                    int newZeroRow = zeroRow + dir[0];
                    int newZeroCol = zeroCol + dir[1];

                    // Check if the new position is within board boundaries
                    if (newZeroRow >= 0 && newZeroRow < M && newZeroCol >= 0 && newZeroCol < N) {
                        int newZeroIndex = newZeroRow * N + newZeroCol;

                        // Create a new board configuration by swapping
                        StringBuilder nextBoardSb = new StringBuilder(currentBoardString);
                        char charToSwap = nextBoardSb.charAt(newZeroIndex);
                        nextBoardSb.setCharAt(zeroIndex, charToSwap);
                        nextBoardSb.setCharAt(newZeroIndex, '0');
                        String nextBoardString = nextBoardSb.toString();

                        // If the new state is the goal, return the number of steps
                        if (nextBoardString.equals(GOAL)) {
                            return steps;
                        }

                        // If the new state hasn't been visited, add to queue and mark as visited
                        if (!visited.contains(nextBoardString)) {
                            queue.offer(nextBoardString);
                            visited.add(nextBoardString);
                        }
                    }
                }
            }
        }

        // If the goal state is unreachable
        return -1;
    }

    public static void main(String[] args) {
        SlidingPuzzle solver = new SlidingPuzzle();

        // Example initial board (unsolvable in this case, for demonstration)
        int[][] board1 = {{1, 2, 3}, {4, 0, 5}}; // Solvable
        System.out.println("Minimum moves for board1: " + solver.solveSlidingPuzzle(board1)); // Expected output: 2

        int[][] board2 = {{1, 2, 3}, {5, 4, 0}}; // Solvable
        System.out.println("Minimum moves for board2: " + solver.solveSlidingPuzzle(board2)); // Expected output: 1

        int[][] board3 = {{4, 1, 2}, {5, 0, 3}}; // Solvable
        System.out.println("Minimum moves for board3: " + solver.solveSlidingPuzzle(board3)); // Expected output: 3
    }
}
