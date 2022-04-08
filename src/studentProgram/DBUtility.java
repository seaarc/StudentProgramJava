package studentProgram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtility {
	
	// �����ͺ��̽� ����
	public static Connection getConnection() {
		Connection con = null;
		FileReader fr = null;
		
		try {
			// db.properties ���� �о����
			fr = new FileReader("src\\studentProgram\\db.properties");
			Properties properties = new Properties();
			properties.load(fr);
			
			String DRIVER = properties.getProperty("DRIVER");
			String URL = properties.getProperty("URL");
			String userID = properties.getProperty("userID");
			String userPassword = properties.getProperty("userPassword");
			
			// ����̹� ����
			Class.forName(DRIVER);
			// �����ͺ��̽� ����
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