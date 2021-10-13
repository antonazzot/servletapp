<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>It adminList</title>
</head>
<body>
<br>
<h1> admin list </h1>

<form id="data" action="watchServlet">
    <select name="user", form="data">
        <option value="student">Student</option>
        <option value="trainer">Trainer</option>
        <option value="administrator">Administrator</option>
        <option value="group">Group</option>
    </select>
    <br>
       <select name="act", form="data">
        <option value="create">ADD </option>
        <option value="delete">Delete </option>
        <option value="change">Change </option>
        <option value="watch">Watch </option>
    </select>
    <br>
    <br>

    <br>
    <br>
    Enter User ID for delete: <input name ="id", type="text"> <br>
    <input type="submit" form="data" value="Do act">
</form>

<br>
<form action="addSalary">
<button type="submit"   > ADD SALARY </button>
</form>

<form action="avarageSalary">
<button type="submit"   > AVARAGE SALARY </button>
</form>
<br>
<form  action="logout">
    <input type="submit" value="LogOut!">
</form>
</body>
</html>