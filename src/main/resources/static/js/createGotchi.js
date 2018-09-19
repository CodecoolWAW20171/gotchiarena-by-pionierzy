var speed = document.getElementById("speed");
var attack = document.getElementById("attack");
var defence = document.getElementById("defence");

speed.addEventListener("input", updatePointsLeft);
attack.addEventListener("input", updatePointsLeft);
defence.addEventListener("input", updatePointsLeft);


function updatePointsLeft() {
    let speedValue = Number(speed.value);
    let attackValue = Number(attack.value);
    let defenceValue = Number(defence.value);

    let pointsLeft = 200 - speedValue - attackValue - defenceValue;
    speed.setAttribute("max", pointsLeft + speedValue);
    attack.setAttribute("max", pointsLeft + attackValue);
    defence.setAttribute("max", pointsLeft + defenceValue);
    document.getElementById("pointsLeft").innerText = pointsLeft > 0 ? pointsLeft : "no";
}

document.getElementById("type").addEventListener("change", setSecondarySelect);

function setSecondarySelect(event) {
    var coolTypes = Array.from(document.getElementsByClassName("cool"));
    var lameTypes = Array.from(document.getElementsByClassName("lame"));


    if (["WATER","FIRE","ICE"].includes(event.target.value)) {
        coolTypes.forEach(e => {
            e.disabled = true;
        });
        lameTypes.forEach(e => {
            e.disabled = false;
        });

    } else {
        coolTypes.forEach(e => {
            e.disabled = false;
        });
        lameTypes.forEach(e => {
            e.disabled = true;
        });
    }
}

function lockTypeSelect() {
    document.getElementById("type").disabled = true;
}

document.getElementById("secondary").addEventListener("change", lockTypeSelect);
