package homework;

public class SqlParser2 {
	public static void main(String[] args) {
		String query = "  select   empno, salary,ename   from   employee  ";

		String aQuery[] = query.trim().split("[ ,]+");

//		for (String s : aQuery) {
//			System.out.println(s);
//		}
		
		System.out.println(aQuery[0]);
		System.out.println(aQuery[aQuery.length -1]);
		
		for (int i = 1; i < aQuery.length - 1 ; i++){
			System.out.println(aQuery[i]);
		}

	}
}
