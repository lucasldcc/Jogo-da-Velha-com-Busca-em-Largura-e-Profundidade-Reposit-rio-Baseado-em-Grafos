import java.util.*;

class GameState {
    char[][] board;
    boolean playerTurn;

    public GameState() {
        board = new char[3][3];
        for (char[] row : board)
            Arrays.fill(row, ' ');
        playerTurn = true;
    }

    public GameState(GameState gameState) {
        board = new char[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(gameState.board[i], 0, board[i], 0, 3);
        playerTurn = gameState.playerTurn;
    }

    public void printBoard() {
        System.out.println();
        for (char[] row : board) {
            System.out.printf(" %c | %c | %c %n", row[0], row[1], row[2]);
            System.out.println("---|---|---");
        }
        System.out.println();
    }

    public boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    public boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ')
                    return false;
            }
        }
        return true;
    }

    public String evaluate() {
        if (checkWin('X'))
            return "X wins";
        else if (checkWin('O'))
            return "O wins";
        return "";
    }
}

class Node {
    GameState state;
    int depth;

    public Node(GameState state, int depth) {
        this.state = state;
        this.depth = depth;
    }
}

public class Main {
    public static void bfs(GameState startState) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(startState, 0));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            String eval = node.state.evaluate();

            if (eval == "X wins") {
                node.state.printBoard();
                System.out.println("Player X wins");
                return;
            }
            
            if (eval == "O wins") {
                node.state.printBoard();
                System.out.println("Player O wins");
                return;
            }

            if (node.state.isDraw()) {
                node.state.printBoard();
                System.out.println("It's a draw!");
                return;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (node.state.board[i][j] == ' ') {
                        GameState newState = new GameState(node.state);
                        newState.board[i][j] = (newState.playerTurn) ? 'X' : 'O';
                        newState.playerTurn = !newState.playerTurn;
                        queue.offer(new Node(newState, node.depth + 1));
                    }
                }
            }
        }
    }

    public static void dfs(GameState state, int depth) {
        String eval = state.evaluate();

        if (eval == "X wins") {
            state.printBoard();
            System.out.println("Player X wins!");
            return;
        }
        if (eval == "O wins") {
            state.printBoard();
            System.out.println("Player O wins!");
            return;
        }
        if (state.isDraw()) {
            state.printBoard();
            System.out.println("It's a draw!");
            return;
        }
        if (depth <= 0)
            return;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state.board[i][j] == ' ') {
                    GameState newState = new GameState(state);
                    newState.board[i][j] = (newState.playerTurn) ? 'X' : 'O';
                    newState.playerTurn = !newState.playerTurn;
                    dfs(newState, depth - 1);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        GameState startState = new GameState();

        System.out.println("Breadth-First Search");
        bfs(startState);
        
        System.out.println("\nDepth-First Search");
        dfs(startState, 9);
    }
}