package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

    // Driver y wait estáticos para todas las páginas
    public static WebDriver driver;
    public static WebDriverWait wait;

    // Constructor vacío
    public BasePage() {
        // No hace nada
    }

    // Inicializa driver si no está inicializado
    public static void initializeDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        }
    }

    // Devuelve driver público para usar en Hooks o tests
    public static WebDriver getDriver() {
        return driver;
    }

    // Cierra el navegador
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    // Navegar a una URL
    public static void navigateTo(String url) {
        driver.get(url);
    }

    // Encuentra elemento con espera explícita
    private WebElement Find(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    // Click en elemento
    public void clickElement(String locator) {
        Find(locator).click();
    }

    // Escribir texto
    public void write(String locator, String keysToSend) {
        WebElement element = Find(locator);
        element.clear();
        element.sendKeys(keysToSend);
    }

    // Dropdown por valor
    public void selectFromDropdownByValue(String locator, String value) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByValue(value);
    }

    // Dropdown por índice
    public void selectFromDropdownByIndex(String locator, int index) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByIndex(index);
    }

    // Obtener tamaño de dropdown
    public int dropdownSize(String locator) {
        return new Select(Find(locator)).getOptions().size();
    }

    // Obtener valores de dropdown
    public List<String> getDropdownValues(String locator) {
        List<WebElement> options = new Select(Find(locator)).getOptions();
        List<String> values = new ArrayList<>();
        for (WebElement option : options) {
            values.add(option.getText());
        }
        return values;
    }
}
