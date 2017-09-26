package cn.gloryroad;

import org.testng.annotations.Test;

public class DependentTest {
	@Test(dependsOnMethods = { "OpenBrowser" })
	public void SingIn() {
		System.out.println("SingIn����������");
	}

	@Test(dependsOnMethods = { "OpenBrowser", "SingIn" })
	public void test123() {
		System.out.println("test123������OpenBrowser��SingIn����ִ�к󱻵���");
	}

	@Test
	public void OpenBrowser() {
		System.out.println("OpenBrowser����������");
	}

	@Test(dependsOnMethods = { "SingIn" })
	public void LogOut() {
		System.out.println("LogOut����������");
	}

}
