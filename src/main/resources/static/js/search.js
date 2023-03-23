const radioButtons = document.querySelectorAll('input[type="radio"]');
const searchField = document.getElementById('search-field');
const kingField = document.getElementById('king-field');
const resultSegment = document.getElementById('resultSegment');
const errorSegment = document.getElementById('errorMessage');

console.log("search.js");
radioButtons.forEach(radioButton => {
    radioButton.addEventListener('change', () => {
        resultSegment.style.display = 'none';
        errorSegment.style.display = 'none';

        if (radioButton.value === 'word') {
            searchField.style.display = 'block';
            kingField.style.display = 'none';

        } else if (radioButton.value === 'king') {
            kingField.style.display = 'block';
            searchField.style.display = 'none';

        } else {
            kingField.style.display = 'none';
            searchField.style.display = 'none';

        }
    });
});
