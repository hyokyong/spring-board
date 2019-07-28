<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pj1</title>
<link href="http://maxcdn.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<style>
    thead tr { background: #eee; }
    tbody tr:hover { background-color: #ffa; cursor: pointer; }
    div.form-inline { margin-bottom: 5px; }
</style>
<script>
    $(function() {
        $("tbody tr").click(function() {
            location.href = $(this).attr("data-url");
        });
        $("div.pagination a").click(function() {
            $("input[name=pg]").val($(this).attr("data-page"));
            $("form").submit();
        });
    });
</script>


</head>

<body>
<%@ include file="/WEB-INF/views/include/menu.jsp" %>

<div class="container">

<c:if test="${ pagination.bd == 1 }">
        <h1>공지사항</h1>
</c:if>
<c:if test="${ pagination.bd == 2 }">
        <h1>자유게시판</h1>
</c:if>
<c:if test="${ pagination.bd == 3 }">
        <h1>사원목록</h1>
</c:if>
<hr />

<form:form method="get" modelAttribute="pagination">
    <input type="hidden" name="pg" value="1" /> <!-- 페이지네이션에서 페이지 바뀌게 하기 위함 -->
    <input type="hidden" name="bd" value="${ pagination.bd }" />

    <c:if test="${ pagination.bd == 1 || pagination.bd == 2 }">
    
	<table class="table table-bordered">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="article" items="${ list }">
                <tr data-url="reading.do?aid=${ article.a_id }&cid=0&${ pagination.queryString }">
                    <td>${ article.a_id }</td>
                    <td>${ article.a_title }</td>
                    <td>${ article.u_name }</td>
                    <td>${ article.timestamp }</td>
                    <td>${ article.a_click }</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <a href="write.do?${ pagination.queryString }" class="btn">글쓰기</a>
    
    <div class="pagination pagination-small pagination-centered">
        <ul>
            <c:forEach var="page" items="${ pagination.pageList }">
                <li class='${ page.cssClass }'><a data-page="${ page.number }">${ page.label }</a></li>
            </c:forEach>
        </ul>
    </div>
    
    <div class="form-inline">
        <form:select path="ss">
            <form:option value="0" label="검색조건" />
            <form:option value="1" label="제목+내용" />
            <form:option value="2" label="작성자" />
        </form:select>
        <form:input path="st" />
        <button type="submit" class="btn btn-small">검색</button>
        <c:if test="${ pagination.ss != 0 }">
            <a href="list.do?bd=${ pagination.bd }" class="btn btn-small">취소</a>
        </c:if>
    </div>
    </c:if>
    
    
    <c:if test="${ pagination.bd == 3 }">
    
    <table class="table table-bordered">
    <thead>
            <tr>
                <th>사원번호</th>
                <th>이름</th>
                <th>이메일</th>
                <th>부서</th>
                <th>직급</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${ list }">
                <tr>
                    <td>${ user.u_id }</td>
                    <td>${ user.u_name }</td>
                    <td>${ user.u_email }</td>
                    <td>${ user.d_name }</td>
                    <td>${ user.t_name }</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <div class="pagination pagination-small pagination-centered">
        <ul>
            <c:forEach var="page" items="${ pagination.pageList }">
                <li class='${ page.cssClass }'><a data-page="${ page.number }">${ page.label }</a></li>
            </c:forEach>
        </ul>
    </div>
    
    <div class="form-inline">
        <form:select path="ss">
            <form:option value="0" label="검색조건" />
            <form:option value="1" label="사원번호" />
            <form:option value="2" label="이름" />
            <form:option value="3" label="부서" />
            <form:option value="4" label="직급" />
        </form:select>
        <form:input path="st" />
        <button type="submit" class="btn btn-small">검색</button>
         <c:if test="${ pagination.ss != 0 }">
            <a href="list.do?bd=3" class="btn btn-small">취소</a>
        </c:if>
    </div>
    </c:if>
  
</form:form>

</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
