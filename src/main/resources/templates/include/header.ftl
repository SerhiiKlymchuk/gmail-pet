<header>
    <#assign sendUrl = (inboxMessages??) ? then("inbox", "outbox")>

    <div class="header-logo">
        <p>&#9776;</p>
        <img src="https://ssl.gstatic.com/ui/v1/icons/mail/rfr/logo_gmail_lockup_dark_1x_r2.png" alt="Logo">
    </div>

    <form id="search-box" action="/messages/search-${sendUrl}">
        <input type="text" name="searchQuery" id="search_query" autofocus placeholder="Search mail!">
        <input type="submit" value="&#9906;" id="search_btn">
    </form>

    <div class="header-options">
        <p class="settings">&#9881;</p>

        <img class="account__img"
             src="https://ih1.redbubble.net/image.1262560022.7589/st,small,507x507-pad,600x600,f8f8f8.jpg"
             alt="Acc Logo">

        <form action="/logout" method="POST">
            <input class="logout_btn" type="submit" value="&#9992;">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/><br>
        </form>
    </div>

</header>