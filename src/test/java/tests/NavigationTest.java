package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.DriverFactory;

public class NavigationTest {

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
    public void testHomePageLoads() {
        HomePage homePage = new HomePage(driver);
        homePage.open(BASE_URL);

        String title = driver.getTitle();
        System.out.println("Titre de la page : " + title);

        // Assertion simple (à adapter)
        Assert.assertTrue(title != null && !title.isEmpty(),
                "La page d'accueil ne semble pas s'être chargée correctement.");
    }
}
