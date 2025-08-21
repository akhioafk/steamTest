package models;

import lombok.Getter;

@Getter
public class Game {
    private final String title;
    private final String originalPrice;
    private final String discountedPrice;
    private final int discountPercentage;

    public Game(String title, String originalPrice, String discountedPrice, int discountPercentage) {
        this.title = title;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.discountPercentage = discountPercentage;
    }
}
