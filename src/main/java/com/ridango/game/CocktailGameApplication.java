package com.ridango.game;

import com.ridango.game.logic.GameRunner;
import com.ridango.game.repository.GameScoreRepository;
import com.ridango.game.service.CocktailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.util.Scanner;

@SpringBootApplication
public class CocktailGameApplication implements CommandLineRunner {
    public static final Logger logger = LoggerFactory.getLogger(CocktailGameApplication.class);
    @Autowired
    private CocktailService cocktailService;
    @Autowired
    private GameScoreRepository gameScoreRepository;

    public static void main(String[] args) {
        SpringApplication.run(CocktailGameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        GameRunner gameRunner = new GameRunner(cocktailService, gameScoreRepository, scanner);
        boolean continuePlaying = true;

        while (continuePlaying) {
            gameRunner.startNewGame();

            System.out.println("Do you want to play again? (yes/no)");
            String response = scanner.nextLine();
            continuePlaying = response.equalsIgnoreCase("yes");

        }
        logger.info("Thank you for playing! Goodbye.");
        System.exit(0);
    }
}
