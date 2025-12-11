package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;
import utils.DriverFactory;

public class CheckoutTest {

    private WebDriver driver;
    private final String BASE_URL = "https://demowebshop.tricentis.com";

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createDriver();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCheckoutProcess() {
        // 1. Entrer sur le site + recherche
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);
        homePage.searchProduct("laptop");

        // 2. Ouvrir le premier produit trouvé
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        resultsPage.openFirstProduct();

        // 3. Ajouter au panier
        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();

        // 4. Aller au panier + Checkout
        CartPage cartPage = new CartPage(driver);
        cartPage.openCart();
        cartPage.proceedToCheckout();

        // 5. Remplir les infos client + passer commande
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillCustomerInfo(
                "Hadir Nasri",
                "Manouba, Tunis",
                "4242424242424242"
        );
        checkoutPage.confirmOrder();

        // 6. Vérifier la confirmation
        String confirmation = checkoutPage.getConfirmationMessage();
        System.out.println(">> Message de confirmation = " + confirmation);

        String txt = confirmation.toLowerCase();

        Assert.assertTrue(
                txt.contains("successfully processed") ||
                txt.contains("order has been successfully") ||
                txt.contains("your order has been"),
                "La confirmation de commande n'a pas été trouvée. Message reçu : " + confirmation
        );
    }
}
