package cn.gloryroad;

import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestngReporter {
  @Test
  public void OpenBrowser() {
	  System.out.println("OpenBrowser 方法被调用");
	  Reporter.log("调用打开浏览器的方法");
  }
  @Test
  public void SingIn() {
	  System.out.println("SingIn 方法被调用");
	  Reporter.log("调用登录方法");
  }  
  @Test
  public void LogOut() {
	  System.out.println("LogOut 方法被调用");
	  Reporter.log("调用注销方法");
  }
}
