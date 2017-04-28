package homework4;

public class ReverseStr2 {
	public static void main(String[] args) {
		
		//矩陣swap
		
		String str = "Hello World";
		char[] ch = str.toCharArray();
		
		//swap
		for(int i = 0; i < ch.length; i++){
			if(i > (ch.length - i - 1)){
				char temp = ch[i];
				ch[i] = ch[ch.length - i - 1];
				ch[ch.length - i - 1] = temp;
			}
		}
		System.out.println(String.valueOf(ch));
	}
}
