package utils;

public class BoardPage {
	
	// static한 멤버는 생성없이 사용 가능하다.
	public static String pagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl) {
		// 나중에 전체 결과를 List.jsp에 전해주기 위해서 사용하는 변수
		String pagingStr = "";
		
		// List.jsp에 정의되어있는 변수들을 매개변수로 받아 사용하는 것.
		// 전체 페이지 수 계산
		int totalPages = (int)(Math.ceil(((double) totalCount / pageSize)));
		
		// 이전 페이지 블록 바로가기 출력
		// pageTemp -> 이전 블럭을 눌렀을 때 시작 번호를 가지게 되는 변수. 현재 블럭의 시작값.
		int pageTemp = (((pageNum - 1) / blockPage) * blockPage) + 1;
		// 1페이지가 아니면
		if(pageTemp != 1) {
			// pageTemp가 1이면 어차피 이전블럭으로 갈 정보가 없기 때문에 화면에 첫 페이지를 출력하게 한다.
			// reqUrl -> board.model2/list.do
			pagingStr += "<a href='" + reqUrl + "?pageNum=1'>[첫 페이지]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp - 1) + "'>[이전 블록]</a>";
		}
		
		// 각 페이지 번호 출력. 한 블럭에 있을 수 있는 번호는 5가 끝임. 그렇기 때문에 blockPage 변수 사용
		int blockCount = 1;
		// 마지막 페이지가 5페이지가 아닐 수도 있기 때문에 전체 페이지보다 작을 때 까지 반복할 수 있도록 구성.
		while (blockCount <= blockPage && pageTemp <= totalPages) {
			if(pageTemp == pageNum) {
				// 현재 페이지는 링크를 걸지 않음
				pagingStr += "&nbsp;" + pageTemp + "&nbsp;";
			}else {
				pagingStr += "&nbsp;<a href='" + reqUrl +"?pageNum=" + pageTemp + "'>" + pageTemp + "</a>&nbsp;";
			}
			pageTemp++; // 1->2->3->4->5->6. 6으로 끝난다.
			blockCount++; //1->2->3->4->5->6
		}
		
		// 다음페이지 블록 바로가기 출력
		if(pageTemp <= totalPages) {
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp + "'>[다음블록]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages + "'>[마지막 페이지]</a>";
		}
		return pagingStr;
	}
}
