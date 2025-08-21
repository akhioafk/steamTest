package pages;

import aquality.selenium.forms.Form;
import models.Game;
import org.openqa.selenium.By;

public class GamePage extends Form {

    private static final By TITLE = By.id("appHubAppName");
    private static final By ORIGINAL_PRICE = By.xpath("//div[contains(@class,'discount_original_price')]");
    private static final By FINAL_PRICE = By.xpath("//div[contains(@class,'discount_final_price')]");
    private static final By DISCOUNT = By.xpath("//div[contains(@class,'discount_pct')]");

    public GamePage() {
        super(TITLE, "Game Page");
    }

    public Game getGame() {
        String title = getElementFactory().getLabel(TITLE, "Title").getText();
        String originalPrice = getElementFactory().getLabel(ORIGINAL_PRICE, "Original Price").getText();
        String finalPrice = getElementFactory().getLabel(FINAL_PRICE, "Final Price").getText();
        int discount = Integer.parseInt(
                getElementFactory().getLabel(DISCOUNT, "Discount").getText().replaceAll("[^0-9]", "")
        );

        return new Game(title, originalPrice, finalPrice, discount);
    }
}
