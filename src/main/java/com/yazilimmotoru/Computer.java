package com.yazilimmotoru;

import java.util.Random;

public class Computer {

    public static int computerScore = 0; // Computer score variable

    public static void randomMove(boolean player) { // The computer makes random moves. If the move field is not empty, it tries again.
        Random random = new Random();
        int lineNumber = random.nextInt(Game.dimension);
        int columnNumber = random.nextInt(Game.dimension);
        if (Game.game[lineNumber][columnNumber].equals("?")) {
            System.out.println("------------");
            System.out.println("Computer [" + (!Game.playerLetter ? "S" : "O") + "]");
            Game.play(player, lineNumber + 1, columnNumber + 1, Game.dimension);
        } else {
            randomMove(player);
        }
    }

}
