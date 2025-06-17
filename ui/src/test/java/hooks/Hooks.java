package hooks;

import driver.DriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        DriverManager.initDriverFactory();
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Finishing scenario: " + scenario.getName());
        if (scenario.isFailed()) {
            System.out.println("Scenario failed: " + scenario.getName());

            // Screenshot capturing
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                                   .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

        }
        DriverManager.quitDriver();
    }
}