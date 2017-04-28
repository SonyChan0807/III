package homework3;

import java.util.Scanner;

public class GuessGameAdvTest {
	
	//Q2 Adv:進階挑戰：產生0～100亂數，每次猜就會提示你是大於還是小於正確答案
	
	public static void main(String[] args) {
	
		int guessNum;
		int num = (int) (Math.random() * 100) + 1;
		Scanner console = new Scanner(System.in);
		System.out.println("開始猜數字");

		while (true) {
			guessNum = console.nextInt();
			if (guessNum == num) {
				System.out.println("猜對囉~~ 答案是 " + num);
				break;
			} else {
				if (guessNum < num) {
					System.out.println("猜錯囉QQ 答案比較大");
				} else {
					System.out.println("猜錯囉QQ 答案比較小");
				}
			}
		}
		console.close();

	}
}