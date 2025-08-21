package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import models.Game;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.StringUtils;
import utils.WaitUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TopSellersPage extends Form {

    private static final String TOP_SELLERS_SECTION = "//div[contains(@class,'TopSellers') or contains(text(),'Top Sellers')]";
    private static final String GAME_CONTAINER = "//div[contains(@class, 'ImpressionTrackedElement')]";
    private static final String GAME_TITLE_BY_INDEX = "(//div[contains(@class, 'ImpressionTrackedElement')])[%d]//div[contains(@class, 'StoreSaleWidgetTitle')]";
    private static final String GAME_DISCOUNT_BY_INDEX = "(//div[contains(@class, 'ImpressionTrackedElement')])[%d]//div[contains(@class, 'StoreSalePriceWidgetContainer') and contains(@class, 'Discounted')]";
    private static final String GAME_LINK_BY_TITLE = "//div[contains(@class,'StoreSaleWidgetTitle') and text()='%s']/ancestor::a";
    private static final String GAME_LINK_BY_CONTAINS_TITLE = "//div[contains(@class,'StoreSaleWidgetTitle') and contains(text(),'%s')]/ancestor::a";
    private static final String ARIA_LABEL_ATTRIBUTE = "aria-label";

    private static final Pattern DISCOUNT_PATTERN = Pattern.compile(
            "(\\d+)%\\s+off\\.\\s+([\\d\\s,]+(?:\\.\\d+)?).*?normally,\\s+discounted\\s+to\\s+([\\d\\s,]+(?:\\.\\d+)?)"
    );

    public TopSellersPage() {
        super(By.xpath(TOP_SELLERS_SECTION), "Top Sellers Section");
    }

    private List<Game> getAllDiscountedGames() {
        List<ILabel> gameContainers = getElementFactory().findElements(
                By.xpath(GAME_CONTAINER), ILabel.class
        );

        List<Game> discountedGames = new ArrayList<>();

        for (int i = 0; i < gameContainers.size(); i++) {
            try {
                List<ILabel> titles = getElementFactory().findElements(
                        By.xpath(String.format(GAME_TITLE_BY_INDEX, i + 1)), ILabel.class
                );

                List<ILabel> discountElements = getElementFactory().findElements(
                        By.xpath(String.format(GAME_DISCOUNT_BY_INDEX, i + 1)), ILabel.class
                );

                if (!titles.isEmpty() && !discountElements.isEmpty()) {
                    String title = titles.get(0).getText();
                    String ariaLabel = discountElements.get(0).getAttribute(ARIA_LABEL_ATTRIBUTE);

                    Game game = parseDiscountFromAriaLabel(title, ariaLabel);
                    if (game != null) {
                        discountedGames.add(game);
                    }
                }
            } catch (Exception ignored) {
            }
        }

        return discountedGames;
    }

    public Game getMaxDiscountedGame() {
        waitUntilGamesLoaded();
        List<Game> discountedGames = getAllDiscountedGames();

        if (discountedGames.isEmpty()) {
            throw new RuntimeException("No discounted games found");
        }

        return discountedGames.stream()
                .max(Comparator.comparingInt(Game::getDiscountPercentage))
                .orElse(discountedGames.get(0));
    }

    private Game parseDiscountFromAriaLabel(String title, String ariaLabel) {
        try {
            Matcher matcher = DISCOUNT_PATTERN.matcher(ariaLabel);

            if (matcher.find()) {
                int discountPercentage = Integer.parseInt(matcher.group(1));
                String originalPrice = StringUtils.normalizePrice(matcher.group(2));
                String discountedPrice = StringUtils.normalizePrice(matcher.group(3));

                return new Game(title, originalPrice, discountedPrice, discountPercentage);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void selectGame(Game game) {
        String originalWindow = getDriver().getWindowHandle();

        String xpath = String.format(GAME_LINK_BY_TITLE, game.getTitle());

        try {
            getElementFactory().getLabel(By.xpath(xpath), "Game: " + game.getTitle()).click();
        } catch (Exception e) {
            String fallbackXpath = String.format(GAME_LINK_BY_CONTAINS_TITLE, game.getTitle());
            getElementFactory().getLabel(By.xpath(fallbackXpath), "Game: " + game.getTitle()).click();
        }

        waitForNewTabAndSwitch(originalWindow);
    }

    private void waitForNewTabAndSwitch(String originalWindow) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = getDriver().getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                getDriver().switchTo().window(windowHandle);
                break;
            }
        }
    }

    private void waitUntilGamesLoaded() {
        WaitUtils.waitUntilElementPresent(
                getDriver(),
                By.xpath(GAME_CONTAINER),
                15,
                1
        );
    }

    private WebDriver getDriver() {
        return AqualityServices.getBrowser().getDriver();
    }
}
