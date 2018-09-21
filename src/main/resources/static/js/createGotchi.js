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

///////////////////////// selects ////////////////////////////////

document.getElementById("type").children[0].selected = true;
document.getElementById("secondary").children[0].selected = true;

document.getElementById("type").addEventListener("change", limitOtherSelect);

function limitOtherSelect(event) {
    let wetFireSecondary = document.querySelectorAll("#secondary .wetFire");
    let bushyLightningSecondary = document.querySelectorAll("#secondary .bushyLightning");

    document.getElementById("secondary").hidden = false;
    console.log(event.target.children[event.target.selectedIndex]);
    console.log(event.target.selectedIndex);
    if (event.target.children[event.target.selectedIndex].className === "wetFire") {
        console.log("wetfire");
        Array.from(event.target.children).forEach(e => {
            if (e.className === "bushyLightning") e.disabled = true;
        });
        wetFireSecondary.forEach(e => {
            e.disabled = true;
        });
    } else {
        console.log("bushyLifghtning");
        Array.from(event.target.children).forEach(e => {
            if (e.className === "wetFire") e.disabled = true;
        });
        bushyLightningSecondary.forEach(e => {
            e.disabled = true;
        });
    }
}
