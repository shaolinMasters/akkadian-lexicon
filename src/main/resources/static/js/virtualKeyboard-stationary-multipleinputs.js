console.log("multipleinputs");

let selectedInput;
document.querySelectorAll(".multipleinput").forEach(input => {
    input.addEventListener("focus", onInputFocus);
    // Optional: Use if you want to track input changes
    // made without simple-keyboard
    input.addEventListener("input", onInputChange);
});
const keyboardMultipleInputs = new Keyboard(".simple-keyboard-multiple-inputs",{
    theme: defaultTheme,
    onChange: input => onChangeMultiple(input),
    onKeyPress: button => onKeyPressMultiple(button),
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

const multiplekeyboardDiv = document.querySelector(".simple-keyboard-multiple-inputs");

document.addEventListener("click", (event) => {
    console.log("click");
    if (
        /**
         * Hide the keyboard when you're not clicking it or when clicking an input
         * If you have installed a "click outside" library, please use that instead.
         */
        keyboardMultipleInputs.options.theme.includes("show-keyboard") &&
        !event.target.className.includes("multipleinput") &&
        !event.target.className.includes("hg-button") &&
        !event.target.className.includes("hg-row") &&
        !event.target.className.includes("simple-keyboard")
    ) {
        console.log("hideMultipleKeyboard()");
        hideMultipleKeyboard();
    }
});

function onInputFocus(event) {
    showMultipleKeyboard();
    selectedInput = `#${event.target.id}`;

    keyboardMultipleInputs.setOptions({
        inputName: event.target.id
    });
}

function onInputChange(event) {
    keyboardMultipleInputs.setInput(event.target.value, event.target.id);
}

function onChangeMultiple(input) {
    console.log("Input changed", input);
    document.querySelector(selectedInput || ".multipleinput").value = input;
}

function onKeyPressMultiple(button) {
    console.log("Button pressed", button);
    if (button === "{shift}" || button === "{lock}") handleShift();
}


function handleShift() {
    let currentLayout = keyboardMultipleInputs.options.layoutName;
    let shiftToggle = currentLayout === "default" ? "shift" : "default";

    keyboardMultipleInputs.setOptions({
        layoutName: shiftToggle
    });
}


function showMultipleKeyboard() {
    console.log("show");
    keyboardMultipleInputs.setOptions({
        theme: `${defaultTheme} show-keyboard`
    });
    multiplekeyboardDiv.style.display = "block";
}

function hideMultipleKeyboard() {
    console.log("in hide");
    keyboardMultipleInputs.setOptions({
        theme: defaultTheme
    });
    multiplekeyboardDiv.style.display = "none";
}
