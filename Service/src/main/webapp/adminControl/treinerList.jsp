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
        <option value="create">ADD Trainer</option>
        <option value="delete">Delete Trainer</option>
        <option value="change">Change Trainer</option>
        <option value="watch">Watch Trainer</option>
    </select>
    <br>
    <br>
    <input  name="user", value="student"> <br>
    Enter student ID: <input name ="id", type="text">
     <input type="submit" form="data"/>
</form>
<br>
<form  action="/web/hello">
    <input type="submit" value="Go to LogIN page!">
</form>
</body>
</html>