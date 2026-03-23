package steps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.BasePage;

public class Hooks {

    @Before
    public void setUp() {
        BasePage.initializeDriver(); // Inicializa ChromeDriver + WebDriverWait
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.log("Scenario failing, please refer to the attached screenshot");
            final byte[] screenshot = ((TakesScreenshot) BasePage.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot of the error");
        }
        BasePage.closeBrowser();
    }
}
