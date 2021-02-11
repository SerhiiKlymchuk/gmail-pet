<div class="message_header">
    <#assign curPage = inboxMessages!outboxMessages>
    <#assign searchQuery = searchQuery ! "">

    <#assign sendUrl =
        (searchQuery!="") ? then("search-", "") +
        (inboxMessages??) ? then("inbox", "outbox")>

    <div class="message_header__checkbox">
        <span class="box">&#9744;</span>
        <span class="refresh">&#8635;</span>
    </div>

    <div class="message_header__nav">
        <p class="message_header__nav__stats">
            ${curPage.number*10} -
            ${curPage.number*10 + curPage.numberOfElements} of
            ${curPage.totalElements}
        </p>

        <form action="/messages/${sendUrl}">
            <input type="submit"
                   id="${curPage.hasPrevious() ? then("", "disabled")}"
                   value="&#8249;">

            <input type="hidden" name="searchQuery" value="${searchQuery}">
            <input type="hidden" name="page" value="${curPage.number-1}">
        </form>

        <form action="/messages/${sendUrl}">
            <input type="submit"
                   id="${curPage.hasNext() ? then("", "disabled")}"
                   value="&#8250;">

            <input type="hidden" name="searchQuery" value="${searchQuery}">
            <input type="hidden" name="page" value="${curPage.number+1}">
        </form>
    </div>

</div>