package com.ridango.game.logic;

import java.util.Scanner;

import static com.ridango.game.CocktailGameApplication.logger;

public class ScoreManager {
    //Saves the players score
    public void savedPlayerScore(GameLogic game, Scanner scanner) {
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine().trim();

        if (!playerName.isEmpty()) {
            game.saveResult(playerName);
        } else {
            logger.warn("Player name is empty. Score not saved.");
        }
    }
}
