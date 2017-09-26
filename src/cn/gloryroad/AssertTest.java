package cn.gloryroad;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

public class AssertTest {
	public WebDriver driver;
	String baseUrl = "http://www.baidu.con";
  @Test
  public void TestBaidu() {
	  driver.get(baseUrl);
	  WebElement inputBox = driver.findElement(By.id("kw"));
	  //inputBox.isDisplayed()用来判断输入框是否显示在了百度的首页上。若显示返回true，否则返回false。
	  Assert.assertTrue(inputBox.isDisplayed());
  }
  @BeforeMethod
  public void beforeMethod() {
	  driver = new FirefoxDriver();
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
