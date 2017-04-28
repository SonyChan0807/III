package homework7;

import java.io.Serializable;

public class Cat extends Animals implements Serializable, ISpeak {
	

	private static final long serialVersionUID = -1586441821909682518L;
	private String name;
	
	public Cat(String name) {
		this.name = name;
	}
	
	@Override  //父類別方法
	public void speak() {
		System.out.println("This is Cat " + name + " speaking!");
		
	}	
	
	@Override //介面方法
	public void speak2() {
		System.out.println("This is Cat " + name + " speaking!");
	}
}
