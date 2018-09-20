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
}

function connect() {
    var socket = new SockJS('/room/info');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message/'+roomId, function (log) {
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

function sendAction(value) {
    stompClient.send("/app/room/action/"+roomId, {}, JSON.stringify({'data': value }));
}

function showLogs(message) {
    let content = $("#logs").html();
    $("#logs").html("");
    $("#logs").append("<tr><td>" + message + "</td></tr>");
    $("#logs").append(content);
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

