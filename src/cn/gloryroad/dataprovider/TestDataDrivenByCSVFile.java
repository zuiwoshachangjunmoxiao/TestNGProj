package cn.gloryroad.dataprovider;

import org.testng.annotations.Test;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import net.sourceforge.htmlunit.corejs.javascript.ast.NewExpression;
import org.testng.annotations.BeforeMethod;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

public class TestDataDrivenByCSVFile {
	public WebDriver driver;
	String baseUrl = "http://www.baidu.com";

	@Test(dataProvider = "testData")
	public void testSearch(String searchWord1, String searchWord2, String searchResult) {
		driver.get(baseUrl);
		driver.findElement(By.id("kw")).sendKeys(searchWord1 + " " + searchWord2);
		driver.findElement(By.id("su")).click();

		// 显示等待，确认页面出现 “搜索工具” 关键字。这一步是为了等待页面加载完成
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.findElement(By.className("search_tool")).getText().contains("搜索工具");
			}
		});

		// CSV 文件每行前两个词汇作为搜索词汇，断言搜索结果页面是否包含 CSV 文件每行中的最后一个词汇的关键词
		Assert.assertTrue(driver.getPageSource().contains(searchResult));
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = new FirefoxDriver();
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	// 使用 @DataProvider 注解，将数据集合命名为 testData
	@DataProvider(name = "testData")
	public static Object[][] words() throws IOException {
		// 调用类中的静态方法 getTestData ，获取CSV文件的测试数据
		return getTestData("C:\\D\\testData.csv");
	}

	// 读取 CSV 文件的静态方法，使用 CSV 文件的绝对文件路径作为函数参数
	private static Object[][] getTestData(String fileName) throws IOException {
		List<Object[]> records = new ArrayList<Object[]>();
		String record;
		// 设定 UTF-8 字符集，使用带缓冲区的字符输入流 BufferedReader 读取文件内容
		BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		// 忽略读取 CSV 文件的标题行（第一行）
		file.readLine();
		// 遍历读取文件中的除第一行外的其他所有行内容，并存储在名为 records 的 ArrayList 中，每一个 records
		// 中存储的对象为一个 String 数组
		while ((record = file.readLine()) != null) {
			String fields[] = record.split(",");
			records.add(fields);

		}
		// 关闭文件对象
		file.close();

		// 定义函数返回值，即 Object[][]
		// 将存储测试数据的 list 转换为一个 Object 的二维数组
		Object[][] results = new Object[records.size()][];
		// 设二维数组每行的值，每行是一个Object对象
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}

		return results;
	}
}
