package cn.gloryroad;

import org.testng.annotations.Test;

public class DependentTest {
	@Test(dependsOnMethods = { "OpenBrowser" })
	public void SingIn() {
		System.out.println("SingIn方法被调用");
	}

	@Test(dependsOnMethods = { "OpenBrowser", "SingIn" })
	public void test123() {
		System.out.println("test123方法在OpenBrowser和SingIn方法执行后被调用");
	}

	@Test
	public void OpenBrowser() {
		System.out.println("OpenBrowser方法被调用");
	}

	@Test(dependsOnMethods = { "SingIn" })
	public void LogOut() {
		System.out.println("LogOut方法被调用");
	}

}
