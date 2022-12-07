package model2.mvcboard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.BoardPage;

@WebServlet("/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// db에서 데이터를 가져오기위해 dao객체 생성
		MVCBoardDAO dao = new MVCBoardDAO(); 
		
		// 뷰에 전달할 매개변수 저장용 맵 생성
		Map<String, Object> map = new HashMap<>();
		String searchField = request.getParameter("searchField"); // 사용자에게로부터 넘어온 값을 request를 통해 받아와서 변수로 저장.
		String searchWord = request.getParameter("searchWord");
		
		if(searchWord != null) { // 사용자가 검색어를 입력한 것
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		
		int totalCount = dao.selectCount(map); 	
		
		// 페이징
		ServletContext application =getServletContext(); // appication 객체 얻기 
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE")); // 기본이 문자열로 반환하기 때문에 parseInt 필요. 문자 -> 숫자
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));
		int pageNum = 1; // 1페이지를 기본값으로
		String pageTemp = request.getParameter("pageNum");
		if(pageTemp != null && !pageTemp.equals("")) {
			pageNum = Integer.parseInt(pageTemp);
		}
		
		int start = (pageNum - 1) * pageSize + 1;
		int end = pageNum * pageSize;
		map.put("start", start);
		map.put("end", end);
		
		List<MVCBoardDTO> boardLists = dao.selectListPage(map); 
		dao.close();
		
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "./list.do");
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		request.setAttribute("boardLists", boardLists);
		request.setAttribute("map", map);
		request.getRequestDispatcher("./List.jsp").forward(request, response);
		
	}

	
	
} // List 끝
