<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
<link href="static/css/style.css" rel="stylesheet">
</head>

<body>
<br>
<section class="admincontainer">
<div class="ser"
<h1> Admin page </h1>
<form id="data" action="watchServlet">
    <select name="user", form="data">
        <option value="student">Student</option>
        <option value="trainer">Trainer</option>
        <option value="administrator">Administrator</option>
        <option value="group">Group</option>
        <option value="theam">Theam</option>
    </select>
    <br>

       <select name="act", form="data">
        <option value="create">ADD </option>
        <option value="delete">Delete </option>
        <option value="change">Change </option>
        <option value="watch">Watch </option>
    </select>

    <br>
    Enter User ID for delete: <input name ="id", type="number"> <br>
    <br>


    <br>
    <br>

    <input type="submit" form="data" value="  Do act   ">
  </div>
</form>
</section>
<br>
<section class="salaryaddontainer">
<div class="salary"
<form action="addSalary">
<button type="submit"   > ADD SALARY </button>
</form>

<form action="avarageSalary">
<button type="submit"   > AVARAGE SALARY </button>
</form>
</div>
<br>
</section>
<br>
 <jsp:include page="logout.jsp" />
</body>
</html>