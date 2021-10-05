<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add page</title>
</head>
<body>

<h1> ADD NEW USER </h1>

<form id="data" action="UserActionServlet" >
    <input name="act" value="add">
    Enter User Role:    <select name="role", form="data">
                        <option value="Student">Student</option>
                        <option value="Trainer">Trainer</option>
                        <option value="Administrator">Administrator</option>
</select>
    <br>
    Enter User NAME: <input  name="name", type="text"> <br>
    Enter User Login: <input  name="login", type="text"> <br>
    Enter User Password: <input  name="password", type="text"> <br>
    Enter User age: <input  name="age", type="text"> <br>

    <br>
    <br>
    <input type="submit" form="data" />
</body>
</html>