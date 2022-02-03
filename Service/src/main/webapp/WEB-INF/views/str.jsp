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
          <input type="submit" value="EnterLikeAdmin">
      </form>

      </br>
       <form  action="/web/mvc/trainer/traineract">
                <input type="submit" value="EnterLikeTrainer">
            </form>
            </br>
             <form  action="/web/mvc/checkUser">
                      <input type="submit" value="EnterLikeStudent">
                  </form>
    </div>

    <div class="login-help">
      <p>Forgot your password? <a href="index.html">Click here to reset it</a>.</p>
    </div>
  </section>

</body>
</html>
