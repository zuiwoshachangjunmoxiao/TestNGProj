package cn.gloryroad.dataprovider;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.sql.*;
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

public class TestDataDrivenByMysqlDatabase {
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
		
		//�����ݿ����ݱ��е�ÿ�����ݵ�ǰ����������Ϊ�����ؼ��ʣ������������ҳ���Ƿ����ÿ���������еĵ��������ݡ�
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

	@DataProvider(name = "testData")
	public static Object[][] words() throws IOException {
		return getTestData("testdata");
	}

	private static Object[][] getTestData(String tablename) throws IOException {
		//����  MySQL ���ݿ������
		String driver = "com.mysql.jdbc.Driver";
		//�������ݿ�� IP ��ַ�����ݿ�����
		String url = "jdbc:mysql://10.10.192.101:3307/fhcb_collectionData_gks?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		//�������ݿ���û�����Ϊ�����ݿ�Ȩ���趨�Ȳ���������ʹ�����ݿ��root�û����в���
		//����ʽ���������������ݿ��У�����ʹ�÷�root�û��˺Ž����Զ������Ե���ز���
		String user = "root";
		//�������ݿ�root�û��ĵ�¼����
		String password = "wfyjy@LVyn206";
		//�����洢�������ݵ� list ����
		List<Object[]> records = new ArrayList<Object[]>();
		try {
			//�趨����
			Class.forName(driver);
			//�����������ݵ����Ӷ���ʹ�����ݿ��������ַ���û�����������Ϊ����
			Connection conn = DriverManager.getConnection(url,user,password);
			//������ݿ����ӿ��ã���ӡ���ݿ����ӳɹ�����Ϣ
			if (!conn.isClosed()) {
				System.out.println("�������ݿ�ɹ���");
			}
			//���� statement ����
			Statement statement = conn.createStatement();
			//ʹ�ú�������ƴ��Ҫִ�е� SQL ��䣬�����������ȡ���ݱ������������
			String sql = "select * from " + tablename;
			//���� ResultSet ���󣬴�ȡִ�� SQL ���󷵻ص����ݽ����
			ResultSet rs = statement.executeQuery(sql);
			//����һ�� ResultSetMetaData ����
			ResultSetMetaData rsMetaData = rs.getMetaData();
			//���� ResultSetMetaData ����� getColumnCount ������ȡ�����е�����
			int cols = rsMetaData.getColumnCount();
			//ʹ�� next �����������ݽ�����е�����������
			while (rs.next()) {
				//����һ���ַ������ݣ������Сʹ�������е��и�����������
				String fields[] = new String[cols];
				int col = 0;
				//���������������е����������ݣ����洢���ַ�������
				for (int colIdx = 0; colIdx<cols; colIdx++){
					fields[col] = rs.getString(colIdx+1);
					col++;
				}
				//��ÿһ�е����ݴ洢���ַ����ݺ󣬴洢�� records ��
				records.add(fields);
				//����������е�ǰ�������ݣ�������֤���ݿ������Ƿ���ȷȡ��
				System.out.println(rs.getString(1) + "   " + rs.getString(2) + "   " + rs.getString(3));
			}
			//�ر����ݽ��������
			rs.close();
			//�ر����ݿ�����
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("δ���ҵ� MySQL �������࣡");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//���庯������ֵ���� Object[][]
		//���洢�������ݵ� list ת��Ϊһ�� Object �Ķ�λ����
		Object[][] results = new Object[records.size()][];
		//���ö�λ����ÿ�е�ֵ��ÿ����һ��Object����
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		
		return results;
	}
}
