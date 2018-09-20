var stompClient = null;
var roomId = $("#roomId").val();

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#logs").html("");
    stompClient.send("/app/room/action/"+roomId+"/start", {}, JSON.stringify({ "data":"start" }));
}

function connect() {
    var socket = new SockJS('/room/info');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message/'+roomId, function (message) {
            let log = createMessage(message);
            showLogs(log);
        });
    });

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

        let ownA = log.ownerAction;
        let oppA = log.opponentAction;
        let ownLoss = log.ownerHPLoss;
        let oppLoss = log.opponentHPLoss;

        let log1 = "Owner's Gotchi used " + ownA + " and lost " + ownLoss + " of its HP. ";
        let log2 = "Opponent's Gotchi used " + oppA + " and lost " + oppLoss + " of its HP" ;
        return log1 + log2;
    }
    return null
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    // $( "#connect" ).click(function() { connect(); });
    // $( "#disconnect" ).click(function() { disconnect(); });
    connect();
    $( "#attack1" ).click(function() { sendAction($("#attack1").val()); });
    $( "#attack2" ).click(function() { sendAction($("#attack2").val()); });
    $( "#defend" ).click(function() { sendAction($("#defend").val()); });
    $( "#evade" ).click(function() { sendAction($("#evade").val()); });
});

