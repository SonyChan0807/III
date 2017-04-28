package homework7;

import java.io.Serializable;

public class Dog extends Animals implements Serializable, ISpeak {

	private static final long serialVersionUID = 164398037973483615L;
	private String name;
	
	public Dog(String name) {
		this.name = name;
	}
	
	@Override  //父類別方法
	public void speak() {
		System.out.println("This is Dog " + name + " speaking.");
	}
	
	@Override  //介面方法
	public void speak2() {
		System.out.println("This is Dog " + name + " speaking!");
	}
}
