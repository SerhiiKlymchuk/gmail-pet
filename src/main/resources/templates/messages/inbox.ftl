<!doctype html>
<html lang="en">
<head>
    <#include "../include/meta.ftl">
    <link rel="stylesheet" href="/css/messages.css">
    <title>Inbox</title>
</head>
<body id="inbox">
    <#include "../include/header.ftl">
    <#include "../include/sidebar.ftl">

    <div class="messages_box">
        <ul>
            <#list inboxMessages as msg>
                <li class="message_item">
                    <div class="message_item__marks">
                        <span class="box">&#9744;</span>
                        <span class="star">&#9734;</span>
                        <p class="sender">${msg.senderUsername}</p>
                    </div>

                    <p class="subject">${msg.subject}</p>

                    <p class="content">${(msg.content?length > 50) ? then(msg.content?substring(0, 50), msg.content)}...
                    </p>
                    <p class="date">${msg.date.hour}:${msg.date.minute}</p>
                </li>
            </#list>
        </ul>
    </div>
</body>
</html>