package homework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchInsertTest {
	public static void main(String[] args) {

		

		try (FileInputStream fis = new FileInputStream("res/emp.txt");				// close all resources automatically
				BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
				Connection conn = DriverManager.getConnection(SQLInfo.getConnUrl(), SQLInfo.getAccount(),
						SQLInfo.getPasswd());) {

			String inserStmt = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(inserStmt);

			String str = "";
			int counter = 0;
			while ((str = br.readLine()) != null) {				//set up prepared statement line by line

				System.out.println(str);
				
				String[] strs = str.trim().split(",");

				pstmt.setInt(1, Integer.valueOf(strs[0].trim()));
				pstmt.setString(2, strs[1].trim());
				pstmt.setString(3, strs[2].trim());
				pstmt.setDouble(4, Double.valueOf(strs[3].trim()));
				pstmt.setInt(5, Integer.valueOf(strs[4].trim()));
				pstmt.setString(6, strs[5].trim());
				pstmt.addBatch();

				counter++;										// execute batch update per 2 times
				if (counter == 2) {
					pstmt.executeBatch();
					counter = 0;
				}
			}

			pstmt.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}

	}
}
