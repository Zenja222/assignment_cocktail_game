package com.ridango.game.logic;

import com.ridango.game.model.Cocktail;
import com.ridango.game.repository.GameScoreRepository;
import com.ridango.game.service.CocktailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.ridango.game.CocktailGameApplication.logger;

public class GameRunner {
    private CocktailService cocktailService;
    private GameScoreRepository gameScoreRepository;
    private ScoreManager scoreManager;
    private Scanner scanner;
    private GameDisplay gameDisplay;

    //Constructor to initialize dependencies
    public GameRunner(CocktailService cocktailService, GameScoreRepository gameScoreRepository, Scanner scanner) {
        this.cocktailService = cocktailService;
        this.gameScoreRepository = gameScoreRepository;
        this.scanner = scanner;
        this.gameDisplay = new GameDisplay();
        this.scoreManager = new ScoreManager();
    }

    //Start a new game
    public void startNewGame() {
        List<String> usedCocktails = new ArrayList<>();
        Cocktail cocktail = cocktailService.getRandomCocktail();

        //Ensure that coctkail is never used before
        while (usedCocktails.contains(cocktail.getStrDrink())) {
            cocktail = cocktailService.getRandomCocktail();
        }

        usedCocktails.add(cocktail.getStrDrink());
        GameLogic game = new GameLogic(cocktail, gameScoreRepository);

        logger.info("Welcome to the game. Guess the cocktail!");
        game.showMaxScore();
        gameLoop(game);
        scoreManager.savedPlayerScore(game, scanner);
    }

    //Game loop for processing game logic
    private void gameLoop(GameLogic game) {
        while (!game.isGameOver()) {
            gameDisplay.displayGameStatus(game);

            logger.info("Enter your answer or skip the round (skip): ");
            String guess = scanner.nextLine();

            if (guess.equalsIgnoreCase("skip")) {
                processSkip(game);
            } else {
                processContinue(game, guess);
            }
        }

        logger.info("Game over! Answer was: {}", game.getCurrentCocktail().getStrDrink());
    }

    //Process skipping the current round
    private void processSkip(GameLogic game) {
        game.setAttemptsLeft(game.getAttemptsLeft() - 1);
        game.revealRandomLetter();
        System.out.println("Round is skipped. New letters are opened: " + game.getMaskedName());
        game.provideHint();
        System.out.println("-----------------------------------------------------------------");
    }

    //Process the players guess
    private void processContinue(GameLogic game, String guess) {
        if (game.guess(guess)) {
            logger.info("True! Score: " + game.getScore());
            System.out.println("New cocktail is coming... ");
            resetGame(game);
        } else {
            logger.warn("Wrong :( Attempts left: " + game.getAttemptsLeft());
            game.revealRandomLetter();
            game.provideHint();
            System.out.println("-----------------------------------------------------------------");
        }
    }

    //Reset the game
    private void resetGame(GameLogic game) {
        Cocktail newCocktail = cocktailService.getRandomCocktail();
        game.resetGame(newCocktail);
    }
}

