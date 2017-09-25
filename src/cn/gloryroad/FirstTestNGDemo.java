package cn.gloryroad;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;

public class FirstTestNGDemo {
	public WebDriver driver;
	String baseUrl = "http://www.baidu.com";
	
  @Test
  public void f() {
	  driver.get(baseUrl);
	  driver.findElement(By.id("kw")).clear();
	  driver.findElement(By.id("kw")).sendKeys("安装TestNG单元测试框架");
	  driver.findElement(By.id("su")).click();
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.setProperty("webdriver.firefox.bin", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
	  driver = new FirefoxDriver();
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}