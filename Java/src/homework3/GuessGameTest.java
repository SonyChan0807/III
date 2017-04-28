package homework3;

import java.util.Scanner;

public class GuessGameTest {

	//Q2: 請設計一隻程式，會亂數產生一個0～9的數字，然後可以玩猜數字遊戲，猜錯會顯示錯誤訊息，猜對則顯示正確訊息
	
	public static void main(String[] args) {

		int guessNum;
		int num = (int) (Math.random() * 9) + 1;
		Scanner console = new Scanner(System.in);

		System.out.println("開始猜數字  0~9");

		while (true) {
			guessNum = console.nextInt();
			if (guessNum == num) {
				System.out.println("猜對囉~~ 答案是 " + num);
				break;
			} else {
				System.out.println("猜錯囉QQ");
			}
		}
		console.close();

	}
}
