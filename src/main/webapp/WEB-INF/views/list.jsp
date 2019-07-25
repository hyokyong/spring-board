<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

<div class="container">

<h1>게시판 목록</h1>
<hr />

<form method="get">
    <input type="hidden" name="pg" value="1" />

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
                <tr data-url="reading.do?id=${ article.a_id }&${ pagination.queryString }">
                    <td>${ article.a_id }</td>
                    <td>${ article.a_title }</td>
                    <td>${ article.u_name }</td>
                    <td>${ article.timestamp }</td>
                    <td>${ article.a_click }</td>
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
</form>

</div>
</body>
</html>
