package pages;

import com.microsoft.playwright.Page;

public class HomePage {

    private final Page homePage;
    private static final String CART_LINK = "//a[@id='nav-cart']";

    public HomePage(Page homePage) {
        this.homePage = homePage;
    }

    public void clickCartLink () {
        homePage.locator(CART_LINK).click();
    }


}
