package tictactoe;

import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {

    private Player currentPlayer;
    private final PlayerService playerService;

    public StatisticsFrame(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.playerService = new PlayerService();

        setTitle("Tic-Tac-Toe - My Statistics");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Player refreshedPlayer =
                playerService.getPlayerById(currentPlayer.getId());

        if (refreshedPlayer != null) {
            this.currentPlayer = refreshedPlayer;
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(201, 225, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("MY STATISTICS");
        titleLabel.setForeground(new Color(50, 58, 68));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int totalGames = currentPlayer.getWins()
                + currentPlayer.getLosses()
                + currentPlayer.getDraws();

        double winRate = 0;

        if (totalGames > 0) {
            winRate = (currentPlayer.getWins() * 100.0) / totalGames;
        }

        JLabel usernameLabel = new JLabel(
                "Username: " + currentPlayer.getUsername()
        );

        JLabel winsLabel = new JLabel(
                "Wins: " + currentPlayer.getWins()
        );

        JLabel lossesLabel = new JLabel(
                "Losses: " + currentPlayer.getLosses()
        );

        JLabel drawsLabel = new JLabel(
                "Draws: " + currentPlayer.getDraws()
        );

        JLabel scoreLabel = new JLabel(
                "Total Score: " + currentPlayer.getScore()
        );

        JLabel winRateLabel = new JLabel(
                String.format("Win Rate: %.2f%%", winRate)
        );

        usernameLabel.setForeground(new Color(50, 58, 68));
        winsLabel.setForeground(new Color(50, 58, 68));
        lossesLabel.setForeground(new Color(50, 58, 68));
        drawsLabel.setForeground(new Color(50, 58, 68));
        scoreLabel.setForeground(new Color(50, 58, 68));
        winRateLabel.setForeground(new Color(50, 58, 68));

        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lossesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        drawsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        winRateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backButton = new JButton("Back to menu");
        backButton.setBackground(new Color(126, 82, 57));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 38));

        backButton.addActionListener(event -> {
            dispose();

            MainMenuFrame mainMenuFrame =
                    new MainMenuFrame(this.currentPlayer);

            mainMenuFrame.setVisible(true);
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(usernameLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(winsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lossesLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(drawsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(winRateLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(backButton);

        add(mainPanel);
    }
}