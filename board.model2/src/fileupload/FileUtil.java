package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {
	
	// 파일 업로드 기능
	public static MultipartRequest uploadFile(HttpServletRequest request, String saveDirectory, int maxPostSize) {
		try {
		
			return new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8");
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 파일 다운로드 기능
	public static void download(HttpServletRequest request, HttpServletResponse response, 
								String directory, String sfileName, String ofileName) {
		String sDirectory = request.getServletContext().getRealPath(directory); 
		// 자바에서는 바로 application 객체 사용을 못하기 때문에 request 객체를 통해서 얻어와야 한다.
		try{
			File file = new File(sDirectory, sfileName); // File(경로, 파일명)
			InputStream iStream = new FileInputStream(file); // 해당 파일을 찾아 입력 스트림을 만든다.
			
			// 한글 파일명 깨짐 방지
			String client = request.getHeader("User-Agent"); // 클라이언트가 요청을 하면 헤더부분에 브라우저 정보도 같이 넘어오기 때문에 getHeader로 브라우저 정보를 받는다.
			if(client.indexOf("WOW64") == -1){
				ofileName = new String(ofileName.getBytes("UTF-8"),"ISO-8859-1");
			}else{
				ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			// 파일 다운로드용 응답 헤더 설정
			response.reset(); // 응답 헤더 초기화. 이전에 다운로드 했을 때의 정보가 남아있을 수 있기 때문에 리셋.
			response.setContentType("application/octet-stream"); // 8비트 단위의 데이터
			response.setHeader("Content-Disposition", "attachment; filename=\"" + ofileName + "\""); // 다운로드될 때 보여질 이름
			response.setHeader("Content-Length", "" + file.length()); // 다운로드 될 때 파일의 크기를 알려주는 것
			
			OutputStream oStream = response.getOutputStream(); // 서버-> 클라이언트 쪽으로 
			
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while ( (readBuffer = iStream.read(b)) > 0 ) {
				oStream.write(b, 0, readBuffer);
			}
			
			// 사용후에는 항상 입-출력 스트림을 닫아야 한다.
			iStream.close();
			oStream.close();

		}catch(FileNotFoundException e){
			System.out.println("파일을 찾을 수 없습니다.");
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("예외가 발생하였습니다.");
			e.printStackTrace();
		}

	}

}
