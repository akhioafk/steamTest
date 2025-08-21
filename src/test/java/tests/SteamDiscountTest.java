package tests;

import models.Game;
import org.testng.annotations.Test;
import pages.AgeCheckPage;
import pages.CategoryPage;
import pages.GamePage;
import pages.TopSellersPage;
import utils.StringUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SteamDiscountTest extends BaseTest {

    @Test
    public void testFindGameWithMaxDiscount() {
        assertTrue(homePage.state().waitForDisplayed(), "Home page is not displayed");
        homePage.menu.selectCategory("Action");

        CategoryPage categoryPage = new CategoryPage();
        assertTrue(categoryPage.state().waitForDisplayed(), "Category page is not displayed");
        categoryPage.openTopSellersTab();

        TopSellersPage topSellersPage = new TopSellersPage();
        Game maxDiscountedGame = topSellersPage.getMaxDiscountedGame();
        topSellersPage.selectGame(maxDiscountedGame);

        AgeCheckPage ageCheckPage = new AgeCheckPage();
        ageCheckPage.confirmAgeWithYear(1985);

        GamePage gamePage = new GamePage();
        Game actualGame = gamePage.getGame();

        assertEquals(StringUtils.normalizePrice(maxDiscountedGame.getDiscountedPrice()),
                StringUtils.normalizePrice(actualGame.getDiscountedPrice()),
                "Discounted price mismatch");
        assertEquals(maxDiscountedGame.getDiscountPercentage(), actualGame.getDiscountPercentage(), "Discount mismatch");
    }
}