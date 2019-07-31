<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!--<script src="/bbs1/res/se2/js/HuskyEZCreator.js" type="text/javascript"></script>
<script src="/bbs1/res/se2/init.js" type="text/javascript"></script> -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="http://netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<title>pj1</title>
<style>
  input[name=a_title] { width: 700px; border-style: groove; margin: 4px;}
  textarea { width: 80%; height: 200px; }
  input[type=file] { width: 600px; margin: 5px 0 5px 0; }
</style>
</head>

<body>
<h2>${ pagination.boardId == 1 ? "공지사항" : "자유게시판" }</h2>
<hr />

<form method="post" enctype="multipart/form-data">
    <div>
        <span>제목</span>
        <input type="text" name="a_title" value="${ article.a_title }"/>
    </div>
    <textarea id="a_content" name="a_content">${ article.a_content }</textarea>
    <div>
        <span>파일</span>
        <input type="file" name="file" />${ file.f_name }&nbsp;
        	<c:if test = "${ file.f_id != 0 && article.a_id != 0}">
        		<a href="edit.do?aid=${ article.a_id }&fid=${ file.f_id }&${ pagination.queryString }">x</a>
        	</c:if>
    </div>
    <div>
        <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i> 저장
        </button>
        <a href="list.do?${ pagination.queryString }" class="btn">
            <i class="icon-ban-circle"></i> 취소
        </a>
    </div>
</form>


</body>