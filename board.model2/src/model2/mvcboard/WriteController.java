package model2.mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

@WebServlet("/write.do") // 이런 이름으로 요청이 들어오면
public class WriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public WriteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("./Write.jsp").forward(request, response);
									// 이 페이지로 이동이 된다.
	} // doGet끝

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 업로드 디렉터리의 물리적 경로 확인
		String saveDirectory = request.getServletContext().getRealPath("/Uploads");
		// 초기화 매개변수로 설정한 첨부 파일 최대 용량 확인
		//ServletContext application = getServletContext();
		int maxPostSize = 1024 * 1000;
		
		MultipartRequest mr = FileUtil.uploadFile(request, saveDirectory, maxPostSize);
		if(mr == null) { // 파일이 제대로 안 올라온 경우임
			JSFunction.alertLocation(response, "첨부파일이 제한 용량을 초과합니다.", "/write.do");
		}
		
		// 파일이 정상적으로 올라왔으면, dto 객체에 저장
		MVCBoardDTO dto = new MVCBoardDTO();
		dto.setName(mr.getParameter("name")); // 작성자
		dto.setTitle(mr.getParameter("title")); // 제목
		dto.setContent(mr.getParameter("content")); // 내용
		dto.setPass(mr.getParameter("pass")); // 비밀번호 
		
		// 원본 파일명과 저장된 파일 이름 설정
		String fileName = mr.getFilesystemName("ofile");
		if(fileName != null) {
			// 새로운 파일명 생성
			String ext = fileName.substring(fileName.lastIndexOf(".")); // 파일 확장자
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String newFileName = now + ext; // 새로운 파일 이름 ("업로드일시.확장자")
			
			// 3. 파일명 변경
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);
			
			dto.setOfile(fileName); // 원래 파일 이름
			dto.setSfile(newFileName); // 서버에 저장된 파일 이름
		}
		
		MVCBoardDAO dao = new MVCBoardDAO();
		int result = dao.insertWrite(dto);
		dao.close();
		
		if(result == 1) {
			response.sendRedirect("list.do");
		} else {
			response.sendRedirect("write.do");
		}
		
	} // doPost끝

} // WriteController끝
