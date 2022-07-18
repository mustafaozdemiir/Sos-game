package com.yazilimmotoru;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String[][] game; // Game matrix variable
    public static int dimension = 5; // Game matrix size variable
    public static int playerScore = 0; // Player score variable
    public static int computerScore = 0; // Computer score variable
    public static int totalSosScore = 0; // Variable for the total amount of sauce in the game
    public static boolean playerLetter = false; //player selection variable
    public static boolean isEnd = false; // Endgame control variable

    public static void startGameStartMatrix(int dimension) { // The game matrix is created according to the size taken from the player.
        game = new String[dimension][dimension];
        for (int line = 0; line < dimension; line++) {
            for (int column = 0; column < dimension; column++) {
                game[line][column] = "?";
            }
        }
        System.out.println("Game is starting...");
        System.out.println("Game has started!");
        printPositions(dimension, game);

    }

    public static void printPositions(int dimension, String[][] game) { // The final state of the game matrix is printed.
        for (int i = 1; i <= dimension; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");

        for (int line = 0; line < dimension; line++) {
            for (int column = 0; column < dimension; column++) {
                System.out.print(game[line][column] + " ");
            }
            System.out.println(" " + (line + 1));
        }
    }

    public static void checkScore(int size, String[][] game, boolean player) { // SOS control is done horizontally, vertically and diagonally. Points are added to the winning player.
        int verticalScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (game[i][j] == "S" && game[i][j + 1] == "O" && game[i][j + 2] == "S") {
                    verticalScore++;
                }
            }
        }// vertical

        int horizantalScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (game[j][i] == "S" && game[j + 1][i] == "O" && game[j + 2][i] == "S") {
                    horizantalScore++;
                }
            }
        }//Horizantal

        int diagonalScore = 0;
        for (int i = 0; i < size - 2; i++) {// soldan sağa
            for (int j = 0; j < size - 2; j++) {
                if (game[i][j] == "S" && game[i + 1][j + 1] == "O" && game[i + 2][j + 2] == "S") {
                    diagonalScore++;
                }
            }
        }

        for (int i = 0; i < size - 2; i++) {// sağdan sola
            for (int j = size - 1; j > 1; j--) {
                if (game[i][j] == "S" && game[i + 1][j - 1] == "O" && game[i + 2][j - 2] == "S") {
                    diagonalScore++;
                }
            }
        } //diagonal
        int totalScore = verticalScore + horizantalScore + diagonalScore;
        if (totalSosScore != totalScore) {
            int score = totalScore - totalSosScore;
            addScore(player, score);
        }
    }

    public static void addScore(boolean player, int score) { // Points are added to the winning player. He makes extra moves.
        System.out.println("Congratulations, " + score + "  point earned.");
        totalSosScore += score;
        if (player == playerLetter) {
            playerScore += score;
            printScore();
            isEnd();
            if (!isEnd) {
                inputMoveBylineAndColumn();
            }
        } else {
            computerScore += score;
            printScore();
            isEnd();
            if (!isEnd) {
                randomMove(!playerLetter);
            }
        }
    }

    public static void printScore() { // The final standings are printed.
        System.out.println("Player score: " + playerScore);
        System.out.println("Computer score: " + computerScore);
    }

    public static void setDimension() { // The game size is taken from the user. If it is not between 3 and 7, it is requested from the user again.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the game size.");
        dimension = scanner.nextInt();
        if (dimension < 3 || dimension > 7) {
            System.out.println("Must be between 3 and 7");
            setDimension();
        }
    }

    public static void startInputs() { // The game size is taken from the user. It is determined which letter the random user will receive.
        setDimension();
        Random random = new Random();
        playerLetter = random.nextBoolean();
    }

    public static void checkSOS(boolean player) { // SOS control is in progress.
        checkScore(dimension, game, player);
    }

    public static void play(boolean player, int line, int column, int dimension) { // The move is being made. If the field is not empty, a warning is given and a value is requested again. When a move is made, the leaderboard and the game's final state matrix are printed.
        if (line <= dimension && column <= dimension && game[line - 1][column - 1].equals("?")) {
            game[line - 1][column - 1] = player ? "S" : "O";
            printPositions(dimension, game);
            printScore();
            checkSOS(player);
        } else {
            System.out.println("Invalid Move Choose again!");
            inputMoveBylineAndColumn();
        }
    }

    public static void randomMove(boolean player) { // The computer makes random moves. If the move field is not empty, it tries again.
        Random random = new Random();
        int lineNumber = random.nextInt(dimension);
        int columnNumber = random.nextInt(dimension);
        if (game[lineNumber][columnNumber].equals("?")) {
            System.out.println("------------");
            System.out.println("Computer [" + (!playerLetter ? "S" : "O") + "]");
            play(player, lineNumber + 1, columnNumber + 1, dimension);
        } else {
            randomMove(player);
        }
    }

    public static void inputMoveBylineAndColumn() { // Getting row and column information for the move from the user. If the field is not empty, the request is made again.
        System.out.println("------------");
        System.out.println("Player [" + (playerLetter ? "S" : "O") + "]");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the line");
        int line = scanner.nextInt();
        System.out.println("Enter the column");
        int column = scanner.nextInt();
        play(playerLetter, line, column, dimension);
    }

    public static boolean isEnd() { // Determining whether there are any remaining moves in the game matrix.
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (game[i][j] == "?") {
                    isEnd = false;
                    return false;
                }
            }
        }
        isEnd = true;
        return true;
    }

    public static void checkWin() { // When the game is over, the scores are checked and the winner is determined.
        System.out.println("Game is end");
        if (playerScore > computerScore) {
            System.out.println("Player win...");
        } else if (playerScore == computerScore) {
            System.out.println("Game draw...");
        } else {
            System.out.println("Computer win...");
        }
    }

    public static void main(String[] args) {
        startInputs(); // The game size is taken from the user. It is determined which letter the random user will receive.
        startGameStartMatrix(dimension); // The game matrix is created according to the size taken from the player.
        while (!isEnd) { // It checks whether there are any remaining moves in the game matrix and performs moves in order.
            isEnd();
            inputMoveBylineAndColumn();
            isEnd();
            if (!isEnd) {
                randomMove(!playerLetter);
            }
        }
        checkWin(); // When the game is over, the scores are checked and the winner is determined.

    }
}