<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>It adminLisst</title>
</head>
<body>
<br>
<h1> admin list </h1>

<form id="data" action="watchServlet">
    <select name="act", form="data">
        <option value="create">ADD Student</option>
        <option value="delete">Delete Student</option>
        <option value="change">Change Student</option>
        <option value="watch">Watch Student</option>
    </select>
    <br>
    <br>
    <input  name="user", value="student">
    Enter student ID: <input name ="id", type="text">
     <input type="submit" form="data"/>
</form>
</body>
</html>