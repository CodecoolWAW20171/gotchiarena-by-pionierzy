var stompClient = null;
var roomId = $("#roomId").val();
var socket;
var ownerGotchiName;
var opponentGotchiName;
var ownerHP;
var opponentHP;

function setConnected(connected) {
    // $("#connect").prop("disabled", connected);
    // $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    console.log("OPEN");
    $("#logs").html("");
    stompClient.send("/app/room/action/"+roomId+"/start", {}, JSON.stringify({ "data":"start" }));
}

function connect() {
    socket = new SockJS('/room/info');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        setConnected(true);
        stompClient.subscribe('/topic/room/action/'+roomId+'/setopponent', function (message) {
            let data = JSON.parse(message.body);
            showOpponent(data);
        });
        stompClient.subscribe('/topic/message/'+roomId, function (message) {
            let log = createMessage(message);
            showLogs(log);
            checkEndGame();
        });
    });
}

function showOpponent(data) {
    let nameSpan = $("#opponent");
    let gotchiSpan = $("#opponentGotchi");
    let hp = $("#opponentHP");

    nameSpan.html(data.opponent.username+": ");
    gotchiSpan.html(data.opponentGotchi.name + ",  " + data.opponentGotchi.type + " / "
                    + data.opponentGotchi.secondaryAttack + ", ");
    hp.html("HP: "+data.opponentGotchi.health);

    ownerGotchiName = data.ownerGotchi.name;
    opponentGotchiName = data.opponentGotchi.name;
    ownerHP = parseFloat(data.ownerGotchi.health);
    opponentHP = parseFloat(data.opponentGotchi.health);
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendAction(value) {
    stompClient.send("/app/room/action/"+roomId, {}, JSON.stringify({ value }));
    disableButtons(true);
    waiting();
}

function showLogs(message) {
    if (message != null){
        $("#wait").html("");
        let logsDiv = $("#logs");
        let content = logsDiv.html();
        logsDiv.html("");
        logsDiv.append("<tr><td>" + message + "</td></tr>");
        logsDiv.append(content);
        disableButtons(false);
    }
}

function waiting() {
    let waitDiv = $("#wait");
    waitDiv.html("Waiting for your opponent...");
}

function createMessage(message){
    if (message.body != "null") {
        console.log("log:");
        let log = JSON.parse(message.body);

        let ownA = log.ownerActionType;
        let ownInfo = log.ownerActionInfo;
        let oppA = log.opponentActionType;
        let oppInfo = log.oppActionInfo;
        let ownLoss = log.ownerHPLoss;
        let oppLoss = log.opponentHPLoss;
        ownerHP = ownerHP - parseFloat(ownLoss);
        opponentHP = opponentHP - parseFloat(oppLoss);

        if (ownerHP < 0){
            ownerHP = 0;
        }
        if (opponentHP < 0){
            opponentHP = 0;
        }

        let log1 = ownerGotchiName+" used " +ownA+ownInfo+ " and took away "+oppLoss+ " of "+opponentGotchiName+" HP. ";
        let log2 = opponentGotchiName+" used " +oppA+oppInfo+ " and took away "+ownLoss+ " of "+ownerGotchiName+" HP.";
        $("#ownerHP").html(ownerHP);
        $("#opponentHP").html("HP: "+opponentHP);
        return log1 + log2;
    }
    return null
}

function checkEndGame(){
    if (ownerHP === opponentHP && ownerHP === 0){
        showLogs("DRAW! Congratulations!");
        disableButtons(true);
    }
    else if (ownerHP === 0){
        showLogs(opponentGotchiName+" WON!");
        disableButtons(true);
    }
    else if (opponentHP === 0){
        showLogs(ownerGotchiName+" WON!");
        disableButtons(true);
    }
}

function disableButtons(bool){
    $("#attack1").prop("disabled", bool);
    $("#attack2").prop("disabled", bool);
    $("#defend").prop("disabled", bool);
    $("#evade").prop("disabled", bool);
}

$(function () {
    connect();
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    // $( "#connect" ).click(function() { connect(); });
    // $( "#disconnect" ).click(function() { disconnect(); });

    $( "#attack1" ).click(function() { sendAction($("#attack1").val()); });
    $( "#attack2" ).click(function() { sendAction($("#attack2").val()); });
    $( "#defend" ).click(function() { sendAction($("#defend").val()); });
    $( "#evade" ).click(function() { sendAction($("#evade").val()); });
});

