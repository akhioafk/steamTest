package pages;

import aquality.selenium.forms.Form;
import forms.components.Menu;
import org.openqa.selenium.By;

public class HomePage extends Form {

    private static final By STORE_NAV_AREA = By.id("store_nav_area");
    private static final By INSTALL_STEAM_BUTTON = By.xpath("//div[contains(@class,'header_installsteam_btn_content')]");

    public final Menu menu;

    public HomePage() {
        super(STORE_NAV_AREA, "Home Page");
        this.menu = new Menu();
    }

    public void clickInstallSteam() {
        getElementFactory().getButton(INSTALL_STEAM_BUTTON, "Install Steam").click();
    }
}
