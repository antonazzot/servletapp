<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test Page</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<br>
<h1> Avarage salary list </h1>

<p>Name: ${trainer}  have got avarage salary ${avarage} </p>

<br>
<form  action="adminActList.jsp">
    <input type="submit" value="Go to Main Admin!">
</form>
<br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>