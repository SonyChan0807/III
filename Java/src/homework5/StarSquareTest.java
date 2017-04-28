package homework5;

import java.util.Scanner;

//請設計一個方法為starSquare(int width, int height)，當使用者鍵盤輸入寬與高時，即會印出對應的*長方形

public class StarSquareTest {

	public static void main(String[] args) {
		int width = 0;
		int height = 0;

		System.out.println("請輸入寬與高");
		Scanner console = new Scanner(System.in);
		if (console.hasNext()) {
			width = console.nextInt();
		}
		if (console.hasNext()) {
			height = console.nextInt();
		}

		console.close();

		starSquare(width, height);
	}

	public static void starSquare(int width, int height) {

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}

}
