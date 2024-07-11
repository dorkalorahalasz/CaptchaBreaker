package com.dorkalorahalasz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App 
{
    static String targetUrl="https://www.google.com/recaptcha/api2/demo";

    public static void main( String[] args )
    {
        WebDriver driver = null;

        try {
            log.info("create driver");
            // create custom WebDriver
            driver = prepareDriver();
            driver.get(targetUrl);
            driver.wait(1000);
        } catch (Exception e) {
            log.error("Something went wrong in main " + e);
        } finally {
            // close the driver anyways
            log.info("close driver");
            if(driver != null) driver.close();
        }    
    }

    private static WebDriver prepareDriver() {
        
        WebDriver driver = null;
        
        // Get the ChromeDriver path from the environment variable
        String chromeDriverPath = System.getenv("CHROME_DRIVER");
        if (chromeDriverPath != null) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        } else {
            throw new IllegalStateException("CHROME_DRIVER environment variable not set");
        }

        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--headless"); // Run Chrome in headless mode
        chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
        //chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
        
        driver = new ChromeDriver(chromeOptions);
        return driver;
    }
}
