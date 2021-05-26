package com.qa.Pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

public class LoginOYORooms 
{
	
	public FileInputStream fis;
	public FileInputStream fis1;
	WebDriver driver;
	Properties OR1;
	public WebDriverWait wait;
	
	
	@BeforeClass
	public void accessURL()throws IOException
	{
		
		fis = new FileInputStream("C:\\Users\\mousu\\eclipse-workspace\\PwCDemo1\\config.properties");
		Properties config = new Properties();
		config.load(fis);
		String startBrowser = config.getProperty("browser");
		if(startBrowser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver","C:\\Users\\mousu\\PwCDemo\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.get(config.getProperty("url"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println(driver.getTitle());
			
			
		}
		else
		{
			System.out.println("Please mention the browser name as Chrome");
		}
		
		fis1 = new FileInputStream("C:\\Users\\mousu\\eclipse-workspace\\PwCDemo1\\OR1.properties");
		OR1 = new Properties();
		OR1.load(fis1);
	}
	
	
	
	
	@Test(priority = 1)
	public void findLocators() throws InterruptedException
	{
		
		WebElement searchLocation = driver.findElement(By.xpath(OR1.getProperty("searchLoc")));
		searchLocation.click();
		System.out.println("hi");
		Thread.sleep(3000);
		searchLocation.sendKeys("Kolkata");
		Thread.sleep(3000);
		
		List<WebElement> searchedLocPopUp = driver.findElements(By.xpath(OR1.getProperty("searchedLocPopUp")));
		int size = searchedLocPopUp.size();
		for (int i = 0; i<size; i++)
		{
			String str=searchedLocPopUp.get(i).getText();
			if (str.contains("Kolkata"))
			{
				searchedLocPopUp.get(i).click();
				break;
			}
		}
		
		WebElement clickOnCalender = driver.findElement(By.xpath(OR1.getProperty("calender_click")));
		clickOnCalender.click();
		//Thread.sleep(3000);
		
		//WebElement month_selector = driver.findElement(By.xpath(OR1.getProperty("monthFrom")));
		//Select s = new Select(month_selector);
		//s.selectByVisibleText("June");
		
		
		WebElement dateFromSelect = driver.findElement(By.xpath(OR1.getProperty("dateFrom")));
		dateFromSelect.click();
		//Thread.sleep(3000);
		
		WebElement dateToSelect = driver.findElement(By.xpath(OR1.getProperty("dateTo")));
		dateToSelect.click();
		//Thread.sleep(3000);
		
		WebElement SearchClick = driver.findElement(By.xpath(OR1.getProperty("Search_button")));
		SearchClick.click();
		
		
		WebElement BookNow_button = driver.findElement(By.xpath(OR1.getProperty("BookNow_btn")));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", BookNow_button);
		//Thread.sleep(6000);
		
}
	
	@Test(priority = 2)
	public void Assert()
	{
		
		
		String str = driver.getWindowHandle();
		Set<String> window_list = driver.getWindowHandles();
		System.out.println("The window IDs are:" +window_list);
		for(String str1:window_list)
		{
			if(!(str1).equals(str))
			{
			driver.switchTo().window(str1);
			System.out.println("switched to child window");
			
			WebElement messageValue=driver.findElement(By.xpath(OR1.getProperty("actual_message")));
			messageValue.click();
			String messageDisplayed = messageValue.getText();
				
				org.testng.Assert.assertEquals(OR1.getProperty("expected_message"),messageDisplayed);
				
				System.out.println("The message displayed is:" +OR1.getProperty("actual_message"));
			}
		}
		
				
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();

	}

}
