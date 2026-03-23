package steps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.BasePage;

public class Hooks {

    // 🔹 Se ejecuta antes de cada escenario
    @Before
    public void setup() {
        BasePage.initializeDriver(); // Inicializa ChromeDriver + WebDriverWait
    }

    // 🔹 Se ejecuta después de cada escenario
    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.log("Scenario failing, please refer to the image attached to this report");
            final byte[] screenshot = ((TakesScreenshot) BasePage.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        BasePage.closeBrowser(); // Cierra el navegador al final
    }
}
