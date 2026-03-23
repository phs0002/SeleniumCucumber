package pages;

import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    // Constructor vacío
    public BasePage() {
        // No hace nada, driver se inicializa por initializeDriver()
    }

    // Inicializa el driver (Chrome) y WebDriverWait
    public static void initializeDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        }
    }

    // Cierra el navegador
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    // Navega a una URL
    public static void navigateTo(String url) {
        driver.get(url);
    }

    // Encuentra un elemento y devuelve WebElement
    private WebElement Find(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    // Click en elemento
    public void clickElement(String locator) {
        Find(locator).click();
    }

    // Escribir texto en elemento
    public void write(String locator, String keysToSend) {
        WebElement element = Find(locator);
        element.clear();
        element.sendKeys(keysToSend);
    }

    // Seleccionar valor en dropdown por value
    public void selectFromDropdownByValue(String locator, String value) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByValue(value);
    }

    // Seleccionar valor en dropdown por index
    public void selectFromDropdownByIndex(String locator, int index) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByIndex(index);
    }

    // Obtener tamaño de dropdown
    public int dropdownSize(String locator) {
        Select dropdown = new Select(Find(locator));
        return dropdown.getOptions().size();
    }

    // Obtener todos los valores de dropdown
    public List<String> getDropdownValues(String locator) {
        Select dropdown = new Select(Find(locator));
        List<WebElement> options = dropdown.getOptions();
        List<String> values = new ArrayList<>();
        for (WebElement option : options) {
            values.add(option.getText());
        }
        return values;
    }
}
