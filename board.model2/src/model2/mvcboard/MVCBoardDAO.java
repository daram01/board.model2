package model2.mvcboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.DBConnPool;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자
public class MVCBoardDAO extends DBConnPool{
	// 현재 db에 몇개의 게시물이 있는지 확인
	
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		String query = "select count(*) from mvcboard"; // 전체 게시글 구해오는 쿼리문
		
		// 검색어가 있을 때의 쿼리문을 if문으로 작성. 검색어가 없으면 위의 쿼리문만 실행된다.
		if(map.get("searchWord") != null) {
			query += " where " + map.get("searchField") + " "
					+ " like '%" + map.get("searchWord") + "%'";
		}
		
		try {
			stmt = con.createStatement(); // 정적 쿼리문 생성
			rs = stmt.executeQuery(query); // 쿼리문 실행
			rs.next(); // 조회할 때 컬럼명 다음 즉 데이터를 조회하기 위해 next() 함수 사용.
			totalCount = rs.getInt(1); // 검색된 게시물 개수 저장. 
		} catch (Exception e) {
			System.out.println("게시물 카운트 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount;
	}
	
	
	
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map){
		List<MVCBoardDTO> board = new ArrayList<>();
		
		String query = " "
					+ " select * from ( "
					+ " select Tb.*, rownum rNum from ( "
					+ " select * from mvcboard ";
	
	
		if(map.get("searchWord") != null) {
			query += " where " + map.get("searchField")
					+ " like '%" + map.get("searchWord") + "%' ";
		}
		
		query += " order by idx desc "
				+ " ) Tb "
				+ " ) "
				+ " where rNum between ? and ?";
				// 페이지에 따라 넘어가는 값이 다르기 때문에 ? 로 동적 쿼리문을 만든다.
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, map.get("start").toString()); // 위의 쿼리문 중 1번 물음표
			psmt.setString(2, map.get("end").toString()); // 위의 쿼리문 중 2번 물음표
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
				
				board.add(dto);
			}
			
		} catch(Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		
		return board;
	}
	
	public int insertWrite(MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "insert into mvcboard ( "
					+ " idx, name, title, content, ofile, sfile, pass) "
					+ " values ( "
					+ " seq_board_num.nextval, ?, ?, ?, ?, ?, ?)";
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());
			result = psmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateVisitCount(String idx) {
		String query = "update mvcboard set "
					+ " visitcount=visitcount+1 "
					+ " where idx=?";
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeQuery();
			
		} catch(Exception e) {
			System.out.println("조회수 카운트 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	public MVCBoardDTO selectView(String idx) {
		MVCBoardDTO dto = new MVCBoardDTO();
		String query = " select * from mvcboard where idx=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}
			
			
			
		} catch(Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto;
	}
	
	
	public void downCountPlus(String idx) {
		String sql = "update mvcboard set "
					+ " downcount=downcount+1 " 
					+ " where idx=?";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, idx);
			psmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("다운로드 조회수 반영 중 예외 발생");
		}
	}
	
	
	
} // DAO 끝
