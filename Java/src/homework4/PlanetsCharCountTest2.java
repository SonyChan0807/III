package homework4;

import java.util.Arrays;

/* 
 * 有個字串陣列如下(八大行星)：{“mercury”, “venus”, “earth”, “mars”, “jupiter”,“saturn”, “uranus”, “neptune”}
 * 請用程式計算出這陣列裡面共有多少個母音(a, e, i, o, u)
 * 純Array方法
*/
public class PlanetsCharCountTest2 {
	public static void main(String[] args) {
		char[] vowelArray = { 'a', 'e', 'i', 'o', 'u' };
		String[] planets = { "mercury", "venus", "earth", "mars", "jupiter", "saturn", "uranus", "neptune" };
		int count = 0;

		Arrays.sort(vowelArray);

		for (String p : planets) {
			char[] charArray = p.toLowerCase().toCharArray();
			for (char c : charArray) {
				if (Arrays.binarySearch(vowelArray, c) >= 0) {
					count++;
				}
			}
		}

		System.out.printf("共有%2d個母音", count);

	}
}
