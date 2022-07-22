package com.yazilimmotoru;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static String[][] game; // Game matrix variable
    public static int dimension = 5; // Game matrix size variable
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
                if (game[i][j].equals("S") && game[i][j + 1].equals("O") && game[i][j + 2].equals("S")) {
                    verticalScore++;
                }
            }
        }// vertical

        int horizantalScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 2; j++) {
                if (game[j][i].equals("S") && game[j + 1][i].equals("O") && game[j + 2][i].equals("S")) {
                    horizantalScore++;
                }
            }
        }//Horizantal

        int diagonalScore = 0;
        for (int i = 0; i < size - 2; i++) {// soldan sağa
            for (int j = 0; j < size - 2; j++) {
                if (game[i][j].equals("S") && game[i + 1][j + 1].equals("O") && game[i + 2][j + 2].equals("S")) {
                    diagonalScore++;
                }
            }
        }

        for (int i = 0; i < size - 2; i++) {// sağdan sola
            for (int j = size - 1; j > 1; j--) {
                if (game[i][j].equals("S") && game[i + 1][j - 1].equals("O") && game[i + 2][j - 2].equals("S")) {
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
            Player.playerScore += score;
            printScore();
            isEnd();
            if (!isEnd) {
                Player.inputMoveBylineAndColumn();
            }
        } else {
            Computer.computerScore += score;
            printScore();
            isEnd();
            if (!isEnd) {
                Computer.randomMove(!playerLetter);
            }
        }
    }

    public static void printScore() { // The final standings are printed.
        System.out.println("Player score: " + Player.playerScore);
        System.out.println("Computer score: " + Computer.computerScore);
    }

    public static void setDimension() { // The game size is taken from the user. If it is not between 3 and 7, it is requested from the user again.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the game size.");
        try {
            dimension = scanner.nextInt();
            if (dimension < 3 || dimension > 7) {
                System.out.println("The game size must be between 3 and 7.");
                setDimension();
            }
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Please enter a number.");
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
        if (line > 0 && column > 0 && line <= dimension && column <= dimension && game[line - 1][column - 1].equals("?")) {
            game[line - 1][column - 1] = player ? "S" : "O";
            printPositions(dimension, game);
            printScore();
            checkSOS(player);
        } else {
            System.out.println("Invalid Move Choose again!");
            Player.inputMoveBylineAndColumn();
        }
    }

    public static boolean isEnd() { // Determining whether there are any remaining moves in the game matrix.
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (game[i][j].equals("?")) {
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
        if (Player.playerScore > Computer.computerScore) {
            System.out.println("Player win...");
        } else if (Player.playerScore == Computer.computerScore) {
            System.out.println("Game draw...");
        } else {
            System.out.println("Computer win...");
        }
    }

}
