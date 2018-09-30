function iconService(type){
    switch (type) {
        case 'FIRE':
            return "fa-fire";
        case 'WATER':
            return "fa-tint";
        case 'PLANT':
            return "fa-leaf";
        case 'ELECTRIC':
            return "fa-bolt";
        case 'ICE':
            return "fa-snowflake";
        case 'GROUND':
            return "fa-gem";
        case 'MAGIC':
            return "fa-star";
        case 'NORMAL':
            return "fa-hand-rock"
    }
}

// let Type1 = document.getElementById("type1");
// let Type2 = document.getElementById("type2");

var els = document.getElementsByClassName("fas");

Array.prototype.forEach.call(els, function(el) {
    console.log(el.parentElement.getAttribute("value"));
    el.classList.toggle(iconService(el.parentElement.getAttribute("value")), true);
});