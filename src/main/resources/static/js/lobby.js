/** Service object for connecting to server endpoints */
let lobbyService = {
    client: new WrappedSocket("/lobby"),
    username: null,
    userRoomId: null,
    connect() {
        return this.client.connect().then(function (frame) {
            return frame.headers["user-name"];
        });
    },
    fetchNotifications() {
        return this.client.subscribe("/user/queue/notify");
    },
    fetchErrors() {
        return this.client.subscribe("/user/queue/errors");
    },
    loadRooms() {
        return this.client.subscribeSingle("/app/rooms");
    },
    addRoom() {
        return this.client.send("/app/add-room");
    },
    fetchNewRoom() {
        return this.client.subscribe("/topic/add-room");
    },
    fetchRoomUpdate() {
        return this.client.subscribe("/topic/update-room");
    },
    joinRoom(id) {
        return this.client.send("/app/update-room", JSON.stringify({"id": id, "action": "JOIN"}));
    },
    leaveRoom(id) {
        return this.client.send("/app/update-room", JSON.stringify({"id": id, "action": "LEAVE"}));
    },
    deleteRoom(id) {
        return this.client.send("/app/delete-room", JSON.stringify({"id": id}));
    },
    fetchRoomDelete() {
        return this.client.subscribe("/topic/delete-room");
    }
};

/** Object for updating display */
let lobbyDisplay = {
    table: document.getElementById("rooms"),
    tbody: document.getElementById("rooms-list"),
    addBtn: document.getElementById("addRoom"),
    modal: $("#modal"),
    spinner: new Spinner("spinner"),
    init() {
        document.querySelectorAll("form:not([data-sub])").forEach(elem =>
            elem.addEventListener("submit", evt => evt.preventDefault()));

        this.addBtn.addEventListener("click", () => lobbyService.addRoom());
        if (lobbyService.userRoomId != null) this.addBtn.disabled = true;

    },
    showRooms(rooms) {
        rooms.forEach(this.addRoomToList, this);
        this.spinner.hide();
        document.getElementById("creator").style.display = "block";
        this.table.style.display = "table";
    },
    generateRow(room) {
        let isDisabled = (lobbyService.username === room.ownerName) ||
            (lobbyService.username === room.opponentName) ||
            (room.count > 1);
        return [
            `<td>${room.ownerName}'s room</td>`,
            `<td>${room.count}/2</td>`,
            `<td>${room.ownerName}</td>`,
            `<td>vs</td>`,
            `<td>${room.opponentName ? room.opponentName : "-"}</td>`,
            `<td><button id="join-${room.id}" class="btn btn-add" `,
            `${isDisabled ? "disabled" : ""}>`,
            `<i class="material-icons">add</i></button></td>`
        ].join("");
    },
    addRoomToList(room) {
        let tr = document.createElement("tr");
        tr.setAttribute("id", "room-" + room.id);
        tr.innerHTML = this.generateRow(room);
        tr.getElementsByTagName("button")[0].addEventListener("click", () => {
            lobbyService.joinRoom(room.id);
        });
        this.tbody.appendChild(tr);
        if (lobbyService.username === room.ownerName || lobbyService.username === room.opponentName) {
            this.addBtn.disabled = true;
            this.showRoomModal(room);
        }
    },
    updateRoomInList(room) {
        let tr = document.getElementById("room-" + room.id);
        if (tr) {
            tr.innerHTML = this.generateRow(room);
            tr.getElementsByTagName("button")[0].addEventListener("click", () => {
                lobbyService.joinRoom(room.id);
            });
            if (!(this.modal.data("bs.modal") || {})._isShown &&
                (lobbyService.username === room.ownerName || lobbyService.username === room.opponentName)) {
                this.addBtn.disabled = true;
                this.showRoomModal(room);
            }
        }
    },
    deleteRoomFromList(id) {
        document.getElementById("room-" + id).remove();
    },
    showRoomModal(room) {
        $("#modalTitle").text(room.ownerName + "'s room");
        $("#leave").click(() => {
            if (lobbyService.username === room.ownerName) {
                lobbyService.deleteRoom(room.id);
            } else {
                lobbyService.leaveRoom(room.id);
            }
        });
        $("#fightPage").attr("action", "/room/" + room.id);
        $("#begin").click(() => {
            $("#fightPage").submit();
        });
        this.modal.modal({
            backdrop: "static",
            keyboard: false
        });
    }
};

(function () {
    lobbyService.connect().then(function (username) {
        lobbyService.username = username;
        return lobbyService.loadRooms();
    }).then(function (response) {
        lobbyService.userRoomId = response.userRoomId;
        lobbyService.fetchNotifications().then(null, null, msg => {
            utils.showSnackBar(msg.msg);
            if (typeof msg.userRoomId !== "undefined") {
                if (msg.userRoomId === null) {
                    lobbyDisplay.addBtn.disabled = false;
                    lobbyDisplay.modal.modal("hide");
                } else {
                    lobbyDisplay.addBtn.disabled = true;
                }
                lobbyService.userRoomId = msg.userRoomId;
            }
        });
        lobbyService.fetchErrors().then(null, null, error => utils.showSnackBar(error.msg));
        lobbyService.fetchNewRoom().then(null, null, room => lobbyDisplay.addRoomToList(room));
        lobbyService.fetchRoomUpdate().then(null, null, room => lobbyDisplay.updateRoomInList(room));
        lobbyService.fetchRoomDelete().then(null, null, id => lobbyDisplay.deleteRoomFromList(id));

        lobbyDisplay.init();
        lobbyDisplay.showRooms(response.rooms);
    }, function () {
        utils.showSnackBar("Could not connect to server.");
    });
})();


