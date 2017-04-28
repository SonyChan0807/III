package homework4;

public class ReverseStr3 {
	public static void main(String[] args) {
		
		//charAt()
		
		String str = "Hello World";
		char[] reStr = new char[str.length()];
		
		for(int i = 0; i < reStr.length; i++){
			reStr[i] = str.charAt(str.length()- i - 1);
		}		
		System.out.println(String.valueOf(reStr));
	}
}
