package tictactoe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TopScorersFrame extends JFrame {

    private final Player currentPlayer;
    private final PlayerService playerService;

    public TopScorersFrame(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        this.playerService = new PlayerService();

        setTitle("Tic-Tac-Toe - Top 5 Scorers");
        setSize(620, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        mainPanel.setBackground(new Color(201, 225, 240));

        JLabel titleLabel = new JLabel("TOP 5 SCORERS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(50, 58, 68));

        String[] columnNames = {
                "Rank",
                "Username",
                "Wins",
                "Losses",
                "Draws",
                "Total Score"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable scorersTable = new JTable(tableModel);
        scorersTable.setRowHeight(28);
        scorersTable.setFont(new Font("Arial", Font.PLAIN, 14));
        scorersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        scorersTable.setBackground(new Color(255, 248, 235));
        scorersTable.setForeground(new Color(50, 58, 68));
        scorersTable.setGridColor(new Color(183, 210, 225));

        scorersTable.getTableHeader().setBackground(new Color(44, 95, 125));
        scorersTable.getTableHeader().setForeground(Color.WHITE);
        scorersTable.getTableHeader().setOpaque(true);

        List<Player> topPlayers = playerService.getTopFiveScorers();

        int rank = 1;

        for (Player player : topPlayers) {
            tableModel.addRow(new Object[]{
                    rank,
                    player.getUsername(),
                    player.getWins(),
                    player.getLosses(),
                    player.getDraws(),
                    player.getScore()
            });

            rank++;
        }

        JScrollPane scrollPane = new JScrollPane(scorersTable);
        scrollPane.getViewport().setBackground(new Color(201, 225, 240));
        scrollPane.setBorder(
                BorderFactory.createLineBorder(new Color(201, 225, 240), 2)
        );

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(201, 225, 240));

        JButton refreshButton = new JButton("Refresh");
        JButton backButton = new JButton("Back to menu");

        refreshButton.setBackground(new Color(44, 95, 125));
        refreshButton.setForeground(Color.WHITE);

        backButton.setBackground(new Color(126, 82, 57));
        backButton.setForeground(Color.WHITE);

        JButton[] actionButtons = {refreshButton, backButton};

        for (JButton button : actionButtons) {
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setFocusPainted(false);
        }

        bottomPanel.add(refreshButton);
        bottomPanel.add(backButton);

        refreshButton.addActionListener(event -> {
            tableModel.setRowCount(0);

            List<Player> refreshedTopPlayers =
                    playerService.getTopFiveScorers();

            int refreshedRank = 1;

            for (Player player : refreshedTopPlayers) {
                tableModel.addRow(new Object[]{
                        refreshedRank,
                        player.getUsername(),
                        player.getWins(),
                        player.getLosses(),
                        player.getDraws(),
                        player.getScore()
                });

                refreshedRank++;
            }
        });

        backButton.addActionListener(event -> {
            dispose();

            Player playerFromDatabase =
                    playerService.getPlayerById(this.currentPlayer.getId());

            if (playerFromDatabase != null) {
                MainMenuFrame mainMenuFrame =
                        new MainMenuFrame(playerFromDatabase);

                mainMenuFrame.setVisible(true);
            } else {
                MainMenuFrame mainMenuFrame =
                        new MainMenuFrame(this.currentPlayer);

                mainMenuFrame.setVisible(true);
            }
        });

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }
}