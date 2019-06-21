package jdbc_study.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;

public class MySQLjdbcUtil {
	
//	public static void main(String[] args) throws SQLException {
//		Connection con = getConnection();
//		System.out.println(con);
//		System.out.println(con.getCatalog());
//	}
	
	public static Connection getConnection() {
		Connection con = null;
		try(InputStream is =  ClassLoader.getSystemResourceAsStream("db.properties")){ // db.properties파일에 들어가있는 값을 읽어옴.
			Properties prop = new Properties(); // hashmap과 비슷함. key:value형태로 값을 저장.
			prop.load(is); // prop에 is를 읽어들임. key:value형태로 저장.
			
			for(Entry<Object, Object> e : prop.entrySet()){ // entryset은 key:value 형태로 prop의 값을 가지고와서 e에 차례대로 대입.
				System.out.printf("%s - %s%n", e.getKey(), e.getValue());
			}
			//데이터베이스에 연결
			con = DriverManager.getConnection(prop.getProperty("url"), prop); // prop의 url을 불러와서 연결.
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (SQLException e1) {
			System.err.println("해당 데이터베이스 연결정보가 잘못되었슴 확인요망");
		}
		return con; // 연결된 결과를 리턴.
	}
}
