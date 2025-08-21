package forms.components;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import org.openqa.selenium.By;

public class Menu {

    private static final String CATEGORIES_MENU = "//a[@class='pulldown_desktop' and contains(text(), 'Categories')]";
    private static final String CATEGORY_ITEM_BY_NAME = "//a[contains(@class,'popup_menu_item') and normalize-space()='%s']";

    public void selectCategory(String categoryName) {
        ILabel categoriesMenu = AqualityServices.getElementFactory()
                .getLabel(By.xpath(CATEGORIES_MENU), "Categories");

        categoriesMenu.getMouseActions().moveMouseToElement();

        String itemXpath = String.format(CATEGORY_ITEM_BY_NAME, categoryName);
        ILabel categoryItem = AqualityServices.getElementFactory()
                .getLabel(By.xpath(itemXpath), categoryName);

        categoryItem.click();
    }
}
