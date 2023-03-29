console.log("edit.js");

const radioButtons = document.querySelectorAll('input[type="radio"]');
const searchField = document.getElementById('search-field');
const kingField = document.getElementById('king-field');
const sourceField = document.getElementById('source-field');
const createSourceForm = document.getElementById('create-source-form');

radioButtons.forEach(radioButton => {
    //if radio button is checked
    if (radioButton.checked) {
        if (radioButton.value === 'word') {
            searchField.style.display = 'block';
        } else if (radioButton.value === 'king') {
            kingField.style.display = 'block';
        } else if (radioButton.value === 'source') {
            sourceField.style.display = 'block';
        }
    }

    radioButton.addEventListener('change', () => {

        if (radioButton.value === 'word') {
            searchField.style.display = 'block';
            kingField.style.display = 'none';
            sourceField.style.display = 'none';
            createSourceForm.style.display = 'none';

        } else if (radioButton.value === 'king') {
            kingField.style.display = 'block';
            searchField.style.display = 'none';
            sourceField.style.display = 'none';
            createSourceForm.style.display = 'none';

        } else if (radioButton.value === 'source') {
            sourceField.style.display = 'block';
            kingField.style.display = 'none';
            searchField.style.display = 'none';

        } else {
            kingField.style.display = 'none';
            searchField.style.display = 'none';
            sourceField.style.display = 'none';
            createSourceForm.style.display = 'none';
        }
    });
});

document.getElementById('source-new-button').onclick = function() {
    createSourceForm.style.display = 'block';
}

