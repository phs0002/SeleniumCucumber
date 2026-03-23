package runner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import pages.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 * TestRunner para ejecutar escenarios @Courses
 * Configurado para capturas de pantalla en caso de fallo.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",      // Directorio de archivos .feature
        glue = "steps",                        // Paquete con los steps
        plugin = { "pretty", "html:target/cucumber-reports" },
        tags = "@Courses"
)
public class TestRunner {

    /**
     * Hook de Cucumber para tomar captura de pantalla si un escenario falla.
     * Se ejecuta después de cada escenario.
     */
    @After
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                File screenshot = ((TakesScreenshot) BasePage.getDriver())
                        .getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(screenshot,
                        new File("build/reports/screenshots/" + scenario.getName() + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Se ejecuta al final de todos los tests para cerrar el navegador.
     */
    @AfterClass
    public static void cleanDriver() {
        BasePage.closeBrowser();
    }
}
