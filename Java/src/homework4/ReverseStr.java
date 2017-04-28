package homework4;

public class ReverseStr {
	public static void main(String[] args) {

		//StringBuilder practice
		String str = "Hello World";
		char[] sArray = str.toCharArray();

//		StringBuilder sb1 = new StringBuilder(str);
//		sb1 = sb1.reverse();

		StringBuilder sb = new StringBuilder(sArray.length);

		for (int i = 0; i < sArray.length; i++) {
			sb.append(sArray[sArray.length - i - 1]);
		}
		System.out.println(sb);
		
	}
}
