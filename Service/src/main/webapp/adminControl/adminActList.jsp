<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin main page</title>
</head>
<body>
<br>
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
    Enter User ID for delete: <input name ="id", type="text"> <br>
    <br>

    <br>
    <br>

    <input type="submit" form="data" value="  Do act   ">
</form>

<br>
<form action="addSalary">
<button type="submit"   > ADD SALARY </button>
</form>

<form action="avarageSalary">
<button type="submit"   > AVARAGE SALARY </button>
</form>
<br>
<form action="fromdb">
<button type="submit"   > ALLUSSER </button>
</form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut!">
</form>
</body>
</html>