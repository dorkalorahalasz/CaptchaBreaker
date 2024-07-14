package com.dorkalorahalasz;

import java.util.Collections;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App 
{
    //paage for testing
    static String targetUrl="https://www.google.com/recaptcha/api2/demo";

    //path to ChromeDriver
    static String chromeDriverPath = "captchabreaker/src/main/resources/chrome-driver/chromedriver-126.exe";

    //success
    private static boolean success = false;

    public static void main( String[] args )
    {
        WebDriver driver = null;

        try {
            log.info("create driver");
            // create custom WebDriver
            driver = prepareDriver();
            
            driver.navigate().to(targetUrl);
            driver.manage().window().maximize();
           
            sleep(5945); //wait for the page to load

            //switch to the iframe containing reCAPTCHA
            WebElement iframeElement = driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']"));
            driver.switchTo().frame(iframeElement);

            ////try method 1
            //log.info("Trying method 1 to break reCAPTCHA");
            success = modifyAriaChecked(driver);
            //log.info("Result of method 1: " + success);
//
            ////refresh the window for the new try
            //driver.navigate().refresh();
//
            //try method 2
            //log.info("Trying method 2 to break reCAPTCHA");
            //success = clickCheckboxDirectly(driver);
            //log.info("Result of method 2: " + success);

            ////refresh the window for the new try
            //driver.navigate().refresh();

            //try method 3
            //log.info("Trying method 3 to break reCAPTCHA");
            //success = modifyAllAttributes(driver);
            //log.info("Result of method 3: " + success);


            ////refresh the window for the new try
            //driver.navigate().refresh();

            //try method 4
            log.info("Trying method 4 to break reCAPTCHA");
            //success = hoverSomeElement(driver);
            log.info("Result of method 4: " + success);




            sleep(4723);
        } catch (Exception e) {
            log.error("Something went wrong in main " + e);
        } finally {
            // close the driver anyways
            log.info("close driver");
            if(driver != null){
                    driver.quit();
            }
        }    
    }

    //this method gets the aria-checked attribute of the reCAPTCHA span and modify it to true

    //RESULT - totally nothing
    private static boolean modifyAriaChecked(WebDriver driver){

        log.info("The method modifyAriaChecked tries to set the aria-checked attribute of the reCAPTCHA to true to break it");
        WebElement captchaSpan = driver.findElement(By.xpath("//span[@id='recaptcha-anchor']"));
    
        String ariaChecked = captchaSpan.getAttribute("aria-checked");
        log.info("aria-checked attribute of the reCAPTCHA: "+ariaChecked);
        log.info("setting attribute...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('aria-checked', 'true')", captchaSpan);
        
        ariaChecked = captchaSpan.getAttribute("aria-checked");
        log.info("aria-checked attribute of the reCAPTCHA: "+ariaChecked);

        boolean s = isSuccessful(driver);

        log.info("The method modifyAriaChecked finished. Success: " + s);
        return s;
    }

    //this method clicks the checkbox

    //RESULT - i get another check with pictures
    private static boolean clickCheckboxDirectly(WebDriver driver) {
        log.info("The method clickCheckboxDirectly tries to click the checkbox to break it");
        WebElement captchaSpan = driver.findElement(By.xpath("//span[@id='recaptcha-anchor']"));
    
        log.info("clicking checkbox...");
        captchaSpan.click();

        boolean s = isSuccessful(driver);

        log.info("The method clickCheckboxDirectly finished. Success: " + s);
        return s;
    }

    //this method modifies all the attributes of the reCAPTCHA span to the state like it was clicked

    //RESULT - its totally not so easy, you have to modify not just this but the whole DOM
    private static boolean modifyAllAttributes(WebDriver driver){

        log.info("The method modifyAllAttributes tries to set all of the attributes of the reCAPTCHA to true to break it");
        WebElement captchaSpan = driver.findElement(By.xpath("//span[@id='recaptcha-anchor']"));
    
        log.info("setting attributes...");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //sets aria-checked
        log.info("setting aria-checked to true...");
        js.executeScript("arguments[0].setAttribute('aria-checked', 'true')", captchaSpan);

        //adds class 
        log.info("adding recaptcha-checkbox-checked to the class list...");
        js.executeScript("arguments[0].classList.add('recaptcha-checkbox-checked')", captchaSpan);

         //adds aria-disabled
         log.info("adding aria-disabled with value false...");
         js.executeScript("arguments[0].setAttribute('aria-disabled', 'false')", captchaSpan);

         //adds tabindex 
         log.info("adding tabindex with value 0...");
         js.executeScript("arguments[0].setAttribute('tabindex', '0')", captchaSpan);

         //adds style
         log.info("adding some style...");
         js.executeScript("arguments[0].style.overflow = 'visible'", captchaSpan);

         sleep(1567);

        
        boolean s = isSuccessful(driver);

        log.info("The method modifyAllAttributes finished. Success: " + s);
        return s;
    }

    //this method hovers over some text element before click

    //RESULT - picture verification
    private static boolean hoverSomeElement(WebDriver driver) {
        log.info("The method hoverSomeElement tries to hover over some elemnts before clicking the checkbox to break it");

        // Create an instance of Actions class
        Actions actions = new Actions(driver);
        
        log.info("hovering over some elements...");  

        //they are outside of the iframe
        driver.switchTo().defaultContent();

        //hovers over one element
        WebElement elementToHover = driver.findElement(By.xpath("//label[@for='input1']"));
        actions.moveToElement(elementToHover).perform();
        sleep(456);

        //hovers over one more element
        elementToHover = driver.findElement(By.xpath("//input[@id='input3']"));
        actions.moveToElement(elementToHover).perform();
        sleep(243);

        //and one more
        elementToHover = driver.findElement(By.xpath("//p"));
        actions.moveToElement(elementToHover).perform();
        sleep(591);

        //and one more
        elementToHover = driver.findElement(By.xpath("//input[@id='recaptcha-demo-submit']"));
        actions.moveToElement(elementToHover).perform();
        sleep(167);


        //this is inside of the iframe
        WebElement iframeElement = driver.findElement(By.xpath("//iframe[@title='reCAPTCHA']"));
        driver.switchTo().frame(iframeElement);

        //and now over the reCHAPTCHA box
        elementToHover = driver.findElement(By.xpath("//label"));
        actions.moveToElement(elementToHover).perform();
        sleep(369);

        // and finally click
        WebElement captchaSpan = driver.findElement(By.xpath("//span[@id='recaptcha-anchor']"));
    
        log.info("clicking checkbox...");
        captchaSpan.click();

        boolean s = isSuccessful(driver);

        log.info("The method hoverSomeElement finished. Success: " + s);
        return s;
    }

    //this method clicks the submit button and looks for the success message
    private static boolean isSuccessful(WebDriver driver){

        //submits the form
        try {
            sleep(3400);

            driver.switchTo().defaultContent();
            driver.findElement(By.xpath("//input[@id='recaptcha-demo-submit']")).click();
            sleep(2674);

            //looks for the message
        
            WebElement successMessage = driver.findElement(By.xpath("//div[@class='recaptcha-success']"));
            //if present - ok
            log.info(successMessage.getText());
            return true;
        } catch (NoSuchElementException e) {
            //if not - not ok
            return false;
        } catch (ElementClickInterceptedException e1) {
            //you got another test - not ok
            return false;
        } catch (Exception e2){
            //something else went wrong - not ok
            log.warn("Error in isSuccessful when submitting the form: " + e2);
            return false;
        }

    }

    //prepares a WebDriver
    private static WebDriver prepareDriver() {
        
        WebDriver driver;
    
        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            //if (!isHeadless) {
                chromeOptions.addArguments("--lang=en", "--disable-gpu", "--window-size=1920,1080",
                        "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            //} else {
            //    chromeOptions.addArguments("--lang=en", "--headless", "--disable-gpu", "--window-size=1920,1080",
            //            "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            //}
            chromeOptions.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(chromeOptions);

        return driver;
    }

    protected static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

}
