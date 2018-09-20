let speed = document.getElementById("speed");
let attack = document.getElementById("attack");
let defence = document.getElementById("defence");

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
    let wetFireTypes = Array.from(document.getElementsByClassName("cool"));
    let bushyLightningTypes = Array.from(document.getElementsByClassName("lame"));
    document.getElementById("secondary").hidden = false;


    if (["WATER","FIRE","ICE"].includes(event.target.value)) {
        wetFireTypes.forEach(e => {
            e.disabled = true;
        });
        bushyLightningTypes.forEach(e => {
            e.disabled = false;
        });

    } else {
        wetFireTypes.forEach(e => {
            e.disabled = false;
        });
        bushyLightningTypes.forEach(e => {
            e.disabled = true;
        });
    }
}




function lockTypeSelect() {
    document.getElementById("type").disabled = true;
}

document.getElementById("secondary").addEventListener("change", lockTypeSelect);
