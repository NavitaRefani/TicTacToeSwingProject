package tictactoe;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {

    private final Player currentPlayer;

    public MainMenuFrame(Player currentPlayer) {
        this.currentPlayer = currentPlayer;

        setTitle("Tic-Tac-Toe - Main Menu");
        setSize(420, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(201, 225, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 60, 25, 60));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + currentPlayer.getUsername() + "!"
        );
        welcomeLabel.setForeground(new Color(50, 58, 68));
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel(
                "Current score: " + currentPlayer.getScore()
        );
        scoreLabel.setForeground(new Color(50, 58, 68));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startGameButton = new JButton("Start");
        JButton statisticsButton = new JButton("My Statistics");
        JButton topScorersButton = new JButton("Top 5 Scorers");
        JButton logoutButton = new JButton("Log out");

        startGameButton.setBackground(new Color(44, 95, 125));
        startGameButton.setForeground(Color.WHITE);

        statisticsButton.setBackground(new Color(183, 210, 225));
        statisticsButton.setForeground(new Color(50, 58, 68));

        topScorersButton.setBackground(new Color(235, 201, 151));
        topScorersButton.setForeground(new Color(50, 58, 68));

        logoutButton.setBackground(new Color(126, 82, 57));
        logoutButton.setForeground(Color.WHITE);

        JButton[] menuButtons = {
                startGameButton,
                statisticsButton,
                topScorersButton,
                logoutButton
        };

        for (JButton button : menuButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setFocusPainted(false);
        }

        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statisticsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        topScorersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(230, 38);

        startGameButton.setMaximumSize(buttonSize);
        statisticsButton.setMaximumSize(buttonSize);
        topScorersButton.setMaximumSize(buttonSize);
        logoutButton.setMaximumSize(buttonSize);

        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(startGameButton);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(statisticsButton);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(topScorersButton);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(logoutButton);

        startGameButton.addActionListener(event -> {
            dispose();

            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
        });

        statisticsButton.addActionListener(event -> {
            dispose();

            StatisticsFrame statisticsFrame =
                    new StatisticsFrame(currentPlayer);

            statisticsFrame.setVisible(true);
        });

        topScorersButton.addActionListener(event -> {
            dispose();

            TopScorersFrame topScorersFrame =
                    new TopScorersFrame(currentPlayer);

            topScorersFrame.setVisible(true);
        });

        logoutButton.addActionListener(event -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Log Out Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                dispose();

                SwingUtilities.invokeLater(() -> {
                    LoginFrame loginFrame = new LoginFrame();
                    loginFrame.setVisible(true);
                });
            }
        });

        add(mainPanel);
    }
}