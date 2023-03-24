const Keyboard = window.SimpleKeyboard.default;
const defaultTheme = "hg-theme-default";

const keyboard = new Keyboard({
    theme: defaultTheme,
    onChange: input => onChange(input),
    onKeyPress: button => onKeyPress(button),
    layout: {
        default: [
            "` ā â ē ê ī î ū û 9 0 - = {bksp}",
            "{tab} q w e r t y u i o p [ ] \\",
            "{lock} a s d ṭ g ḫ j k l ’ ’ {enter}",
            "{shift} z š ṣ v b n m , . / {shift}",
            ".com @ {space}"
        ],
        shift: [
            "~ Ā Â Ē Ê Ī Î Ū Û ( ) _ + {bksp}",
            "{tab} Q W E R T Y U I O P { } |",
            '{lock} A S D Ṭ G Ḫ J K L : " {enter}',
            "{shift} Z Š Ṣ V B N M < > ? {shift}",
            ".com @ {space}"
        ]
    },
    newLineOnEnter: false,
});

const inputDOM = document.querySelector(".input");
const keyboardDiv = document.querySelector(".simple-keyboard");

/**
 * Update simple-keyboard when input is changed directly
 */
inputDOM.addEventListener("input", event => {
    keyboard.setInput(event.target.value);
});





inputDOM.addEventListener("focus", (event) => {
    showKeyboard();
});

document.addEventListener("click", (event) => {
    console.log("click");
    if (
        /**
         * Hide the keyboard when you're not clicking it or when clicking an input
         * If you have installed a "click outside" library, please use that instead.
         */
        keyboard.options.theme.includes("show-keyboard") &&
        !event.target.className.includes("input") &&
        !event.target.className.includes("hg-button") &&
        !event.target.className.includes("hg-row") &&
        !event.target.className.includes("simple-keyboard")
    ) {
        console.log("hideKeyboard()");
        hideKeyboard();
    }
});

function showKeyboard() {
    console.log("show");
    keyboard.setOptions({
        theme: `${defaultTheme} show-keyboard`
    });
    keyboardDiv.style.display = "block";
    keyboardDiv.classList.add("mt-3");

    // give it focus like style
    inputDOM.style.outlineColor = "rgb(0, 95, 204)";
    inputDOM.style.outline = "-webkit-focus-ring-color auto 1px";
}

function hideKeyboard() {
    console.log("in hide");
    keyboard.setOptions({
        theme: defaultTheme
    });
    keyboardDiv.style.display = "none";

    // delete focus like style
    inputDOM.style.outlineColor = "";
    inputDOM.style.outline = "";
}

function onChange(input) {
    inputDOM.value = input;
    console.log("Input changed", input);
}

function onKeyPress(button) {
    console.log("Button pressed", button);
    if (button === "{shift}" || button === "{lock}") handleShift();
    if(button === "{enter}") document.forms["searchForm"].submit()
}


function handleShift() {
    let currentLayout = keyboard.options.layoutName;
    let shiftToggle = currentLayout === "default" ? "shift" : "default";

    keyboard.setOptions({
        layoutName: shiftToggle
    });
    keyboardDiv.classList.add("mt-3");
}
