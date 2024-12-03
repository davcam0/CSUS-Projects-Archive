(function() {
  "use strict";

  window.addEventListener("load", init);
  
  /**
   * Initializes the application by setting up event listeners.
   * This function is called when the window's "load" event is triggered.
   * @function init
   */
  function init() {
    console.log("Window loaded!");

    const encryptButton = document.getElementById("encrypt-it");
    encryptButton.addEventListener("click", handleClick);

    const resetButton = document.getElementById("reset");
    resetButton.addEventListener("click", handleReset);
  }

  /**
   * Handles the "click" event for the "Encrypt It!" button.
   * Retrieves the input text, encrypts it using the `shiftCipher` function,
   * and displays the result in the output area.
   * @function handleClick
   */
  function handleClick() {
    const text = document.getElementById("input-text").value;
    const encryptedText = shiftCipher(text);
    document.getElementById("result").textContent = encryptedText;
  }

    /**
   * Handles the "click" event for the "Reset" button.
   * Clears the input text area and resets the output area to a placeholder message.
   * @function handleReset
   */
  function handleReset() {
    document.getElementById("input-text").value = "";
    document.getElementById("result").textContent = "";
  }

/**
 * Returns an encrypted version of the given text, where
 * each letter is shifted alphabetically ahead by 1 letter,
 * and 'z' is shifted to 'a' (creating an alphabetical cycle).
 * @function shiftCipher
 * @param {string} text - The text to encrypt.
 * @returns {string} The encrypted text
 */
function shiftCipher(text) {
  text = text.toLowerCase();
  let result = "";
  for (let i = 0; i < text.length; i++) {
    if (text[i] < 'a' || text[i] > 'z') {
      result += text[i];
    } else if (text[i] == 'z') {
      result += 'a';
    } else { // letter is between 'a' and 'y'
      let letter = text.charCodeAt(i);
      let resultLetter = String.fromCharCode(letter + 1);
      result += resultLetter;
    }
  }
  return result;
}
})();
