package homework;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class WriteInputFileTest {
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(SQLInfo.getConnUrl(), SQLInfo.getAccount(), SQLInfo.getPasswd()); 			
			
			String qryStmt = "SELECT * FROM employee";
			PreparedStatement pstmt = conn.prepareStatement(qryStmt);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
//			FileWriter fw = new FileWriter("res\\text2.txt");
//			BufferedWriter bw = new BufferedWriter(fw);
//			PrintWriter pw = new PrintWriter(bw);
			
			
			PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream("res\\text2.txt", true)));
			
			
			int count = rsmd.getColumnCount();
			
			for (int i = 1 ; i <= count; i++){
				pw.print(rsmd.getColumnLabel(i) + "(" + rsmd.getColumnType(i) + ", "
						+ rsmd.getColumnTypeName(i)+"), ");
			}
			pw.println();   // next line
			
			while (rs.next()){
				for(int i = 1; i <= count ; i++){
					pw.print(rs.getString(i) + ", ");
				}
				pw.println();; // next line
			}		
			pw.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ie) { 
			ie.printStackTrace();
		}
		finally {
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
}
