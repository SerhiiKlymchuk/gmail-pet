<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Login</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<body>

    <form name="f" action="login" class="form login" method="POST">
        <img id="form_google_logo" src="/images/google_logo.png" alt="404 IMAGE NOT FOUND!">
        <h3>Login</h3>
        <p>Go to Gmail!</p>

        <div id="username-box">
            <input type="text"  id="username" name="username" placeholder="user.name">
            <p id="domain-placeholder">@gmail.com</p>
        </div>

        <input type="password" id="password" name="password" placeholder="password">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>

        <div id="buttons_box">
            <a href="/user/register" id="register_btn">Create account</a>
            <input type="submit" value="Log in!" id="login_btn">
        </div>

    </form>

</body>
</html>