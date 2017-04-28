
public class Homework2_adV1 {
	public static void main(String[] agrs){
		
		int choice = 0;
		for(int i = 1; i < 50; i++){
			if((i % 10.0) == 4 || (i / 10.0) == 4) {
				continue;
			}else{
				System.out.printf("%d ",i);
				choice++;
			}
		}
		System.out.print("阿文可以選擇的數字總共有 " + choice);
	}
}
