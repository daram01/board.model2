<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>파일 첨부형 게시판</title>
<style>a{text-decoration: none;}</style>
</head>
<body>
	<h2>파일 첨부형 게시판 - 목록보기(List)</h2>
	<!-- 검색 폼 -->
	<form method="get"> <!-- action="http://localhost:8081/board.model2/list.do" 가 생략된것임.-->
	<table border="1" width="90%">
	<tr>
		<td align="center">
			<select name="searchField">
				<option value="title">제목</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchWord" />
			<input type="submit" value="검색하기" />
		</td>
	</tr>	
	</table>
	</form>
	
	<!-- 목록 테이블 -->
	<table border="1" width="90%">
		<tr align="center">
			<td width="10%">번호</td>
			<td width="*">제목</td>
			<td width="15%">작성자</td>
			<td width="10%">조회수</td>
			<td width="15%">작성일</td>
			<td width="8%">첨부</td>
		</tr>
		
		
	<c:choose>
		<c:when test="${ empty boardLists }">
			<tr>
				<td colspan="6" align="center">
					등록된 게시물이 없습니다.
				</td>
			</tr>
		</c:when>

		<c:otherwise>
			<c:forEach items="${ boardLists }" var="row" varStatus="loop">
				<tr align="center">
					<td> ${ map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index)} </td>
					<td align="left"><a href="view.do?idx=${ row.idx }">${ row.title }</a></td> <!-- 제목(클릭하면 상세보기) -->
					<td>${ row.name }</td>
					<td>${ row.visitcount }</td>
					<td>${ row.postdate }</td>
					<td>
					<c:if test="${ not empty row.ofile }">
					<a href="download.do?ofile=${ row.ofile }&sfile=${ row.sfile }&idx=${ row.idx }">[DOWN]</a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	</table>
	
	
	<!-- 하단메뉴(페이징, 글쓰기) -->
	<table border="1" width="90%">
		<tr align="center">
			<td>${ map.pagingImg }</td>
			<td width="100"><button type="button" onclick="location.href='write.do';">글쓰기</button></td>
		</tr>
	</table>
</body>
</html>