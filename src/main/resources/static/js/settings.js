document.getElementById('admin-new-button').onclick = function () {
    window.location.replace(location.origin + "/settings/user?option=admin&action=create");

}


document.getElementById('admin-delete-button').onclick = function () {
    window.location.replace(location.origin + "/settings/user?option=admin&action=delete");
}

const removableAdminInput = document.getElementById('removable-admin-input');

const adminDeleteButtons = document.querySelectorAll(".admin-delete-button");

adminDeleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        removableAdminInput.value = button.value;
    });
});


const textInputs = document.querySelectorAll('input[type="text"], input[type="email"]');
const alerts = document.querySelectorAll('.alert');
textInputs.forEach(textInput => {
    textInput.addEventListener('focus', () => {
        alerts.forEach(alert => {
            alert.style.display='none';
        });

    });
});

function hideAlerts(){
    alerts.forEach(alert => {
        alert.style.display='none';
    });
}
