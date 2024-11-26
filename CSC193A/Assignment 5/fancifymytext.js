function makeTextBigger() {
    document.getElementById("Text").style.fontSize = "24pt";
}

function makeFancy() {
    document.getElementById("Text").style.fontWeight = "bold";
    document.getElementById("Text").style.color = "blue";
    document.getElementById("Text").style.textDecoration = "underline";
}

function makeBoring() {
    document.getElementById("Text").style.fontWeight = "normal";
}

function addMoo() {
    var str = document.getElementById("Text").value;
    str = str.toUpperCase();
    var parts = str.split(".");
    str = parts.join("-Moo.");
    document.getElementById("Text").value = str;
}
