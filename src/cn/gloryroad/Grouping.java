package cn.gloryroad;

import org.testng.annotations.Test;

public class Grouping {
	@Test(groups = { "��" })
	public void student() {
		System.out.println("ѧ������������");
	}

	@Test(groups = { "��" })
	public void teacher() {
		System.out.println("��ʦ����������");
	}

	@Test(groups = { "����" })
	public void cat() {
		System.out.println("Сè����������");
	}

	@Test(groups = { "����" })
	public void dog() {
		System.out.println("С������������");
	}

	@Test(groups = { "��", "����" })
	public void feeder() {
		System.out.println("����Ա����������");
	}
}
