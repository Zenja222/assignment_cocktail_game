package com.ridango.game.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//Wrapper class
public class CocktailResponse {
    private List<Cocktail> drinks;
}
