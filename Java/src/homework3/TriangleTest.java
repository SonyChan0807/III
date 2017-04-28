package homework3;

import java.util.Scanner;

public class TriangleTest {

	// Q1:請設計一隻程式，使用者輸入三個數字後，輸出結果會為正三角形、等腰三角形、其它三角形或不是三角形 + 加入直角三角形的判斷

	public static void main(String[] args) {

		double[] inputNum = new double[3];
		System.out.println("請輸入三個數字");
		Scanner console = new Scanner(System.in);
		
		// Input存成Array
		int i = 0;
		while (i < inputNum.length) {
			inputNum[i] = console.nextDouble();
			i++;
		}
		console.close();

		Triangle triangle = new Triangle(inputNum);

		if (triangle.isTriangle()) {
			System.out.println(triangle.getTriangleType()); // 判斷三角形
			System.out.println(triangle.getRightTri()); // 判斷直角三角形
		} else {
			System.out.println("不是三角形");
		}

	}

}
