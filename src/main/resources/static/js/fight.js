var owner = null;
var opponent = null;

var stompClient = null;

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
}

function connect() {
    var socket = new SockJS('/room/info');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message', function (log) {
            console.log("log:");
            console.log(log);
            console.log("-----------");
            console.log(log.body);
            console.log("-----------");
            showLogs(JSON.parse(log.body).data);
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

function sendAttack() {
    stompClient.send("/app/attack", {}, JSON.stringify({'data': $("#send").val()}));
}

function showLogs(message) {
    $("#logs").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    // $( "#connect" ).click(function() { connect(); });
    // $( "#disconnect" ).click(function() { disconnect(); });
    connect();
    $( "#send" ).click(function() { sendAttack(); });
});

