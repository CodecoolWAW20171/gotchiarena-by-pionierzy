document.getElementById("speed").addEventListener("input",updatePointsLeft);
document.getElementById("attack").addEventListener("input",updatePointsLeft);
document.getElementById("defence").addEventListener("input",updatePointsLeft);



function updatePointsLeft(){
    let speedValue = document.getElementById("speed").value;
    let attackValue = document.getElementById("attack").value;
    let defenceValue = document.getElementById("defence").value;

    let pointsLeft = 200 - speedValue - attackValue - defenceValue;
    document.getElementById("speed").setAttribute("max",pointsLeft + speedValue);
    document.getElementById("attack").setAttribute("max",pointsLeft + attackValue);
    document.getElementById("defence").setAttribute("max",pointsLeft + defenceValue);
    document.getElementById("pointsLeft").innerText  = pointsLeft >= 0? pointsLeft.toString() : 0;

}