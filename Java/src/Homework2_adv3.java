
public class Homework2_adv3 {

	public static void main(String[] args){
		String s = "";
		for(int i = 1; i <= 5; i++){
			
			// 依照每行切換字元
			switch (i) {
			case 1:
				s = "A";
				break;
			case 2:
				s = "B";
				break;
			case 3:
				s = "C";
				break;
			case 4:
				s = "D";
				break;
			case 5:
				s = "E";
				break;
			}
			
			for(int j = 1; j <= i; j++){
				System.out.printf("%s",s);
			}
			System.out.println("");
		}
		
	}
}
