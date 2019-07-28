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

<body>

<div class="container">

    <h1>회원가입</h1>
    <hr />

    <form:form method="post" modelAttribute="user">

        <label>사원번호</label>
        <form:input path="u_id" />

        <label>비밀번호</label>
        <form:input path="u_passwd" type="password"/>
        
        <label>이름</label>
        <form:input path="u_name" />

        <label>이메일</label>
        <form:input path="u_email" />

        <label>부서</label>
        <form:select path="d_id">
            <form:options itemValue="d_id" itemLabel="d_name" items="${ departments }" />
        </form:select>

        <label>직급</label>
        <form:select path="t_id">
            <form:options itemValue="t_id" itemLabel="t_name" items="${ types }" />
        </form:select>
        <hr />

        <div>
            <input type="submit" class="btn btn-primary" value="저장" />
        </div>
    </form:form>

    <c:if test="${ not empty error }">
        <div class="alert alert-error">${ error }</div>
    </c:if>
    <c:if test="${ not empty success }">
        <div class="alert alert-success">${ success }</div>
    </c:if>

</div>
</body>
</html>
