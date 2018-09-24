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
$("#reset").click( function() {reset()} );


function reset() {
    console.log("start reset");
    document.getElementById("secondary").hidden = true;
    document.getElementById("option2").hidden = true;
    document.getElementById("option2").selected = true;
    document.getElementById("option2").disabled = true;
    $("#type > option").each(function() {
        this.disabled = false;
    });
    $("#secondary > option").each(function() {
        this.disabled = false;
    });

    console.log("done");

}

function limitOtherSelect(event) {
    let fire = document.querySelectorAll("#secondary .fire");
    let water = document.querySelectorAll("#secondary .water");
    let plant = document.querySelectorAll("#secondary .plant");
    let electric = document.querySelectorAll("#secondary .electric");
    let ice = document.querySelectorAll("#secondary .ice");
    let ground = document.querySelectorAll("#secondary .ground");
    let magic = document.querySelectorAll("#secondary .magic");
    let normal = document.querySelectorAll("#secondary .normal");


    document.getElementById("secondary").hidden = false;
    console.log(event.target.children[event.target.selectedIndex]);
    console.log(event.target.selectedIndex);

    switch (event.target.children[event.target.selectedIndex].className) {
        case "fire":
            console.log("fire");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            water.forEach(e => {
                e.disabled = true;
            });
            ice.forEach(e => {
                e.disabled = true;
            });
            plant.forEach(e => {
                e.disabled = true;
            });
            break;
        case "water":
            console.log("water");
            Array.from(event.target.children).forEach(e => {
                e.disabled = true;
                if (e.className === event.target.children[event.target.selectedIndex].className ){
                    e.disabled = false;
                }
            });
            fire.forEach(e => {
                e.disabled = true;
            });
            ice.forEach(e => {
                e.disabled = true;
            });
            break;
        case "plant":
            console.log("plant");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            fire.forEach(e => {
                e.disabled = true;
            });
            ice.forEach(e => {
                e.disabled = true;
            });
            electric.forEach(e => {
                e.disabled = true;
            });
            break;
        case "electric":
            console.log("electric");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            ground.forEach(e => {
                e.disabled = true;
            });
            ice.forEach(e => {
                e.disabled = true;
            });
            water.forEach(e => {
                e.disabled = true;
            });
            magic.forEach(e => {
                e.disabled = true;
            });
            break;
        case "ice":
            console.log("ice");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            fire.forEach(e => {
                e.disabled = true;
            });
            plant.forEach(e => {
                e.disabled = true;
            });
            electric.forEach(e => {
                e.disabled = true;
            });
            break;
        case "ground":
            console.log("ground");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            electric.forEach(e => {
                e.disabled = true;
            });
            water.forEach(e => {
                e.disabled = true;
            });
            break;
        case "magic":
            console.log("magic");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            break;
        case "normal":
            console.log("normal");
            Array.from(event.target.children).forEach(e => {

                e.disabled = e.className !== event.target.children[event.target.selectedIndex].className;
            });
            ground.forEach(e => {
                e.disabled = true;
            });
            ice.forEach(e => {
                e.disabled = true;
            });
            water.forEach(e => {
                e.disabled = true;
            });
            fire.forEach(e => {
                e.disabled = true;
            });
            electric.forEach(e => {
                e.disabled = true;
            });
            plant.forEach(e => {
                e.disabled = true;
            });
            break;
        default:
            console.log("WRONG");

    }
}
