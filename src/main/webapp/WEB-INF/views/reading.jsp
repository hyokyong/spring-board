<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="http://netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<title>bbs1</title>
<style>
    label { margin-top: 20px; }
</style>

</head>
<body>

<%@ include file="/WEB-INF/views/include/menu.jsp" %>

<div class="container">

<form:form method="post" modelAttribute="c">

	<!-- 본문 -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>${ article.a_title }</th>
                <th>${ article.u_name }</th>
                <th>${ article.timestamp }</th>
                <th>${ article.a_click }</th>
            </tr>
        </thead>
        <tbody>
                <td>${ article.a_content }</td>
            </tr>
        </tbody>
    </table>
    
    <!-- 댓글목록 -->
    <table class="table table-bordered">
            <tbody>
            <c:forEach var="comment" items="${ list }">
                <tr>
                    <td>${ comment.u_name }</td>
                    <td>${ comment.timestamp }</td>
                    <c:if test= "${ comment.c_writer == u_id }">
                    	<c:if test="${ c.c_id != comment.c_id }"> <!-- 댓글수정 안눌렀을때만 -->
                    		<td><a href="reading.do?aid=${ comment.a_id }&cid=${ comment.c_id }&${ pagination.queryString }" class="btn">수정</a></td>
                    		<td><a href="delete.do?aid=${ comment.a_id }&cid=${ comment.c_id }&${ pagination.queryString }" class="btn">삭제</a></td>
                    	</c:if>
                    </c:if>
                </tr>
                <tr>
                	<c:if test="${ c.c_id == 0 }"> <!-- 댓글수정 안눌렀을때 기본화면 -->   
                    	<td>${ comment.c_content }</td>
                    </c:if>
                    <c:if test="${ c.c_id != 0 }"> <!-- 댓글수정 눌렀을때 -->
                    	<c:if test="${ c.c_id != comment.c_id }">
                    		<td>${ comment.c_content }</td>
                    	</c:if>
                    	<c:if test="${ c.c_id == comment.c_id }"> <!-- 댓글수정  -->
                    		<td><form:textarea path="c_content"/></td>
                    		<td><input type="submit" class="btn btn-primary" value="수정" /></td>
                    		<td><a href="reading.do?aid=${ c.a_id }&cid=0&${ pagination.queryString }" class="btn">취소</a></td>
                    	</c:if>
                   </c:if>
                </tr>
            </c:forEach>
            </tbody>
    </table>

 	<!-- 댓글등록 -->
 		<c:if test="${ c.c_id == 0 }">
        	<form:textarea path="c_content"/>
        	<div>
            	<input type="submit" class="btn btn-primary" value="등록" />
        	</div>
        </c:if>
</form:form>
    
     	<div>
     		<c:if test="${ u_id == article.a_writer }">
     	 		<a href="edit.do?aid=${ article.a_id }&${ pagination.queryString }" class="btn">수정</a>
     			<a href="delete.do?aid=${ article.a_id }&cid=0&${ pagination.queryString }" class="btn">삭제</a>
     		</c:if>
            <a href="write.do" class="btn">글쓰기</a>
            <a href="list.do?${ pagination.queryString }" class="btn">목록</a>
    	</div>
   


  <!--  <c:if test="${ not empty error }">
        <div class="alert alert-error">${ error }</div>
    </c:if>
    <c:if test="${ not empty success }">
        <div class="alert alert-success">${ success }</div>
    </c:if>   -->

	<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</div>
</body>
</html>
