package pages;

import aquality.selenium.forms.Form;
import aquality.selenium.browser.AqualityServices;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.Objects;

public class AgeCheckPage extends Form {

    private static final By AGE_DAY_SELECT = By.id("ageDay");
    private static final By AGE_MONTH_SELECT = By.id("ageMonth");
    private static final By AGE_YEAR_SELECT = By.id("ageYear");
    private static final By VIEW_PAGE_BUTTON = By.id("view_product_page_btn");
    private static final By AGE_GATE_CONTAINER = By.className("agegate_birthday_selector");
    private static final By MAIN_CONTAINER = By.className("main_content_ctn");

    private static final String DAY_VALUE = "1";
    private static final String MONTH_VALUE = "January";

    public AgeCheckPage() {
        super(MAIN_CONTAINER, "Age Check Page");
    }

    public void confirmAgeWithYear(int birthYear) {
        waitForAgeGateToLoad();

        if (tryBirthdaySelector(birthYear) ||
                tryDirectButtonClick()) {
            waitForGamePageToLoad();
            return;
        }

        throw new RuntimeException("Age gate confirmation failed");
    }

    private void waitForAgeGateToLoad() {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(AGE_GATE_CONTAINER));
        } catch (Exception ignored) {
        }
    }

    private void waitForGamePageToLoad() {
        try {
            getWait().until(driver -> !Objects.requireNonNull(driver.getCurrentUrl()).contains("/agecheck/"));
        } catch (Exception ignored) {
        }
    }

    private boolean tryBirthdaySelector(int birthYear) {
        try {
            WebElement daySelect = getWait().until(ExpectedConditions.elementToBeClickable(AGE_DAY_SELECT));
            new Select(daySelect).selectByValue(DAY_VALUE);

            WebElement monthSelect = getWait().until(ExpectedConditions.elementToBeClickable(AGE_MONTH_SELECT));
            new Select(monthSelect).selectByValue(MONTH_VALUE);

            WebElement yearSelect = getWait().until(ExpectedConditions.elementToBeClickable(AGE_YEAR_SELECT));
            new Select(yearSelect).selectByValue(String.valueOf(birthYear));

            WebElement viewPageBtn = getWait().until(ExpectedConditions.elementToBeClickable(VIEW_PAGE_BUTTON));
            viewPageBtn.click();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean tryDirectButtonClick() {
        try {
            WebElement viewPageBtn = getWait().until(ExpectedConditions.elementToBeClickable(VIEW_PAGE_BUTTON));
            viewPageBtn.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private WebDriver getDriver() {
        return AqualityServices.getBrowser().getDriver();
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(30));
    }
}