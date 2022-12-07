package common;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPool {
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	
	// 기본 생성자
	public DBConnPool() {
		try {
			// 커넥션 풀(DataSource) 얻기
			Context initCtx = new InitialContext();
			Context ctx = (Context)initCtx.lookup("java:comp/env"); // context.xml 파일에 적은 네임속성값
			DataSource source = (DataSource)ctx.lookup("dbcp_myoracle");
			
			// 커넥션 풀을 통해 연결 얻기
			con = source.getConnection(); 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void close() { // 닫히는 기능을 따로 메소드로 빼줌.
		try{
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(psmt != null) {psmt.close();}
			if(con != null) {con.close();}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
