package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



public class TestWiki {
	
	public static String url="https://www.wikipedia.org";
	public static WebDriver driver;
	
	public static void init_chrome()
	{
		System.setProperty("webdriver.chrome.driver", "D:\\tools\\WebDriver\\chromedriver_win32\\chromedriver.exe");
		
		driver= new ChromeDriver();
	}


	public static void init_firefox()
	{
		//System.setProperty("webdriver.gecko.driver", "D:\\tools\\WebDriver\\geckodriver-v0.21.0-win64\\geckodriver.exe");
		System.setProperty("webdriver.firefox.marionette", "false");
		driver= new FirefoxDriver();
	}
	
	public static void init_ie()
	{
		//System.setProperty("webdriver.ie.driver", "D:\\tools\\WebDriver\\IEDriverServer_x64_3.14.0\\IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", "D:\\tools\\WebDriver\\IEDriverServer_Win32_3.14.0\\IEDriverServer.exe");
		
		driver= new InternetExplorerDriver();
	}
	
	public static void main(String[] args) throws InterruptedException {
		//init_firefox();
		//init_ie();
		init_chrome();
		driver.get(url);
	    WebElement link;
	    link = driver.findElement(By.id("js-link-box-en"));
	    link.click();
	    Thread.sleep(5000);
	    WebElement searchBox;
	    searchBox=driver.findElement(By.id("searchInput"));
	    searchBox.sendKeys("software");
	    searchBox.submit();
	    Thread.sleep(5000);
	    driver.quit();    
	}
}
