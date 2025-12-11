package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.ProductPage;
import utils.DriverFactory;

public class AddToCartTest {

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
    public void testAddProductToCart() {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);

        homePage.searchProduct("laptop");

        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        resultsPage.openFirstProduct();

        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();

        // Vérif simple : on cherche le mot "panier" / "cart" dans la page
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(pageSource.contains("panier") || pageSource.contains("cart"),
                "Le produit ne semble pas avoir été ajouté au panier.");
    }
}
