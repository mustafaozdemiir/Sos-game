package com.yazilimmotoru;


import static com.yazilimmotoru.Game.startGameStartMatrix;
import static com.yazilimmotoru.Game.startInputs;

public class Main {

    public static void main(String[] args) {
        startInputs(); // The game size is taken from the user. It is determined which letter the random user will receive.
        startGameStartMatrix(Game.dimension); // The game matrix is created according to the size taken from the player.
        while (!Game.isEnd) { // It checks whether there are any remaining moves in the game matrix and performs moves in order.
            Game.isEnd();
            Player.inputMoveBylineAndColumn();
            Game.isEnd();
            if (!Game.isEnd) {
                Computer.randomMove(!Game.playerLetter);
            }
        }
        Game.checkWin(); // When the game is over, the scores are checked and the winner is determined.

    }
}