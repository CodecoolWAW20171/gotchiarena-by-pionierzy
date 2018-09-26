var stompClient = null;
var roomId = $("#roomId").val();
var socket;

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
        });
    });
}

function showOpponent(data) {
    let nameSpan = $("#opponent");
    let gotchiSpan = $("#opponentGotchi");
    let hp = $("#opponentHP");

    nameSpan.html(data.opponent.username+": ");
    gotchiSpan.html(data.opponentGotchi.name + ", Type: " + data.opponentGotchi.type + ", Secondary Attack: "
                    + data.opponentGotchi.secondaryAttack);
    hp.html(", HP: "+data.opponentGotchi.health);
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
    $("#attack1").prop("disabled", true);
    $("#attack2").prop("disabled", true);
    $("#defend").prop("disabled", true);
    $("#evade").prop("disabled", true);
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
        $("#attack1").prop("disabled", false);
        $("#attack2").prop("disabled", false);
        $("#defend").prop("disabled", false);
        $("#evade").prop("disabled", false);
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
        let oppA = log.opponentActionType;
        let ownLoss = log.ownerHPLoss;
        let oppLoss = log.opponentHPLoss;

        let log1 = "Owner's Gotchi used " + ownA + " and lost " + ownLoss + " of its HP. ";
        let log2 = "Opponent's Gotchi used " + oppA + " and lost " + oppLoss + " of its HP" ;
        return log1 + log2;
    }
    return null
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

