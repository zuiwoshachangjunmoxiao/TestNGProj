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

		// ��ʾ�ȴ���ȷ��ҳ����� ���������ߡ� �ؼ��֡���һ����Ϊ�˵ȴ�ҳ��������
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.findElement(By.className("search_tool")).getText().contains("��������");
			}
		});

		// CSV �ļ�ÿ��ǰ�����ʻ���Ϊ�����ʻ㣬�����������ҳ���Ƿ���� CSV �ļ�ÿ���е����һ���ʻ�Ĺؼ���
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

	// ʹ�� @DataProvider ע�⣬�����ݼ�������Ϊ testData
	@DataProvider(name = "testData")
	public static Object[][] words() throws IOException {
		// �������еľ�̬���� getTestData ����ȡCSV�ļ��Ĳ�������
		return getTestData("C:\\D\\testData.csv");
	}

	// ��ȡ CSV �ļ��ľ�̬������ʹ�� CSV �ļ��ľ����ļ�·����Ϊ��������
	private static Object[][] getTestData(String fileName) throws IOException {
		List<Object[]> records = new ArrayList<Object[]>();
		String record;
		// �趨 UTF-8 �ַ�����ʹ�ô����������ַ������� BufferedReader ��ȡ�ļ�����
		BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		// ���Զ�ȡ CSV �ļ��ı����У���һ�У�
		file.readLine();
		// ������ȡ�ļ��еĳ���һ������������������ݣ����洢����Ϊ records �� ArrayList �У�ÿһ�� records
		// �д洢�Ķ���Ϊһ�� String ����
		while ((record = file.readLine()) != null) {
			String fields[] = record.split(",");
			records.add(fields);

		}
		// �ر��ļ�����
		file.close();

		// ���庯������ֵ���� Object[][]
		// ���洢�������ݵ� list ת��Ϊһ�� Object �Ķ�ά����
		Object[][] results = new Object[records.size()][];
		// ���ά����ÿ�е�ֵ��ÿ����һ��Object����
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}

		return results;
	}
}
