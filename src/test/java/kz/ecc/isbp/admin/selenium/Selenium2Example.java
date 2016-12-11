package kz.ecc.isbp.admin.selenium;


import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Selenium2Example {

	@Test
	public void test() throws Exception {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        
        driver.get("http://localhost:8080/admin/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
        WebElement element = driver.findElement(By.linkText("Пользователи / Полномочия"));        
        element.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);        
        
        element = driver.findElement(By.id("departmentId"));
        element.click();
        Thread.sleep(1000);        
        element.sendKeys("Департамент ин");
        Thread.sleep(1000);        
        element.sendKeys(Keys.RETURN);
                
        element = driver.findElement(By.id("applySearch"));
        element.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);
        
        element = driver.findElement(By.id("cmdCollapse"));
        element.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);
        
        element = driver.findElement(By.id("cmdFullScreen"));
        element.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);
        
        element = driver.findElement(By.cssSelector("a.dropdown-toggle"));
        element.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);
        
        element = driver.findElement(By.linkText("Изменить"));
        element.click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(1000);

        element = driver.findElement(By.id("email"));
        element.click();
        element.sendKeys(Keys.END);
        Thread.sleep(1000);
        element.sendKeys("1");
        Thread.sleep(1000);
        element.sendKeys("2");
        Thread.sleep(1000);
        element.sendKeys("3");
        Thread.sleep(1000);
        element.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(1000);
        element.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(1000);
        
        element = driver.findElement(By.id("cmdSave"));
        element.click();
        Thread.sleep(1000);	
	
        driver.quit();
	}

}
