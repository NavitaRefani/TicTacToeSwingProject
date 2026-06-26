# Simple Tic-Tac-Toe Game with Java Swing, Login, and Statistics

## Student Information
Name: NAVITA FITRIANTI REFANI
Student ID: 5026251041
Class: A

## Project Description
This project is a simple Tic-Tac-Toe game built using Java Swing.
The application includes login, game statistics, and Top 5 scorer
feature.

## Features
- Player login using PostgreSQL database
- Tic-Tac-Toe game using Java Swing GUI
- Player plays as **X**
- Computer plays as **O**
- Automatic game result detection:
  - Win
  - Loss
  - Draw
- Automatic update of wins, losses, draws, and score
- Personal player statistics page
- Top 5 players leaderboard using `JTable`
- Refresh leaderboard data
- Blue, cream, and brown user interface theme
- PostgreSQL database connection using JDBC
  
## Database
Database used: PostgreSQL

## How to Run
1. Create the database.
2. Import schema.sql.
3. Open the Java project.
4. Add JDBC driver.
5. Configure DatabaseManager.java.
6. Run Main.java.
   
## Class Explanation
Main: 
- The main class of the application. It starts the program by opening the login window.
DatabaseManager:
- Manages the connection between the Java application and the PostgreSQL database using JDBC.
Player:
- Represents player data, including ID, username, password, wins, losses, draws, and score.
PlayerService:
- Handles database operations related to players, including login validation, retrieving player data, updating game statistics, and retrieving the Top 5 players.
GameLogic:
- Handles the Tic-Tac-Toe game logic, including the game board, player moves, computer moves, win detection, draw detection, and game reset.
LoginFrame:
- Provides the login interface. It validates the username and password using PlayerService before opening the main menu.
MainMenuFrame:
- Displays the main menu after a successful login. It provides navigation to start a game, view personal statistics, view the Top 5 players, or log out.
GameFrame:
- Displays the Tic-Tac-Toe game board. The player uses X, while the computer uses O. This class updates the game result and player statistics after each completed game.
StatisticsFrame:
- Displays the logged-in player's personal statistics, including wins, losses, draws, score, and win rate.
TopScorersFrame:
- Displays the Top 5 players based on score using JTable. It also provides a refresh button to reload the latest leaderboard data.
  
## Screenshots
Add screenshots here.

## Video Link
YouTube:
