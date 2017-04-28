package homework6;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalTest {

	public static void main(String[] args) {

		int x = 0;
		int y = 0;
		
		Scanner console = new Scanner(System.in);
		while (true) {
			
			try {
				System.out.println("請輸入x:");
				//x = intParser(console.next());  
				x = Integer.parseInt(console.next());
				System.out.println("請輸入y:");
				//y = intParser(console.next());
				y = Integer.parseInt(console.next());
		
				
				if (x == 0 && y == 0) {
					throw new CalException("x與y同時為0");
				} else if (y < 0) {
					throw new CalException("y為負值，而導致x的y次方結果不為整數");
				} else {
					System.out.printf("%d的%d的次方等於%d%n", x, y, powerXY(x, y));
					break;
				}

			} catch (CalException e) {
				System.out.println(e.getMessage());
			} catch(InputMismatchException ime){
				System.out.println("輸入格試錯誤");
			}
		}
		console.close();
	}

	
	public static int powerXY(int x, int y) {
		return (int) Math.pow(x, y);
	}
		
}
