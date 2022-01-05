<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.util.*" %>

<head>
    <meta charset="UTF-8">
    <title>Add page</title>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<section class="container">
<div class="login">
<h1> ADD NEW THEAM </h1>

<form id="data" method = "post" action="/web/mvc/views/savetheam" >
    <br>
    Enter THEAM NAME: <input  name="theam", type="text"> <br>
      <br>
    <br>
    <input type="submit" form="data" />
<br>

        <jsp:include page="mainpage.jsp" />
       </div>
      </section>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>