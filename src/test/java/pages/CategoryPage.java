package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CategoryPage extends Form {

    private static final By PAGE_TITLE = By.xpath("//div[contains(@class,'ContentHubTitle')]");
    private static final By TOP_SELLERS_TAB = By.xpath(
            "//div[@role='button' and contains(@class,'Focusable') and (text()='Top Sellers' or text()='Лидеры продаж')]"
    );
    private static final By TOP_SELLERS_SECTION = By.xpath("//div[text()='Top Sellers']");

    public CategoryPage() {
        super(PAGE_TITLE, "Category Page");
    }

    public void openTopSellersTab() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");

        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(TOP_SELLERS_TAB));
        js.executeScript("arguments[0].click();", element);

        wait.until(ExpectedConditions.presenceOfElementLocated(TOP_SELLERS_SECTION));
    }

    private WebDriver getDriver() {
        return AqualityServices.getBrowser().getDriver();
    }
}
