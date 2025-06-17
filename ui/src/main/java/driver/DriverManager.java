package driver;

import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {

    private static final ThreadLocal<DriverFactory> driverFactoryThread = new ThreadLocal<>();

    public static void initDriverFactory() {
        try {
            DriverFactory factory = new DriverFactory();
            driverFactoryThread.set(factory);
            RemoteWebDriver driver = factory.getDriver();
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize DriverFactory", e);
        }
    }

    public static RemoteWebDriver getDriver() {
        DriverFactory factory = driverFactoryThread.get();
        if (factory == null) {
            throw new IllegalStateException("DriverFactory not initialized.");
        }
        try {
            return factory.getDriver();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get WebDriver instance", e);
        }
    }

    public static void quitDriver() {
        DriverFactory factory = driverFactoryThread.get();
        if (factory != null) {
            factory.quitDriver();
            driverFactoryThread.remove();
        }
    }
}
