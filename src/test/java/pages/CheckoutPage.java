package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Checkout as Guest
    private By checkoutAsGuestButton = By.cssSelector("input.button-1.checkout-as-guest-button");

    // Billing Address
    private By firstNameInput = By.id("BillingNewAddress_FirstName");
    private By lastNameInput = By.id("BillingNewAddress_LastName");
    private By emailInput = By.id("BillingNewAddress_Email");
    private By countrySelect = By.id("BillingNewAddress_CountryId");
    private By cityInput = By.id("BillingNewAddress_City");
    private By address1Input = By.id("BillingNewAddress_Address1");
    private By zipInput = By.id("BillingNewAddress_ZipPostalCode");
    private By phoneInput = By.id("BillingNewAddress_PhoneNumber");

    // Continue buttons
    private By billingContinueButton =
            By.cssSelector("#billing-buttons-container input.button-1.new-address-next-step-button");
    private By shippingAddressContinueButton =
            By.cssSelector("#shipping-buttons-container input.button-1.new-address-next-step-button");
    private By shippingMethodContinueButton =
            By.cssSelector("#shipping-method-buttons-container input.button-1.shipping-method-next-step-button");
    private By paymentMethodContinueButton =
            By.cssSelector("#payment-method-buttons-container input.button-1.payment-method-next-step-button");
    private By paymentInfoContinueButton =
            By.cssSelector("#payment-info-buttons-container input.button-1.payment-info-next-step-button");

    // Confirmation
    private By confirmOrderButton =
            By.cssSelector("#confirm-order-buttons-container input.button-1.confirm-order-next-step-button");
    private By confirmationMessage =
            By.cssSelector(".section.order-completed .title strong");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void clickIfPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (!elements.isEmpty()) {
            elements.get(0).click();
        }
    }

    public void fillCustomerInfo(String fullName, String address, String cardNumber) {

        // Si la page propose "Checkout as Guest", on clique
        clickIfPresent(checkoutAsGuestButton);

        // Formulaire visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));

        // Split du nom
        String firstName = fullName;
        String lastName = "Test";
        if (fullName.contains(" ")) {
            String[] parts = fullName.split(" ", 2);
            firstName = parts[0];
            lastName = parts[1];
        }

        // Remplir les champs
        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(firstName);

        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lastName);

        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys("test@example.com");

        new Select(driver.findElement(countrySelect)).selectByVisibleText("Canada");

        driver.findElement(cityInput).clear();
        driver.findElement(cityInput).sendKeys("Montreal");

        driver.findElement(address1Input).clear();
        driver.findElement(address1Input).sendKeys(address);

        driver.findElement(zipInput).clear();
        driver.findElement(zipInput).sendKeys("H1H 1H1");

        driver.findElement(phoneInput).clear();
        driver.findElement(phoneInput).sendKeys("5140000000");

        // Enchaîner les étapes
        wait.until(ExpectedConditions.elementToBeClickable(billingContinueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(shippingAddressContinueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodContinueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(paymentMethodContinueButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(paymentInfoContinueButton)).click();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton)).click();
    }

    public String getConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage)).getText();
    }
}
