import java.util.Arrays;
import java.util.List;

public class Homework2_adv3_alt {
	public static void main(String[] args){
		//List<String> list = Arrays.asList("A", "B", "C", "D", "E");
		String[] list = new String[] {"A", "B", "C", "D", "E"};
		for(int i = 0; i < 5; i++){
			//String s = list.get(i);
			String s = list[i];
			for(int j = 0; j <= i; j++){
				System.out.printf("%s",s);
			}
			System.out.println("");
		}
	}
}
