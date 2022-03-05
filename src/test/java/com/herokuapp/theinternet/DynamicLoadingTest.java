package com.herokuapp.theinternet;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DynamicLoadingTest {
	
	// Selenium mimic user acctions so, if the element is not present/ is visible on the page, Selenum cannot interact with it

	String projectLocation = System.getProperty("user.dir");

	public void elementIsHidenTest() throws InterruptedException {

		String testPage = "https://the-internet.herokuapp.com/dynamic_loading";

		// Create a Chrome driver
		System.setProperty("webdriver.chrome.driver", projectLocation + "/src/main/resources/web-drivers/chromedriver");
		WebDriver driver = new ChromeDriver();

		
		// Please ignore below commented code
		/*
		 * Create chrome driver using WebDriverManager
		WebDriverManager.chromedriver().setup();
		WebDriver driver2 = new ChromeDriver();
		 */

		// Open test page
		driver.get(testPage);
		System.out.println("Page " + testPage + " opened");

		// Maximize browser window
		driver.manage().window().maximize();
		System.out.println("Page maximized");
		
		// Create implicit wait.
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// Navigate to Dynamic load page
		WebElement dynamicLoadLinkElement = driver.findElement(By.xpath("//a[contains(@href,'dynamic_loading/1')]"));

		dynamicLoadLinkElement.click();

		// Locate and click over Start button
		WebElement startButtonElement = driver.findElement(By.xpath("//div[@id='start']//button"));
		startButtonElement.click();

		// Validation

		WebElement finalMessageElement = driver.findElement(By.id("finish"));
		
		// Create a wait
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(finalMessageElement));
		
		
		// Assertion
		// Hard assert
		Assert.assertTrue(finalMessageElement.isDisplayed(), "finishElement is not displayed");
		
		// Soft Assert
		//SoftAssert softAssert = new SoftAssert();
		//softAssert.assertTrue(finalMessageElement.isDisplayed(), "finishElement is not displayed");
		
		System.out.println("finishElement is displayed");

		// quit driver
		driver.quit();

	}

}
