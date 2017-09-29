package cn.gloryroad.dataprovider;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class DataProviderTest {
	public WebDriver driver;
	String baseUrl = "http://www.baidu.com";

	@Test(dataProvider = "serchWords")
	public void f(String searchWord1, String searchWord2, String searchResult) {
		driver.get(baseUrl);
		driver.findElement(By.id("kw")).clear();
		driver.findElement(By.id("kw")).sendKeys(searchWord1+""+searchWord2);
		driver.findElement(By.id("su")).click();
		//����������ť�󣬵ȴ�3����ʾ�������
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//��ȡҳ��Դ�룬������������Ƿ�������������������Ĺؼ���
		Assert.assertTrue(driver.getPageSource().contains(searchResult));
	}

	@BeforeMethod
	public void beforeMethod() {
		driver= new FirefoxDriver();
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@DataProvider(name = "serchWords")
	public static Object[][] words() {
		return new Object[][] { {"������","����","���˶�"}, {"����","����","����"},{"����Σ��","���","����ɭ"} };
	}
}
