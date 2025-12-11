package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By cartLink = By.cssSelector("a[href='/cart']");
    private By cartPageTitle = By.cssSelector(".page-title");
    private By termsOfServiceCheckbox = By.id("termsofservice");

    // 🔴 ANCIEN : private By checkoutButton = By.id("checkout");
    // ✅ NOUVEAU : on cible le vrai bouton
    private By checkoutButton = By.cssSelector("button.button-1.checkout-button");
    // ou : private By checkoutButton = By.cssSelector("button[name='checkout']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openCart() {
        driver.findElement(cartLink).click();
        String title = driver.getTitle();
        System.out.println(">> CartPage : page du panier ouverte, titre = " + title);
    }

    public void proceedToCheckout() {
        // Attendre que la page panier soit bien chargée
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageTitle));

        // 1. Cocher la case "I agree with the terms of service" si elle existe
        try {
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(termsOfServiceCheckbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (TimeoutException e) {
            System.out.println("Checkbox 'termsofservice' ABSENTE, on continue sans la cocher.");
        }

        // 2. Cliquer sur le bouton Checkout
        WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutBtn.click();
    }
}
