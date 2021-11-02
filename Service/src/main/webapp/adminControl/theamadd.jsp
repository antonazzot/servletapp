<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>Add page</title>
</head>
<body>

<h1> ADD NEW THEAM </h1>

<form id="data" method = "post" action="theamAdd" >

    <br>
    Enter THEAM NAME: <input  name="theam", type="text"> <br>
      <br>
    <br>
    <input type="submit" form="data" />
<br>
  <form  action="hello">
      <input type="submit" value="Go to Main Admin!">
  </form>
    <br>
<form  action="logout">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>