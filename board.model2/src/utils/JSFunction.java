package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class JSFunction {
	// 메시지 알림창을 띄운 후, 명시한 URL로 이동한다. 
									// 메시지알림창	 이동할 URL	
	public static void alertLocation(String msg, String url, JspWriter out) {
		try {
			String script = "" // 삽입할 자바 스크립트 코드
						  + "<script>"
						  + "	alert('" + msg + "');" // 메시지 출력
						  + "	location.href='" + url + "';" // 페이지 이동
						  + "</script>";
			out.println(script);
		}catch(Exception e) {
			
		}
	}
	
	public static void alertBack(String msg, JspWriter out) {
		try {
			String script = "" 
						  + "<script>"
						  + "	alert('" + msg + "');" // 메시지 출력
						  + "	history.back();"
						  + "</script>";
			out.println(script);
		}catch(Exception e) {
			
		}
	}
	
	public static void alertLocation(HttpServletResponse response, String msg, String url) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String script = "" // 삽입할 자바 스크립트 코드
						  + "<script>"
						  + "	alert('" + msg + "');" // 메시지 출력
						  + "	location.href='" + url + "';" // 페이지 이동
						  + "</script>";
			writer.print(script);
		}catch(Exception e) {
			
		}
	}
	
	public static void alertBack(HttpServletResponse response, String msg) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			String script = "" 
						  + "<script>"
						  + "	alert('" + msg + "');" // 메시지 출력
						  + "	history.back();"
						  + "</script>";
			writer.print(script);
		}catch(Exception e) {
			
		}
	}
	
}
