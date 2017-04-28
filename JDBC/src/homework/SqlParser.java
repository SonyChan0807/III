package homework;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {
	public static void main(String[] args) {

		String queryStr = "  Select empno, salary, name from employee  ";
		// String queryStr = " select * from employee";
		String regEx = "\\s*(select)\\s*"
				+ "(\\w+\\s*|\\*|\\w+\\s*(,\\s*\\w+)*)" 
				+ "\\s*from\\s*(\\w+)\\s*";

		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(queryStr);

		if (m.matches()) {
			System.out.println(m.group(1).toLowerCase());   	// group1: select
			System.out.println(m.group(4).toLowerCase());		// group4: table name

			String array[] = m.group(2).split(","); // group2: column names  note: ignore group3

			for (String s : array) {
				System.out.println(s.trim().toLowerCase());
			}

		} else {
			System.out.println("Invalid format or syntax error!");
		}

	}

}
