<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="http://netdna.bootstrapcdn.com/bootstrap/2.3.2/css/bootstrap.min.css" rel="stylesheet">
<title>pj1</title>
<style>
    label { margin-top: 20px; }
</style>
</head>

<h1>PJ1 인트라망 로그인</h1>
<hr />

<form method="POST" action="login_processing.do">
    <label>사원번호</label>
    <input type="text" name="u_id" />

    <label>비밀번호</label>
    <input type="password" name="u_passwd" />

    <hr />
    <!-- <button type="submit" class="btn btn-primary">
        <i class="icon-check icon-white"></i> 로그인
   </button>-->
   
    <div>
    	<input type="submit" class="btn btn-primary" value="로그인" />
        <a href="join.do" class="btn">회원가입</a>
    </div>
</form>

<c:if test="${ param.error != null }">
    <div class="alert alert-error">로그인 실패</div>
</c:if>

