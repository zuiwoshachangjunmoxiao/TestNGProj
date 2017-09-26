package cn.gloryroad;

import org.testng.annotations.Test;

public class Grouping {
	@Test(groups = { "人" })
	public void student() {
		System.out.println("学生方法被调用");
	}

	@Test(groups = { "人" })
	public void teacher() {
		System.out.println("老师方法被调用");
	}

	@Test(groups = { "动物" })
	public void cat() {
		System.out.println("小猫方法被调用");
	}

	@Test(groups = { "动物" })
	public void dog() {
		System.out.println("小狗方法被调用");
	}

	@Test(groups = { "人", "动物" })
	public void feeder() {
		System.out.println("饲养员方法被调用");
	}
}
