<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
        <link href="/resources/css/style.css" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
  <section class="container">
    <div class="login">
      <h1>Login to Web App</h1>
      <form  action="/web/mvc/views/mainadmin">
          <input type="submit" value="Enter Like Admin">
      </form>

      </br>
       <form  action="/web/mvc/trainer/traineract">
                <input type="submit" value="Enter Like Trainer">
            </form>
            </br>
             <form  action="/web/mvc/student">
                      <input type="submit" value="Enter Like Student">
              </form>
              </br>
              </br>
              </hr>
             <form  action="/web/mvc/registrate">
                      <input type="submit" value="Registrate like new student">
              </form>
    </div>

    <div class="login-help">
      <p>Forgot your password? <a href="/web/mvc/resetpassword">Click here to reset it</a>.</p>
    </div>
  </section>

</body>
</html>
