package common;
import java.sql.*;

import javax.servlet.ServletContext;

public class JDBConnect {
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;

	public JDBConnect() {
		
		String url = "jdbc:oracle:thin:@localhost:1521:orcl"; // db주소를 변수 url에 담았음.
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 이것이 없으면 오라클 데이터베이스를 사용할 수 없음.
			con = DriverManager.getConnection(url, "musthave", "1234"); // 연결 DriverManager.getConnection(DB주소, "아이디", "비밀번호");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public JDBConnect(String driver, String url, String id, String pwd) {
		try{
			Class.forName(driver); 
			con = DriverManager.getConnection(url, id, pwd); 
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public JDBConnect(ServletContext application) { // 오버로딩 생성자
		try{
			String driver = application.getInitParameter("OracleDriver");
			Class.forName(driver); 
			
			String url = application.getInitParameter("OracleURL");
			String id = application.getInitParameter("OracleId");
			String pwd = application.getInitParameter("OraclePwd");
			con = DriverManager.getConnection(url, id, pwd);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public void close() { // 닫히는 기능을 따로 메소드로 빼줌.
		try{ // 순서 중요함. rs -> psmt, stmt (두개는 순서 상관 없음) -> con 순서로 닫히게 해야함
			if (rs != null) {rs.close();}
			if (stmt != null) {stmt.close();}
			if (psmt != null) {psmt.close();}
			if (con != null) {con.close();}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}