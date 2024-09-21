package com.ridango.game.service;

import com.ridango.game.model.Cocktail;
import com.ridango.game.model.CocktailResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

//Service for fetching cocktail data from an API
@Service
public class CocktailService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiUrl = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

    public Cocktail getRandomCocktail() {
        ResponseEntity<CocktailResponse> response = restTemplate.getForEntity(apiUrl, CocktailResponse.class);
        return Objects.requireNonNull(response.getBody()).getDrinks().get(0);
    }
}

