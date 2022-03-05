package com.herokuapp.theinternet;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest {
	
	String projectLocation = System.getProperty("user.dir");
	
	@Test(priority = 1, groups = {"possitiveTest"})
	public void possitiveLoginTest() {

		System.out.println("Starting possitiveLoginTest");

		String pageURLString = "https://the-internet.herokuapp.com/login";
		String username = "tomsmith";
		String password = "SuperSecretPassword!";

		// Firefox driver
		System.setProperty("webdriver.gecko.driver", projectLocation + "/src/main/resources/web-drivers/geckodriver");
		// WebDriver driver = new FirefoxDriver();

		// Chrome driver
		System.setProperty("webdriver.chrome.driver", projectLocation + "/src/main/resources/web-drivers/chromedriver");
		WebDriver driver = new ChromeDriver();

		// Open page
		driver.get(pageURLString);

		// Implicit wait - By default it is set to zzero
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		// Locate username box and type username
		WebElement usernamElement = driver.findElement(By.id("username"));
		usernamElement.clear();
		usernamElement.sendKeys(username);
		System.out.println("Typing " + username);

		pause(2000);

		// Locate password box and type password
		WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys(password);
		System.out.println("Typing " + password);

		pause(2000);

		// Press Login button
		WebElement loginButton = driver.findElement(By.xpath("//button[@class='radius']"));
		loginButton.click();

		//
		// Validations
		//

		// flash message
		String expectedFlashMessage = "You logged into a secure area!";
		WebElement flashMessageElement = driver.findElement(By.id("flash"));

		// success message
		WebElement subheaderElement = driver.findElement(By.xpath("//*[@class='subheader']"));
		String expectedMessage = "Welcome to the Secure Area. When you are done click logout below.";
		String currentMessageString = subheaderElement.getText().trim();
		
		// Logout button
		WebElement logoutButton = driver.findElement(By.cssSelector("a.button.secondary.radius"));

		// Assertions
		
		// Flash message
		Assert.assertEquals(removeChildText(flashMessageElement), expectedFlashMessage);
		
		// Sub-header message
		Assert.assertTrue(subheaderElement.isDisplayed(), "Element was not displayed");
		Assert.assertEquals(expectedMessage, currentMessageString);
		
		// Logout button
		Assert.assertTrue(logoutButton.isDisplayed());

		// quit driver
		driver.quit();

	}

	@Test(priority = 2)
	public void negativeUserLoginTest() {

		System.out.println("Starting negativeUserLoginTest");

		String pageURLString = "https://the-internet.herokuapp.com/login";
		String username = "wrongUsername";
		String password = "SuperSecretPassword!";

		// Firefox driver
		System.setProperty("webdriver.gecko.driver", projectLocation + "/src/main/resources/web-drivers/geckodriver");
		// WebDriver driver = new FirefoxDriver();

		// Chrome driver
		System.setProperty("webdriver.chrome.driver", projectLocation + "/src/main/resources/web-drivers/chromedriver");
		WebDriver driver = new ChromeDriver();

		// Open page
		driver.get(pageURLString);

		// Locate username box and type username
		WebElement usernamElement = driver.findElement(By.id("username"));
		usernamElement.clear();
		usernamElement.sendKeys(username);
		System.out.println("Typing " + username);

		pause(2000);

		// Locate password box and type password
		WebElement passwordElement = driver.findElement(By.id("password"));
		passwordElement.clear();
		passwordElement.sendKeys(password);
		System.out.println("Typing " + password);

		pause(2000);

		// Press Login button
		WebElement loginButton = driver.findElement(By.xpath("//button[@class='radius']"));
		loginButton.click();

		// Validation

		// Expected message
		String expectedMessage = "Your username is invalid";

		// Current message
		WebElement invalidUsernameMessageElement = driver.findElement(By.id("flash"));
		String currentMessage = invalidUsernameMessageElement.getText();

		// Assertion
		Assert.assertTrue(currentMessage.contains(expectedMessage));

		// quit driver
		driver.quit();

	}

	private void pause(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (Exception e) {
			// Nothing
		}
	}

	private String removeChildText(WebElement element) {
		String text = element.getText().trim();
		List<WebElement> children = element.findElements(By.xpath("./*"));
		for (WebElement child : children) {
			text = text.replaceFirst(child.getText(), "").trim();
		}
		return text.replaceAll(":", "").trim();
	}

	public List<String> removeChildText(List<WebElement> elements) {
		List<String> Listtext = new ArrayList<>();
		for (WebElement element : elements) {
			Listtext.add(removeChildText(element));
		}
		return Listtext;
	}

}
