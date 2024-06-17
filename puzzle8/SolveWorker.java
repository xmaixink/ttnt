package puzzle8;

import javax.swing.SwingWorker;
import java.util.*;

public class SolveWorker extends SwingWorker<Void, int[][]> {
    private int[][] initialState;
    private int rows = 3;
    private int cols = 3;
    private Puzzle3x3 puzzle3x3Instance;

    public SolveWorker(int[][] initialState, Puzzle3x3 puzzle3x3Instance) {
        this.initialState = initialState;
        this.puzzle3x3Instance = puzzle3x3Instance;
    }

    @Override
    protected Void doInBackground() throws Exception {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<String> closedSet = new HashSet<>();

        int[][] startState = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                startState[i][j] = initialState[i][j];
            }
        }

        Node startNode = new Node(startState, 0, manhattanDistance(startState), null);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            if (isGoalState(currentNode.board)) {
                displaySolution(currentNode);
                return null;
            }

            String currentBoardStr = Arrays.deepToString(currentNode.board);
            if (closedSet.contains(currentBoardStr)) {
                continue;
            }
            closedSet.add(currentBoardStr);

            List<int[][]> neighbors = getNeighbors(currentNode.board);
            for (int[][] neighbor : neighbors) {
                int g = currentNode.g + 1;
                int h = manhattanDistance(neighbor);
                Node neighborNode = new Node(neighbor, g, h, currentNode);
                openSet.add(neighborNode);
            }
        }
        return null;
    }

    @Override
    protected void process(List<int[][]> chunks) {
        for (int[][] boardState : chunks) {
            for (int i = 0; i < puzzle3x3Instance.rows; i++) {
                for (int j = 0; j < puzzle3x3Instance.cols; j++) {
                    puzzle3x3Instance.board[i][j] = boardState[i][j];
                }
            }
            puzzle3x3Instance.updateBoard();
        }
    }

    private void displaySolution(Node node) {
        Stack<int[][]> solutionStack = new Stack<>();
        while (node != null) {
            solutionStack.push(node.board);
            node = node.parent;
        }

        while (!solutionStack.isEmpty()) {
            int[][] boardState = solutionStack.pop();
            publish(boardState);
            try {
                Thread.sleep(500); // Pause to show each step of the solution
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int manhattanDistance(int[][] state) {
        int distance = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int value = state[i][j];
                if (value != -1) {
                    int targetX = (value - 1) / rows;
                    int targetY = (value - 1) % cols;
                    distance += Math.abs(i - targetX) + Math.abs(j - targetY);
                }
            }
        }
        return distance;
    }

    private List<int[][]> getNeighbors(int[][] state) {
        List<int[][]> neighbors = new ArrayList<>();
        int emptyRow = -1, emptyCol = -1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (state[i][j] == -1) {
                    emptyRow = i;
                    emptyCol = j;
                    break;
                }
            }
        }

        int[] rowOffsets = {-1, 1, 0, 0};
        int[] colOffsets = {0, 0, -1, 1};

        for (int k = 0; k < 4; k++) {
            int newRow = emptyRow + rowOffsets[k];
            int newCol = emptyCol + colOffsets[k];
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                int[][] newBoard = new int[rows][cols];
                for (int i = 0; i < rows; i++) {
                    System.arraycopy(state[i], 0, newBoard[i], 0, state[i].length);
                }
                newBoard[emptyRow][emptyCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = -1;
                neighbors.add(newBoard);
            }
        }

        return neighbors;
    }

    private boolean isGoalState(int[][] state) {
        int count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (state[i][j] != count && state[i][j] != -1) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }
}
