package pages;

import com.microsoft.playwright.Page;

public class CartPage {

    private final Page cartPage;
    private static final String EMPTY_CART_HEADER = "//h2[normalize-space(text())='Your Amazon Cart is empty']";

    public CartPage(Page cartPage) {
        this.cartPage = cartPage;
    }

    public String getHeaderText () {
        return cartPage.locator(EMPTY_CART_HEADER).textContent().trim();
    }
}
