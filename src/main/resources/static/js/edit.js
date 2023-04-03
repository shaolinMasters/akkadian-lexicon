console.log("edit.js");

const radioButtons = document.querySelectorAll('input[type="radio"]');
const searchEditField = document.getElementById('search-field');
const kingField = document.getElementById('king-field');
const sourceField = document.getElementById('source-field');
/*const createSourceForm = document.getElementById('create-source-form');
const createKingForm = document.getElementById('create-king-form');*/
const createVerbForm = document.getElementById('create-verb-form');
const createNotVerbForm = document.getElementById('create-not-verb-form');
const wordField = document.getElementById('word-field');

radioButtons.forEach(radioButton => {
    //if radio button is checked
    if (radioButton.checked) {
        if (radioButton.value === 'word') {
            searchEditField.style.display = 'block';
        } else if (radioButton.value === 'king') {
            kingField.style.display = 'block';

        } else if (radioButton.value === 'source') {
            sourceField.style.display = 'block';
        }
    }

    radioButton.addEventListener('change', () => {

        if (radioButton.value === 'word') {
            window.location = "edit?option=word";
        } else if (radioButton.value === 'king') {
            window.location = "edit?option=king";
        } else if (radioButton.value === 'source') {
            window.location = "edit?option=source";
        } else {
            kingField.style.display = 'none';
            searchEditField.style.display = 'none';
            sourceField.style.display = 'none';

        }
    });
});

document.getElementById('sources-new-button').onclick = function () {
    window.location = "edit?option=source&action=create";
}

document.getElementById('kings-new-button').onclick = function () {
    window.location = "edit?option=king&action=create";
}


document.getElementById('sources-delete-button').onclick = function () {
    window.location = "edit?option=source&action=delete";
}

document.getElementById('kings-delete-button').onclick = function () {
    window.location = "edit?option=king&action=delete";
}

document.getElementById('word-new-button').onclick = function () {

    window.location = "edit?option=word&action=create";
}

document.getElementById('words-delete-button').onclick = function () {
    window.location = "edit?option=word&action=delete";
}



const removableSourceInput = document.getElementById('removable-source-input');
const removableKingInput = document.getElementById('removable-king-input');
const removableWordInput = document.getElementById('removable-word-input');

const sourceDeleteButtons = document.querySelectorAll(".source-delete-button");
const kingDeleteButtons = document.querySelectorAll(".king-delete-button");
const wordDeleteButtons = document.querySelectorAll(".word-delete-button");



sourceDeleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        removableSourceInput.value = button.value;
    });
});

kingDeleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        removableKingInput.value = button.value;
    });
});

wordDeleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        removableWordInput.value = button.value;
    });
});


const wordClassList = document.querySelector("#dropdown-wordClasses");

const notVerbWordclassInput = document.querySelector("#not-verb-wordclass-input");


function getWordForm() {
    if (wordClassList.value === "Verb") {
        createVerbForm.style.display = 'block'
        createNotVerbForm.style.display = 'none'
    } else {
        createNotVerbForm.style.display = 'block'
        createVerbForm.style.display = 'none'
        notVerbWordclassInput.value = wordClassList.value;
    }
}





