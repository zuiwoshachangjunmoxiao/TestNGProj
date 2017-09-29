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
		
		// 显示等待，确认页面出现 “搜索工具” 关键字。这一步是为了等待页面加载完成
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver d) {
				return d.findElement(By.className("search_tool")).getText().contains("搜索工具");
			}
		});
		
		//在数据库数据表中的每行数据的前两列数据作为搜索关键词，断言搜索结果页面是否包含每个数据行中的第三列数据。
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
		//声明  MySQL 数据库的驱动
		String driver = "com.mysql.jdbc.Driver";
		//声明数据库的 IP 地址和数据库名称
		String url = "jdbc:mysql://10.10.192.101:3307/fhcb_collectionData_gks?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		//声明数据库的用户名。为简化数据库权限设定等操作，本例使用数据库的root用户进行操作
		//在正式对外服务的生产数据库中，建议使用非root用户账号进行自动化测试的相关操作
		String user = "root";
		//声明数据库root用户的登录密码
		String password = "wfyjy@LVyn206";
		//声明存储测试数据的 list 对象
		List<Object[]> records = new ArrayList<Object[]>();
		try {
			//设定驱动
			Class.forName(driver);
			//声明链接数据的链接对象，使用数据库服务器地址、用户名和密码作为参数
			Connection conn = DriverManager.getConnection(url,user,password);
			//如果数据库链接可用，打印数据库链接成功的信息
			if (!conn.isClosed()) {
				System.out.println("连接数据库成功！");
			}
			//创建 statement 对象
			Statement statement = conn.createStatement();
			//使用函数参数拼接要执行的 SQL 语句，此语句用来获取数据表的所有数据行
			String sql = "select * from " + tablename;
			//声明 ResultSet 对象，存取执行 SQL 语句后返回的数据结果集
			ResultSet rs = statement.executeQuery(sql);
			//声明一个 ResultSetMetaData 对象
			ResultSetMetaData rsMetaData = rs.getMetaData();
			//调用 ResultSetMetaData 对象的 getColumnCount 方法获取数据行的列数
			int cols = rsMetaData.getColumnCount();
			//使用 next 方法遍历数据结果集中的所有数据行
			while (rs.next()) {
				//声明一个字符型数据，数组大小使用数据行的列个数进行声明
				String fields[] = new String[cols];
				int col = 0;
				//遍历所有数据行中的所有列数据，并存储在字符数据中
				for (int colIdx = 0; colIdx<cols; colIdx++){
					fields[col] = rs.getString(colIdx+1);
					col++;
				}
				//将每一行的数据存储到字符数据后，存储到 records 中
				records.add(fields);
				//输出数据行中的前三列内容，用于验证数据库内容是否正确取出
				System.out.println(rs.getString(1) + "   " + rs.getString(2) + "   " + rs.getString(3));
			}
			//关闭数据结果集对象
			rs.close();
			//关闭数据库链接
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println("未能找到 MySQL 的驱动类！");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//定义函数返回值，即 Object[][]
		//将存储测试数据的 list 转换为一个 Object 的二位数组
		Object[][] results = new Object[records.size()][];
		//设置二位数组每行的值，每行是一个Object对象
		for (int i = 0; i < records.size(); i++) {
			results[i] = records.get(i);
		}
		
		return results;
	}
}
