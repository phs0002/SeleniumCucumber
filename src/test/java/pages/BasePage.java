package pages;

// Importaciones necesarias
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

    // Inicialización estática del driver y WebDriverWait
    static {
        WebDriverManager.chromedriver().setup();

        ChromeDriver chromeDriver = new ChromeDriver();  // Puedes agregar opciones headless aquí si quieres
        driver = chromeDriver;

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Constructor (puedes dejarlo si lo necesitas)
    public BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }

    // 🔹 Nuevo método: permite acceder al driver desde TestRunner o Hooks
    public static WebDriver getDriver() {
        return driver;
    }

    // Navegar a una URL
    public static void navigateTo(String url) {
        driver.get(url);
    }

    // Cerrar navegador
    public static void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    // Buscar elemento con espera explícita
    private WebElement Find(String locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void clickElement(String locator) {
        Find(locator).click();
    }

    public void write(String locator, String keysToSend) {
        Find(locator).clear();
        Find(locator).sendKeys(keysToSend);
    }

    public void selectFromDropdownByValue(String locator, String value) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByValue(value);
    }

    public void selectFromDropdownByIndex(String locator, Integer index) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByIndex(index);
    }

    public int dropdownSize(String locator) {
        Select dropdown = new Select(Find(locator));
        return dropdown.getOptions().size();
    }

    public List<String> getDropdownValues(String locator) {
        Select dropdown = new Select(Find(locator));
        List<String> values = new ArrayList<>();
        for (WebElement option : dropdown.getOptions()) {
            values.add(option.getText());
        }
        return values;
    }
}
