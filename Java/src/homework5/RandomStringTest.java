package homework5;

/*身為程式設計師的你，收到一個任務，要幫系統的註冊新增驗證碼的功能，請設計一個方法void genAuthCode()，
當呼叫此方法時，會回傳一個8位數的驗證碼，此驗證碼內容包含了英文大小寫與數字的亂數組合 */



public class RandomStringTest {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(8);

		int i = 0;

		while (i < sb.capacity()) {
			int index = (int) (Math.random() * 299 + 1) % 3;
			char c = ' ';

			switch (index) {   //ASCII code 
			case 0:
				c = (char) (Math.random() * 9 + 48);
				break;
			case 1:
				c = (char) (Math.random() * 26 + 65);
				break;
			case 2:
				c = (char) (Math.random() * 26 + 97);
				break;
			}
			sb.append(c);
			i++;
		}

		System.out.println(sb);

	}
}
