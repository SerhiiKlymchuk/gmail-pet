<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/auth.css">
    <title>Register</title>
</head>
<body>

    <form name="f" action="/user/register" class="form register" method="POST">
        <img id="form_google_logo" src="/images/google_logo.png" alt="404 IMAGE NOT FOUND!">
        <h3>Create Account</h3>
        <input type="text" name="firstName" id="first_name" placeholder="first name">
        <input type="text" name="lastName" id="last_name" placeholder="last name">

        <div id="username-box">
            <input type="text"  id="username" name="username" placeholder="user.name">
            <p id="domain-placeholder">@gmail.com</p>
        </div>

        <input type="password" id="password" name="password" placeholder="password">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>

        <div id="buttons_box">
            <a href="/login" id="login_btn">Log in</a>
            <input type="submit" value="Create account" id="register_btn">
        </div>

    </form>

    <p>
        <#if errors??>
            <#list errors as e>
               <p>${e.codes[1]}</p>
            <p>${e.defaultMessage}</p>
            </#list>
        </#if>
    </p>

</body>
</html>