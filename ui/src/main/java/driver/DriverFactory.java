package driver;

import static org.openqa.selenium.Proxy.ProxyType.MANUAL;
import static org.openqa.selenium.remote.CapabilityType.PROXY;
import static driver.DriverType.CHROME;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

@SuppressWarnings("unused")
public class DriverFactory {

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);
    private final DriverType selectedDriverType;
    private final String browser;
    private RemoteWebDriver driver;

    public DriverFactory() {
        DriverType driverType = CHROME;
        browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = DriverType.valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            System.err.println("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public RemoteWebDriver getDriver() throws Exception {
        if (null == driver) {
            instantiateWebDriver(selectedDriverType);
        }

        return driver;
    }

    public RemoteWebDriver getStoredDriver() {
        return driver;
    }

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }

    private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {
        //TODO add in a real logger instead of System.out
        System.out.println(" ");
        System.out.println("Local Operating System: " + operatingSystem);
        System.out.println("Local Architecture: " + systemArchitecture);
        System.out.println("Selected Browser: " + selectedDriverType);
        System.out.println("Connecting to Selenium Grid: " + useRemoteWebDriver);
        System.out.println(" ");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (proxyEnabled) {
            Proxy proxy = new Proxy();
            proxy.setProxyType(MANUAL);
            proxy.setHttpProxy(proxyDetails);
            proxy.setSslProxy(proxyDetails);
            desiredCapabilities.setCapability(PROXY, proxy);
        }

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(System.getProperty("gridURL"));
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }

            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }

            desiredCapabilities.setBrowserName(selectedDriverType.toString());
            desiredCapabilities.setCapability("browserstack.local", "true");
            desiredCapabilities.setCapability("os", "Windows");
            desiredCapabilities.setCapability("os_version", "10");
            desiredCapabilities.setCapability("resolution", "1920x1080");
            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
            driver.setFileDetector(new LocalFileDetector());
        } else {
            resolveDriver();
            driver = driverType.getWebDriverObject(desiredCapabilities);
        }
    }

    private void resolveDriver() {

        if (operatingSystem.contains("WINDOWS")) {
            if (BrowserTypeConstants.CHROME.equals(browser)) {
                WebDriverManager.chromedriver().setup();
            } else if (BrowserTypeConstants.FIREFOX.equals(browser)) {
                WebDriverManager.firefoxdriver().setup();
            } else if (BrowserTypeConstants.IE.equals(browser)) {
                WebDriverManager.iedriver().setup();
            } else if (BrowserTypeConstants.EDGE.equals(browser)) {
                WebDriverManager.edgedriver().setup();
            } else if (BrowserTypeConstants.OPERA.equals(browser)) {
                WebDriverManager.operadriver().setup();
            }
        } else if (operatingSystem.contains("LINUX")) {
            if (BrowserTypeConstants.CHROME.equals(browser)) {
                WebDriverManager.chromedriver().setup();
            } else if (BrowserTypeConstants.FIREFOX.equals(browser)) {
                WebDriverManager.firefoxdriver().setup();
            } else if (BrowserTypeConstants.OPERA.equals(browser)) {
                WebDriverManager.operadriver().setup();
            }
        } else if (operatingSystem.contains("MAC")) {
            if (BrowserTypeConstants.CHROME.equals(browser)) {
                WebDriverManager.chromedriver().setup();
            } else if (BrowserTypeConstants.FIREFOX.equals(browser)) {
                WebDriverManager.firefoxdriver().setup();
            } else if (BrowserTypeConstants.OPERA.equals(browser)) {
                WebDriverManager.operadriver().setup();
            }
        }
    }
}

