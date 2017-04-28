package homework5;

// 請設計一個類別MyRectangle

public class MyRectangleTest {
	public static void main(String[] args){
		
		
		// Setter
		MyRectangle mr1 = new MyRectangle();
		mr1.setWidth(10);
		mr1.setDepth(20);
		System.out.println(mr1.getArea());
		
		// Constructor
		MyRectangle mr2 = new MyRectangle(10, 20);
		System.out.println(mr2.getArea());
		
	}
}
