package com.ridango.game.logic;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.GameScore;
import com.ridango.game.repository.GameScoreRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    @Getter
    private int score;
    @Getter
    @Setter
    private int attemptsLeft = 5;
    @Getter
    private Cocktail currentCocktail;
    @Getter
    private String maskedName;
    private List<String> hints;
    private int currentHintIndex;

    private GameScoreRepository gameScoreRepository;

    //Constructor. Initializes the game
    public GameLogic(Cocktail cocktail, GameScoreRepository gameScoreRepository) {
        this.currentCocktail = cocktail;
        this.score = 0;
        this.maskedName = createMaskedName(cocktail.getStrDrink());
        this.hints = generateHints();
        this.currentHintIndex = 0;
        this.gameScoreRepository = gameScoreRepository;
    }

    //Reset the game with a new cocktail
    public void resetGame(Cocktail newCocktail) {
        this.currentCocktail = newCocktail;
        this.maskedName = createMaskedName(newCocktail.getStrDrink());
        this.attemptsLeft = 5;
        this.hints = generateHints();
        this.currentHintIndex = 0;
    }

    //Checks if the players guess is correct
    public boolean guess(String playerGuess) {
        //Checks that the guess is not empty or invalid
        if (playerGuess == null || playerGuess.isBlank()) {
            System.out.println("Invalid input!");
            return false;
        }
        //If the guess is correct, add score
        if (playerGuess.equalsIgnoreCase(currentCocktail.getStrDrink())) {
            score += attemptsLeft;
            return true;
        } else {
            attemptsLeft--;
            return false;
        }
    }

    //Reveals random letter
    public void revealRandomLetter() {
        List<Integer> hiddenIndexes = getHiddenIndexes();

        if (!hiddenIndexes.isEmpty()) {
            int indexToReveal = new Random().nextInt(hiddenIndexes.size());
            revealLetterAt(hiddenIndexes.get(indexToReveal));
        }
    }

    //Finds indexes of hidden letters
    private List<Integer> getHiddenIndexes() {
        List<Integer> hiddenIndexes = new ArrayList<>();
        for (int i = 0; i < currentCocktail.getStrDrink().length(); i++) {
            if (maskedName.charAt(i * 2) == '_') {
                hiddenIndexes.add(i);
            }
        }
        return hiddenIndexes;
    }

    //Reveals the letter at a given index
    private void revealLetterAt(int index) {
        StringBuilder maskedBuilder = new StringBuilder(maskedName);
        maskedBuilder.setCharAt(index * 2, currentCocktail.getStrDrink().charAt(index));
        maskedName = maskedBuilder.toString();
    }

    //Generates a list of hints
    private List<String> generateHints() {
        List<String> hintsList = new ArrayList<>();
        hintsList.add("Cocktail category: " + getCocktailCategory());
        hintsList.add("Cocktail glass: " + getCocktailGlass());
        hintsList.add("Cocktail ingredient 1: " + getCocktailIngredient1());
        hintsList.add("Cocktail ingredient 2: " + getCocktailIngredient2());
        return hintsList;
    }

    //Provides the next hint
    public void provideHint() {
        if (currentHintIndex < hints.size()) {
            System.out.println(hints.get(currentHintIndex));
            currentHintIndex++;
        } else {
            System.out.println("No more hints available.");
        }
    }

    //Saves result to the repository
    public void saveResult(String playerName) {
        try {
            GameScore result = new GameScore();
            result.setPlayerName(playerName);
            result.setScore(score);
            gameScoreRepository.save(result);
            System.out.println("Score saved for " + playerName + ": " + score);
        } catch (Exception e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    //Displays maximum score
    public void showMaxScore() {
        Integer maxScore = gameScoreRepository.findMaxScore();
        if (maxScore != null) {
            System.out.println("Current highest score: " + maxScore);
        } else {
            System.out.println("-------------------------------------------\n" +
                    "No high score avaliable yet.\n" +
                    "-------------------------------------------\n");
        }
    }

    //Creates a masked version of the cocktail
    private String createMaskedName(String name) {
        return name.replaceAll(".", "_ ").trim();
    }

    //Checks if the game is over
    public boolean isGameOver() {
        return attemptsLeft <= 0;
    }

    //Provides current cocktail instructions
    public String getCurrentCocktailInstructions() {
        return currentCocktail.getStrInstructions();
    }

    //Provides current cocktail category
    public String getCocktailCategory() {
        return currentCocktail.getStrCategory();
    }

    //Provides current cocktail glass
    public String getCocktailGlass() {
        return currentCocktail.getStrGlass();
    }

    //Provides current cocktail ingredient1
    public String getCocktailIngredient1() {
        return currentCocktail.getStrIngredient1();
    }

    //Provides current cocktail ingredient2
    public String getCocktailIngredient2() {
        return currentCocktail.getStrIngredient2();
    }
}
