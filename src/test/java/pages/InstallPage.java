package pages;

import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class InstallPage extends Form {

    private static final By INSTALL_PAGE_HEADER =
            By.xpath("//h1[contains(text(),'Install Steam') or contains(text(),'Установить Steam')]");
    private static final By DOWNLOAD_BUTTON =
            By.xpath("//a[contains(@href, 'SteamSetup.exe')]");

    public InstallPage() {
        super(INSTALL_PAGE_HEADER, "Install Page");
    }

    public void clickDownload() {
        getElementFactory().getButton(DOWNLOAD_BUTTON, "Download Setup").click();
    }
}
