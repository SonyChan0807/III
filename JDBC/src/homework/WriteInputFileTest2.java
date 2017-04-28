package homework;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class WriteInputFileTest2 {
	public static void main(String[] args) {
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(SQLInfo.getConnUrl(), SQLInfo.getAccount(), SQLInfo.getPasswd()); 			
			
			String qryStmt = "SELECT * FROM employee";
			PreparedStatement pstmt = conn.prepareStatement(qryStmt);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("res/test.txt", true), "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			StringBuilder sb = new StringBuilder();
			int count = rsmd.getColumnCount();
			
			for (int i = 1 ; i <= count; i++){
				sb.append(rsmd.getColumnLabel(i) + "(" + rsmd.getColumnType(i) + ","
						+ rsmd.getColumnTypeName(i)+"),");
			}
			sb.append("\n");   // next line
			bw.write(sb.toString()); // write header to file
			
			while (rs.next()){
				StringBuilder sb1 = new StringBuilder();      // use StreamBuilder to save memory
				for(int i = 1; i <= count ; i++){
	         		sb1.append(rs.getString(i) + ",");
				}
				sb1.append("\n"); // next line
				bw.write(sb1.toString());        // write each row to file line by line
			}		
			
			bw.close();
		
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
