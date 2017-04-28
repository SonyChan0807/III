package homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadPhotoTest {
	public static void main(String[] args) {
		
		File file = new File("images");
		String[] images = file.list();      // get all images in folder
//		System.out.println(images[0]);

		FileInputStream fis = null;
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(SQLInfo.getConnUrl(), SQLInfo.getAccount(), SQLInfo.getPasswd());
			
			
			for(String s : images){
				
				int empno = Integer.valueOf(s.substring(0,4));   // retrieve empno from file name XXXX.gif
				
				String qryStmt = "SELECT photo FROM employee1 WHERE empno = ?";     /// delete original photo if exits
				PreparedStatement pStmt = conn.prepareStatement(qryStmt);
				pStmt.setInt(1,empno);
				ResultSet rs = pStmt.executeQuery();
				
				if (rs.next()){
					String deleteStmt = "Update employee1 SET photo=? Where empno=?";
					PreparedStatement dStmt = conn.prepareStatement(deleteStmt);
					dStmt.setString(1, "");
					dStmt.setInt(2, empno );
					dStmt.executeUpdate();
				}
				
				fis = new FileInputStream(file + "/" + s);							// update the latest photo
				String insertStmt = "Update employee1 SET photo=? where empno=?";
				pStmt = conn.prepareStatement(insertStmt);
				pStmt.setBinaryStream(1, fis, fis.available());
				pStmt.setInt(2, empno );
				pStmt.executeUpdate();
				System.out.println("Insert photo successfully id:" + empno);
				
			}
			
		} catch (IOException ie){
			ie.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		} finally {
			if (conn != null){
				try{
					conn.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
