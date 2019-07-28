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
</style>
</head>

<body>
<h2>${ pagination.boardId == 1 ? "공지사항" : "자유게시판" }</h2>
<hr />

<form:form method="post" modelAttribute="article">

        <label>제목</label>
        <form:input path="a_title"/>
    	<form:textarea path="a_content"/>
    
    <div>
        <button type="submit" class="btn btn-primary">
            <i class="icon-ok icon-white"></i> 저장
        </button>
        <a href="list.do?${ pagination.queryString }" class="btn">
            <i class="icon-ban-circle"></i> 취소
        </a>
    </div>
</form:form>
</body>