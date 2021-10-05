<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<h1> ADD NEW STUDENT </h1>

<form id="data" action="UserActionServlet" >
    <input name="act" value="add"> <input name="user" value="student"> <br>
    Enter Student NAME: <input  name="name", type="text"> <br>
    Enter Student Login: <input  name="login", type="text"> <br>
    Enter Student Password: <input  name="password", type="text"> <br>
    Enter Student age: <input  name="age", type="text"> <br>

    <br>
    <br>
   <input type="submit" form="data" />
</body>
</html>