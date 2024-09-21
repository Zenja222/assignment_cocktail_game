package com.ridango.game.model;

import lombok.Data;

@Data
//Data model that represents the structure of a cocktail
public class Cocktail {
    private String strDrink;
    private String strInstructions;
    private String strGlass;
    private String strIngredient1;
    private String strIngredient2;
    private String strCategory;

}
