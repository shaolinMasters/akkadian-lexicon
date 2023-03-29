const radioButtons = document.querySelectorAll('input[type="radio"]');
const searchField = document.getElementById('search-field');
const kingField = document.getElementById('king-field');
const resultSegment = document.getElementById('results');
const errorSegment = document.getElementById('errorMessage');
const sourceField = document.getElementById('source-field');

console.log("search.js");
radioButtons.forEach(radioButton => {
    //if radio button is checked
    if(radioButton.checked){
        if(radioButton.value === 'word'){
            searchField.style.display = 'block';
        }
        else if (radioButton.value === 'king'){
            kingField.style.display = 'block';
        }
        else if (radioButton.value === 'source'){
            sourceField.style.display = 'block';
        }
    }



    radioButton.addEventListener('change', () => {
        resultSegment.style.display = 'none';
        errorSegment.style.display = 'none';

        if (radioButton.value === 'word') {
            searchField.style.display = 'block';
            kingField.style.display = 'none';
            sourceField.style.display = 'none';

        } else if (radioButton.value === 'king') {
            kingField.style.display = 'block';
            searchField.style.display = 'none';
            sourceField.style.display = 'none';

        } else if (radioButton.value === 'source') {
            sourceField.style.display = 'block';
            kingField.style.display = 'none';
            searchField.style.display = 'none';

        } else {
            kingField.style.display = 'none';
            searchField.style.display = 'none';
            sourceField.style.display = 'none';
            resultSegment.style.display = 'none';
        }
    });
});
