<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
</head>
<body>
<br> <br/>
<form id="data" action="checkUser">
    <br>
    <br>
    <br>
    Enter id or login: <input name="id", type="text"> <br/>
    <br>
    Enter password:      <input name="password", type="text">
    <input type="submit" form="data"/>
</form>

</body>
</html>