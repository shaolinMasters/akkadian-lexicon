console.log("edit.js");

const radioButtons = document.querySelectorAll('input[type="radio"]');
const searchField = document.getElementById('search-field');
const kingField = document.getElementById('king-field');
const sourceField = document.getElementById('source-field');
const createSourceForm = document.getElementById('create-source-form');
const createKingForm = document.getElementById('create-king-form');
const createVerbForm = document.getElementById('create-verb-form');
const createNotVerbForm = document.getElementById('create-not-verb-form');

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
            window.location = "edit?option=word";
        } else if (radioButton.value === 'king') {
            window.location = "edit?option=king";
        } else if (radioButton.value === 'source') {
            window.location = "edit?option=source";
        } else {
            kingField.style.display = 'none';
            searchField.style.display = 'none';
            sourceField.style.display = 'none';
            createSourceForm.style.display = 'none';

        }
        /*if (radioButton.value === 'word') {
            searchField.style.display = 'block';
            kingField.style.display = 'none';
            sourceField.style.display = 'none';
            createSourceForm.style.display = 'none';
            createKingForm.style.display = 'none';
            wordClassField.style.display = 'none';




        } else if (radioButton.value === 'king') {
            kingField.style.display = 'block';
            searchField.style.display = 'none';
            sourceField.style.display = 'none';
            createSourceForm.style.display = 'none';
            wordClassField.style.display = 'none';



        } else if (radioButton.value === 'source') {
            sourceField.style.display = 'block';
            kingField.style.display = 'none';
            searchField.style.display = 'none';
            createKingForm.style.display ='none';
            wordClassField.style.display = 'none';
*/


    });

    /*radioButton.addEventListener('change', () => {
        if (radioButton.value === 'verb') {
            createVerbForm.style.display = 'block';
            createNotVerbForm.style.display = 'none';

        } else if (radioButton.value === 'notVerb') {
            createNotVerbForm.style.display = 'block';
            createVerbForm.style.display = 'none';

        } else {
            createVerbForm.style.display = 'none';
            createNotVerbForm.style.display = 'none';
        }
    });*/
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
        //wordClassField.style.display = 'block';
        window.location = "edit?option=word&action=create";
    }

    const removableSourceInput = document.getElementById('removable-source-input');
    const removableKingInput = document.getElementById('removable-king-input');


    const sourceDeleteButtons = document.querySelectorAll(".source-delete-button");
    const kingDeleteButtons = document.querySelectorAll(".king-delete-button");

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


/*const wordClassList = document.getElementById('dropdown-wordClasses');

    wordClassList.addEventListener('onchange', function showVerbForm(event) {
        if(wordClassList.value === 'Verb') {
            createVerbForm.style.display = 'block'
        }
    })*/





