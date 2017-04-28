package homework10;

public class IsPrimeTest {
	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			int num = (int) (Math.random() * 100) + 1;
			if (isPrime(num)) {
				System.out.println(num + "是質數");
			} else {
				System.out.println(num + "不是質數");
			}
		}

	}

	public static boolean isPrime(int num) {
		if (num % 2 == 0) {
			return false;
		} else {
			for (int i = 0; i < (int) Math.sqrt(num); i++) {
				if (num % 3 == 0) {
					return false;
				}
			}
		}

		return true;
	}
}
