const textInputs = document.querySelectorAll('input[type="text"]');
const alerts = document.querySelectorAll('.alert');
textInputs.forEach(textInput => {
    textInput.addEventListener('focus', () => {
        alerts.forEach(alert => {
            alert.style.display='none';
        });

    });
});
