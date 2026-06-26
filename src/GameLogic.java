package tictactoe;

public class GameLogic {

    private final char[][] board;
    private char currentPlayer;
    private boolean gameOver;

    public GameLogic() {
        board = new char[3][3];
        resetGame();
    }

    public boolean makeMove(int row, int column) {
        if (gameOver || board[row][column] != '-') {
            return false;
        }

        board[row][column] = currentPlayer;

        if (hasWinner()) {
            gameOver = true;
        } else if (isBoardFull()) {
            gameOver = true;
        } else {
            switchPlayer();
        }

        return true;
    }

    public char getCell(int row, int column) {
        return board[row][column];
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasWinner() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    public boolean isDraw() {
        return gameOver && !hasWinner() && isBoardFull();
    }

    public void resetGame() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                board[row][column] = '-';
            }
        }

        currentPlayer = 'X';
        gameOver = false;
    }

    private void switchPlayer() {
        if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    private boolean checkRows() {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] != '-'
                    && board[row][0] == board[row][1]
                    && board[row][1] == board[row][2]) {
                return true;
            }
        }

        return false;
    }

    private boolean checkColumns() {
        for (int column = 0; column < 3; column++) {
            if (board[0][column] != '-'
                    && board[0][column] == board[1][column]
                    && board[1][column] == board[2][column]) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonals() {
        boolean mainDiagonal =
                board[0][0] != '-'
                        && board[0][0] == board[1][1]
                        && board[1][1] == board[2][2];

        boolean secondaryDiagonal =
                board[0][2] != '-'
                        && board[0][2] == board[1][1]
                        && board[0][2] == board[2][0];

        return mainDiagonal || secondaryDiagonal;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (board[row][column] == '-') {
                    return false;
                }
            }
        }

        return true;
    }
}