package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.DriverFactory;

public class SearchTest {

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
    public void testProductSearch() {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);

        String keyword = "laptop";  // exemple
        homePage.searchProduct(keyword);

        // Vérif très simple : la page contient le mot recherché (à améliorer selon ton site)
        Assert.assertTrue(driver.getPageSource().toLowerCase().contains(keyword.toLowerCase()),
                "Les résultats de recherche ne semblent pas contenir le produit recherché.");
    }
}
