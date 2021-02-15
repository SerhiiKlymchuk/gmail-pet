const trash_icons = document.querySelectorAll("#form_delete > .fa-trash");

trash_icons.forEach(el => el.onclick = () => {
    el.parentElement.submit();
})

