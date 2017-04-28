package homework3;

import java.util.Scanner;

public class LottoryTest {

	/*
	 * Q3: 阿文很喜歡簽大樂透(1～49)，但他是個善變的人，上次討厭數字是4，但這次他想要依心情決定討厭哪個數字，
	 * 請您設計一隻程式，讓阿文可以輸入他不想要的數字，畫面會顯示他可以選擇的號碼與總數
	 */

	public static void main(String[] args) {

		int hateNum;

		System.out.println("阿文 請輸入你討厭的數字");
		Scanner console = new Scanner(System.in);

		while (true) {

			hateNum = console.nextInt();

			if (hateNum <= 9 && hateNum >= 0) {
				break;
			} else {
				System.out.println("超出範圍@@ 再輸入一次.....");
			}
		}

		console.close();

		int choice = 0;
		for (int i = 1; i <= 49; i++) {
			if ((i % 10.0) == hateNum || (i / 10.0) == hateNum) {  // 10.0 強制轉型 處理輸入等於0
				continue;
			} else {
				choice++;
				if (i != hateNum) {
					if (choice % 6 != 0) {
						System.out.printf("%2d ", i);
					} else {
						System.out.printf("%2d%n", i);
					}
				}
			}
		}
		System.out.print("阿文可以選擇的數字總共有 " + choice);
	}

}
