package homework;

public class SQLInfo {
	private static String connUrl = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
	private static String account = "root";
	private static String passwd = "sony0807";
	
	public static String getAccount() {
		return account;
	}
	public static String getConnUrl() {
		return connUrl;
	}
	public static String getPasswd() {
		return passwd;
	}
	
}
