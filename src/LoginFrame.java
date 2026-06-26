package tictactoe;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final PlayerService playerService;

    public LoginFrame() {
        playerService = new PlayerService();

        setTitle("Tic-Tac-Toe - Login");
        setSize(400, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(201, 225, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 8, 8, 8);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("TIC-TAC-TOE");
        titleLabel.setForeground(new Color(50, 58, 68));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Please log in to play");
        subtitleLabel.setForeground(new Color(50, 58, 68));
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameLabel.setForeground(new Color(50, 58, 68));
        passwordLabel.setForeground(new Color(50, 58, 68));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        usernameField.setBackground(Color.WHITE);
        passwordField.setBackground(Color.WHITE);

        usernameField.setForeground(new Color(50, 58, 68));
        passwordField.setForeground(new Color(50, 58, 68));

        usernameField.setBorder(
                BorderFactory.createLineBorder(new Color(183, 210, 225), 2, true)
        );

        passwordField.setBorder(
                BorderFactory.createLineBorder(new Color(183, 210, 225), 2, true)
        );

        JButton loginButton = new JButton("Login");

        loginButton.setBackground(new Color(44, 95, 125));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setFocusPainted(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        mainPanel.add(titleLabel, constraints);

        constraints.gridy = 1;
        mainPanel.add(subtitleLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(usernameLabel, constraints);

        constraints.gridx = 1;
        mainPanel.add(usernameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        mainPanel.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        mainPanel.add(loginButton, constraints);

        loginButton.addActionListener(event -> processLogin());
        passwordField.addActionListener(event -> processLogin());

        add(mainPanel);
    }

    private void processLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Username and password must be filled.",
                    "Incomplete input",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Player player = playerService.login(username, password);

        if (player != null) {
            UIManager.put("OptionPane.background", new Color(201, 225, 240));
            UIManager.put("Panel.background", new Color(201, 225, 240));
            UIManager.put("OptionPane.messageForeground", new Color(50, 58, 68));

            UIManager.put("Button.background", new Color(44, 95, 125));
            UIManager.put("Button.foreground", Color.WHITE);

            JOptionPane.showMessageDialog(
                    this,
                    "Log in successful. Welcome, " + player.getUsername() + "!",
                    "Log in Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            MainMenuFrame mainMenuFrame = new MainMenuFrame(player);
            mainMenuFrame.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Username or password is incorrect.",
                    "Log in Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            passwordField.setText("");
        }
    }
}