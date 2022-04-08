package studentProgram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtility {
	
	// 데이터베이스 접속
	public static Connection getConnection() {
		Connection con = null;
		FileReader fr = null;
		
		try {
			// db.properties 파일 읽어오기
			fr = new FileReader("src\\studentProgram\\db.properties");
			Properties properties = new Properties();
			properties.load(fr);
			
			String DRIVER = properties.getProperty("DRIVER");
			String URL = properties.getProperty("URL");
			String userID = properties.getProperty("userID");
			String userPassword = properties.getProperty("userPassword");
			
			// 드라이버 적재
			Class.forName(DRIVER);
			// 데이터베이스 연결
			con = (Connection) DriverManager.getConnection(URL, userID, userPassword);
			
		} catch (FileNotFoundException e) {
			System.out.println("db.properties File Not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("db.properties File Connection Failed");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("mySQL Database Connection Failed");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("mySQL Database Connection Failed");
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return con;
	}
	
}