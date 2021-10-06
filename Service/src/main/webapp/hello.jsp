<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>

    <form  id="data"  action="checkUser" >
      <input name="id" type="text" placeholder="username or ID"/>
      <input name="password" type="text" placeholder="password"/>
     <input type="submit" form="data"/>
    </form>
</body>
</html>
