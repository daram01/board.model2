package model2.mvcboard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/view.do")
public class ViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ViewController() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MVCBoardDAO dao = new MVCBoardDAO();
		String idx = request.getParameter("idx"); // 제목을 클릭했을 때 나오는 것 http://localhost:8081/board.model2/view.do?idx=192 
		dao.updateVisitCount(idx); // 조회수 1 증가
		MVCBoardDTO dto = dao.selectView(idx); // 상세보기 화면
		dao.close();
		
		dto.setContent(dto.getContent().replaceAll("\r\n", "<br />"));
		
		request.setAttribute("dto", dto);
		request.getRequestDispatcher("./View.jsp").forward(request, response);
	}

}
