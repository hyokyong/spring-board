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

    <h1>사용자 정보 수정</h1>
    <hr />

    <form:form method="post" modelAttribute="user">

        <label>로그인ID</label>
        <form:input path="loginId" />

        <label>이름</label>
        <form:input path="name" />

        <label>이메일</label>
        <form:input path="email" />

        <label>사용자 유형</label>
        <form:select path="userType">
            <form:option value="관리자" />
            <form:option value="교수" />
            <form:option value="학생" />
        </form:select>

        <label>학과</label>
        <form:select path="departmentId">
            <form:options itemValue="id" itemLabel="departmentName" items="${ departments }" />
        </form:select>
        <hr />

        <div>
            <input type="submit" class="btn btn-primary" value="저장" />
            <a href="list.do" class="btn">목록으로 나가기</a>
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
