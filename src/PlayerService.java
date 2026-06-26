package tictactoe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {

    public Player login(String username, String password) {
        String sql = """
                SELECT id, username, wins, losses, draws, score
                FROM players
                WHERE username = ? AND password = ?
                """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createPlayerFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Login database error: " + e.getMessage());
        }

        return null;
    }

    public Player getPlayerById(int playerId) {
        String sql = """
                SELECT id, username, wins, losses, draws, score
                FROM players
                WHERE id = ?
                """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return createPlayerFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("Get player database error: " + e.getMessage());
        }

        return null;
    }

    public void updateStatistics(int playerId, String result) {
        String sql;
        int additionalScore;

        switch (result.toUpperCase()) {
            case "WIN" -> {
                sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
                additionalScore = 10;
            }
            case "LOSE" -> {
                sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
                additionalScore = 0;
            }
            case "DRAW" -> {
                sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
                additionalScore = 3;
            }
            default -> throw new IllegalArgumentException("Result harus WIN, LOSE, atau DRAW.");
        }

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, additionalScore);
            statement.setInt(2, playerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update statistics database error: " + e.getMessage());
        }
    }

    public List<Player> getTopFiveScorers() {
        List<Player> players = new ArrayList<>();

        String sql = """
                SELECT id, username, wins, losses, draws, score
                FROM players
                ORDER BY score DESC, wins DESC, username ASC
                LIMIT 5
                """;

        try (
                Connection connection = DatabaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                players.add(createPlayerFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Top 5 database error: " + e.getMessage());
        }

        return players;
    }

    private Player createPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        return new Player(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getInt("wins"),
                resultSet.getInt("losses"),
                resultSet.getInt("draws"),
                resultSet.getInt("score")
        );
    }
}