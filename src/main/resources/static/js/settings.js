document.getElementById('admin-new-button').onclick = function () {
    window.location = "settings/user?option=admin&action=create";
}


document.getElementById('admin-delete-button').onclick = function () {
    window.location = "settings/user?option=admin&action=delete";
}
