package cn.gloryroad;

import org.testng.annotations.Test;

public class SequenceTest {
	@Test(priority = 2)
	public void test3() {
	}

	@Test(priority = 3)
	public void test4() {
	}

	@Test
	public void test5() {
	}

	@Test(priority = 0)
	public void test1() {
	}

	@Test(priority = 1,enabled=false)
	public void test2() {
		System.out.println("enabled������ʾ���ã�����false��ʾ�����������Ը÷�����ִ��");
	}
}
