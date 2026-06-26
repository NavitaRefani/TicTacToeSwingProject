package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends JFrame {

    private static final Color BACKGROUND_COLOR = new Color(201, 225, 240);
    private static final Color PANEL_COLOR = new Color(201, 225, 240);
    private static final Color X_COLOR = new Color(235, 201, 151);
    private static final Color O_COLOR = new Color(126, 82, 57);
    private static final Color BUTTON_COLOR = new Color(44, 95, 125);

    private Player currentPlayer;
    private final PlayerService playerService;
    private final GameLogic gameLogic;

    private final JButton[][] boardButtons;
    private final JLabel statusLabel;
    private final JLabel scoreLabel;

    private final Random random;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic = new GameLogic();
        this.boardButtons = new JButton[3][3];
        this.random = new Random();

        setTitle("Tic-Tac-Toe - Game");
        setSize(620, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(BACKGROUND_COLOR);

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setBackground(PANEL_COLOR);
        informationPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel playerLabel = new JLabel(
                "Player: " + this.currentPlayer.getUsername() + " (X)"
        );
        playerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerLabel.setForeground(new Color(55, 85, 70));

        scoreLabel = new JLabel("Score: " + this.currentPlayer.getScore());
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setForeground(new Color(55, 85, 70));

        statusLabel = new JLabel("Your turn: X");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setForeground(new Color(55, 85, 70));

        playerLabel.setForeground(new Color(50, 58, 68));
        scoreLabel.setForeground(new Color(50, 58, 68));
        statusLabel.setForeground(new Color(50, 58, 68));

        informationPanel.add(playerLabel);
        informationPanel.add(Box.createVerticalStrut(8));
        informationPanel.add(scoreLabel);
        informationPanel.add(Box.createVerticalStrut(8));
        informationPanel.add(statusLabel);

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 12, 12));
        boardPanel.setBackground(BACKGROUND_COLOR);
        boardPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 64));
                button.setFocusPainted(false);
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBackground(BUTTON_COLOR);
                button.setForeground(Color.BLACK);
                button.setBorder(
                        BorderFactory.createLineBorder(
                                new Color(80, 130, 110),
                                2,
                                true
                        )
                );
                button.setPreferredSize(new Dimension(150, 150));

                final int selectedRow = row;
                final int selectedColumn = column;

                button.addActionListener(event ->
                        processPlayerMove(selectedRow, selectedColumn)
                );

                boardButtons[row][column] = button;
                boardPanel.add(button);
            }
        }

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(PANEL_COLOR);

        JButton resetButton = new JButton("Play again");
        JButton backButton = new JButton("Back to menu");

        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        resetButton.setBackground(new Color(44, 95, 125));
        resetButton.setForeground(Color.WHITE);
        resetButton.setOpaque(true);
        resetButton.setContentAreaFilled(true);
        resetButton.setFocusPainted(false);

        backButton.setBackground(new Color(126, 82, 57));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        backButton.setFocusPainted(false);

        bottomPanel.add(resetButton);
        bottomPanel.add(backButton);

        resetButton.addActionListener(event -> resetGame());

        backButton.addActionListener(event -> {
            dispose();

            Player refreshedPlayer =
                    playerService.getPlayerById(this.currentPlayer.getId());

            if (refreshedPlayer != null) {
                this.currentPlayer = refreshedPlayer;
            }

            MainMenuFrame mainMenuFrame =
                    new MainMenuFrame(this.currentPlayer);

            mainMenuFrame.setVisible(true);
        });

        mainPanel.add(informationPanel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void processPlayerMove(int row, int column) {
        if (gameLogic.isGameOver()) {
            return;
        }

        boolean moveAccepted = gameLogic.makeMove(row, column);

        if (!moveAccepted) {
            return;
        }

        updateBoard();

        if (gameLogic.isGameOver()) {
            finishGame();
            return;
        }

        statusLabel.setText("Computer's turn: O");

        Timer computerTimer = new Timer(500, event -> {
            processComputerMove();
            ((Timer) event.getSource()).stop();
        });

        computerTimer.setRepeats(false);
        computerTimer.start();
    }

    private void processComputerMove() {
        if (gameLogic.isGameOver()) {
            return;
        }

        List<int[]> emptyCells = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (gameLogic.getCell(row, column) == '-') {
                    emptyCells.add(new int[]{row, column});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return;
        }

        int[] selectedCell = emptyCells.get(random.nextInt(emptyCells.size()));

        gameLogic.makeMove(selectedCell[0], selectedCell[1]);

        updateBoard();

        if (gameLogic.isGameOver()) {
            finishGame();
        } else {
            statusLabel.setText("Your turn: X");
        }
    }

    private void updateBoard() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                char cellValue = gameLogic.getCell(row, column);
                JButton button = boardButtons[row][column];

                if (cellValue == '-') {
                    button.setText("");
                    button.setForeground(Color.BLACK);
                    button.setBackground(BUTTON_COLOR);

                } else if (cellValue == 'X') {
                    button.setText("X");
                    button.setForeground(X_COLOR);
                    button.setBackground(PANEL_COLOR);

                } else {
                    button.setText("O");
                    button.setForeground(O_COLOR);
                    button.setBackground(PANEL_COLOR);
                }

                button.setOpaque(true);
                button.setContentAreaFilled(true);
            }
        }
    }

    private void finishGame() {
        final String result;
        final String message;

        if (gameLogic.isDraw()) {
            result = "DRAW";
            message = "It's a Draw.";
        } else {
            char winner = getWinnerSymbol();

            if (winner == 'X') {
                result = "WIN";
                message = "You Win!";
            } else {
                result = "LOSE";
                message = "Computer Wins!";
            }
        }

        playerService.updateStatistics(currentPlayer.getId(), result);

        Player refreshedPlayer = playerService.getPlayerById(currentPlayer.getId());

        if (refreshedPlayer != null) {
            currentPlayer = refreshedPlayer;
            scoreLabel.setText("Score: " + currentPlayer.getScore());
        }

        statusLabel.setText(message);
        setBoardEnabled(false);
        JOptionPane.showMessageDialog(
                this,
                message,
                "Game Result",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private char getWinnerSymbol() {
        for (int row = 0; row < 3; row++) {
            if (gameLogic.getCell(row, 0) != '-'
                    && gameLogic.getCell(row, 0) == gameLogic.getCell(row, 1)
                    && gameLogic.getCell(row, 1) == gameLogic.getCell(row, 2)) {
                return gameLogic.getCell(row, 0);
            }
        }

        for (int column = 0; column < 3; column++) {
            if (gameLogic.getCell(0, column) != '-'
                    && gameLogic.getCell(0, column) == gameLogic.getCell(1, column)
                    && gameLogic.getCell(1, column) == gameLogic.getCell(2, column)) {
                return gameLogic.getCell(0, column);
            }
        }

        if (gameLogic.getCell(0, 0) != '-'
                && gameLogic.getCell(0, 0) == gameLogic.getCell(1, 1)
                && gameLogic.getCell(1, 1) == gameLogic.getCell(2, 2)) {
            return gameLogic.getCell(0, 0);
        }

        if (gameLogic.getCell(0, 2) != '-'
                && gameLogic.getCell(0, 2) == gameLogic.getCell(1, 1)
                && gameLogic.getCell(0, 2) == gameLogic.getCell(2, 0)) {
            return gameLogic.getCell(0, 2);
        }

        return '-';
    }

    private void resetGame() {
        gameLogic.resetGame();
        setBoardEnabled(true);

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                JButton button = boardButtons[row][column];

                button.setText("");
                button.setForeground(Color.BLACK);
                button.setBackground(BUTTON_COLOR);
                button.setOpaque(true);
                button.setContentAreaFilled(true);
            }
        }

        statusLabel.setText("Your turn: X");
    }
    private void setBoardEnabled(boolean enabled) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                boardButtons[row][column].setEnabled(enabled);
            }
        }
    }
}