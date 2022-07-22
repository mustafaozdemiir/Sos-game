package com.yazilimmotoru;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {

    public static int playerScore = 0; // Player score variable

    public static void inputMoveBylineAndColumn() { // Getting row and column information for the move from the user. If the field is not empty, the request is made again.

       try {
           if (!Game.isEnd) {
               System.out.println("------------");
               System.out.println("Player [" + (Game.playerLetter ? "S" : "O") + "]");
               Scanner scanner = new Scanner(System.in);
               System.out.println("Enter the line");
               int line = scanner.nextInt();
               System.out.println("Enter the column");
               int column = scanner.nextInt();
               Game.play(Game.playerLetter, line, column, Game.dimension);
           }} catch (InputMismatchException inputMismatchException) {
           System.out.println("Please enter a number.");
           inputMoveBylineAndColumn();
       }

    }
}
