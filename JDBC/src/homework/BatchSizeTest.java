package homework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class BatchSizeTest {
	public static void main(String[] args) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(SQLInfo.getConnUrl(), SQLInfo.getAccount(), SQLInfo.getPasswd()); 
			
			String qryStmt = "SELECT empno, salary FROM employee";
			PreparedStatement pstmt = conn.prepareStatement(qryStmt);
			ResultSet rs = pstmt.executeQuery();
			
			String updateStmt = "Update employee SET salary=? Where empno=?";
			pstmt = conn.prepareStatement(updateStmt);
			
			int counter = 0;
			
			while (rs.next()){
				pstmt.setDouble(1, rs.getDouble(2) * 1.1);
				pstmt.setInt(2, rs.getInt(1));
				pstmt.addBatch();
				counter++;
				
				if (counter == 3){						// Execute command while count == 3
					pstmt.executeBatch();
					counter = 0;                         // Reset Counter
				}
			}
			pstmt.executeBatch();
			
			qryStmt = "SELECT ename,salary FROM employee";
			pstmt = conn.prepareStatement(qryStmt);
			rs = pstmt.executeQuery();
			while (rs.next()){
				System.out.println("name = " + rs.getString("ename"));
				System.out.println("salary = " + rs.getDouble("salary"));
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
}
