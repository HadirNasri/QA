package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private WebDriver driver;

    // Sur Demo Web Shop, les boutons "Add to cart" ont la classe .add-to-cart-button
    private By addToCartButton = By.cssSelector(".add-to-cart-button");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(addToCartButton)
        );
        btn.click();
    }
}
