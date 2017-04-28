package homework4;

import java.util.Arrays;
import java.util.List;

public class PlanetsCharCountTest {

	/* 
	 * 有個字串陣列如下(八大行星)：{“mercury”, “venus”, “earth”, “mars”, “jupiter”,“saturn”, “uranus”, “neptune”}
	 * 請用程式計算出這陣列裡面共有多少個母音(a, e, i, o, u)
	 * List API practice
	*/
	
	static List<String> vowelArray = Arrays.asList("a", "e", "i", "o", "u");

	public static void main(String[] args) {
		int count = 0;
		String[] planets = new String[] { "mercury", "venus", "earth", "mars", "jupiter", "saturn", "uranus",
				"neptune" };

		for (String s : planets) {
			count = count + countChar(s);
		}
		System.out.printf("共有%2d個母音", count);
	}

	public static Integer countChar(String s) {
		int count = 0;
		char[] charArray = s.toCharArray();

		for (char ch : charArray) {
			if (vowelArray.indexOf(String.valueOf(ch).toLowerCase()) != -1) {
				count += 1;
			}
		}
		return count;
	}

}
