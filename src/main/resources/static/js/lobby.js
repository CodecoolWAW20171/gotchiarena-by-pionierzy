let lobby = {
    client: null,
    connect() {
        let socket = new SockJS("/lobby");
        let stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            loadRooms();
            console.log("Connected: " + frame);
            stompClient.subscribe("/topic/rooms", function (rooms) {
                // loadRooms(JSON.parse(rooms.body).content);
            });
        });
        this.client = stompClient;
    },
    loadRooms() {

    },
    addRoom() {
        this.client.send("/app/addroom", {}, JSON.stringify({"name": "room23"}));
    }
};

function loadRooms(rooms) {

}


$(function () {
    $("form").on("submit", function (e) {
        e.preventDefault();
    });
    lobby.connect();
    $("#addRoom").click(function () {lobby.addRoom();});
});

