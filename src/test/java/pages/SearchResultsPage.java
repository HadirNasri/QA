package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Sélecteur pour les liens de produits dans les résultats
    // Tu peux aussi mettre ".product-item .product-title a"
    private By productLinks = By.cssSelector(".product-item a");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openFirstProduct() {
        // 1. Attendre que les produits apparaissent
        wait.until(ExpectedConditions.visibilityOfElementLocated(productLinks));

        // 2. Essayer de cliquer avec une petite tolérance aux éléments "stale"
        for (int i = 0; i < 3; i++) {  // on tente 3 fois max
            try {
                List<WebElement> products = driver.findElements(productLinks);
                if (products.isEmpty()) {
                    throw new NoSuchElementException("Aucun produit trouvé dans les résultats.");
                }

                WebElement firstProduct = products.get(0);
                wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();
                return; // si ça marche → on sort de la méthode

            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException, tentative " + (i + 1));
                // on boucle et on réessaie
            }
        }

        // Si on arrive ici après 3 tentatives, on fail "proprement"
        throw new RuntimeException("Impossible de cliquer sur le premier produit (stale element répété).");
    }
}
