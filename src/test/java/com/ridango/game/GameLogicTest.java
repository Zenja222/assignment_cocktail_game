
package com.ridango.game;

import com.ridango.game.logic.GameLogic;
import com.ridango.game.model.Cocktail;
import com.ridango.game.repository.GameScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameLogicTest {

    @Mock
    private GameScoreRepository gameScoreRepository;

    private GameLogic gameLogic;
    private Cocktail cocktail;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cocktail = new Cocktail();
        cocktail.setStrDrink("Mojito");
        gameLogic = new GameLogic(cocktail, gameScoreRepository);
    }

    // Test game initialization
    @Test
    void testInitialization() {
        assertEquals(5, gameLogic.getAttemptsLeft());
        assertEquals("Mojito", gameLogic.getCurrentCocktail().getStrDrink());
        assertEquals("_ _ _ _ _ _", gameLogic.getMaskedName());
    }

    // Test guessing the correct cocktail name
    @Test
    void testCorrectGuess() {
        assertTrue(gameLogic.guess("Mojito"));
        assertEquals(5, gameLogic.getScore());
        assertFalse(gameLogic.isGameOver());
    }

    // Test guessing an incorrect cocktail name
    @Test
    void testIncorrectGuess() {
        assertFalse(gameLogic.guess("Margarita"));
        assertEquals(4, gameLogic.getAttemptsLeft());
        assertEquals(0, gameLogic.getScore());
    }

    // Test game over when attempts run out
    @Test
    void testGameOver() {
        gameLogic.setAttemptsLeft(0);
        assertTrue(gameLogic.isGameOver());
    }

    // Test random letter reveal
    @Test
    void testRevealRandomLetter() {
        String initialMaskedName = gameLogic.getMaskedName();
        gameLogic.revealRandomLetter();
        String newMaskedName = gameLogic.getMaskedName();
        assertNotEquals(initialMaskedName, newMaskedName);
    }

    // Test score saving
    @Test
    void testSaveResult() {
        gameLogic.guess("Mojito");
        gameLogic.saveResult("Player1");

        verify(gameScoreRepository, times(1)).save(any());
    }
}
