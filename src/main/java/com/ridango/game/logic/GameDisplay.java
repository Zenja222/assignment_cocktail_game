package com.ridango.game.logic;

public class GameDisplay {
    //Displays needed information in the console
    public void displayGameStatus(GameLogic game) {
        System.out.println("Instruction: " + game.getCurrentCocktailInstructions());
        System.out.println("Cocktail name: " + game.getMaskedName());
        System.out.println("Number of letters: " + game.getCurrentCocktail().getStrDrink().length());
        System.out.println("Attempts left: " + game.getAttemptsLeft());
    }
}
