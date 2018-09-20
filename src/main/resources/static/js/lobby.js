/** Wrapper class to create Stomp over SockJS client */
class WrappedSocket {
    /**
     * Create new client
     * @param url server endpoint prefix
     */
    constructor(url) {
        this.client = Stomp.over(new SockJS(url));
    }

    connect() {
        return new Promise((resolve, reject) => {
            if (!this.client) {
                reject("Client not created.");
            } else {
                this.client.connect({}, function (frame) {
                    resolve(frame);
                }, function (error) {
                    reject("Connection error." + error);
                });
            }
        });
    }

    disconnect() {
        this.client.disconnect();
    }

    subscribe(destination) {
        return new Promise((resolve, reject) => {
            if (!this.client) {
                reject("Client not created.");
            } else {
                this.client.subscribe(destination, function (message) {
                    resolve(JSON.parse(message.body));
                });
            }
        });
    }

    send(destination, object, headers) {
        this.client.send(destination, headers, object);
    }
}

/** Service object for connecting to server endpoints */
let lobbyService = {
    client: new WrappedSocket("/lobby"),
    username: null,
    connect() {
        return this.client.connect().then(function (frame) {
            return frame.headers["user-name"];
        });
    },
    loadRooms() {
        return this.client.subscribe("/app/rooms");
    },
    addRoom(room) {
        return this.client.send("/app/add-room", JSON.stringify(room));
    },
    fetchNewRoom() {
        return this.client.subscribe("/topic/add-room");
    },
    joinRoom(room) {
        return this.client.send("/app/update-room", JSON.stringify(room));
    },
    fetchRoomUpdate() {
        return this.client.subscribe("/topic/update-room");
    },
    getGotchis() {
        return this.client.subscribe("/app/gotchis");
    }
};

/** Object for updating display */
let lobbyDisplay = {
    table: document.getElementById("rooms"),
    tbody: document.getElementById("rooms-list"),
    addBtn: document.getElementById("addRoom"),
    modal: $("#modalAdd"),
    spinner: new Spinner("spinner"),
    init() {
        document.querySelectorAll("form").forEach(elem =>
            elem.addEventListener("submit", evt => evt.preventDefault()));

        this.addBtn.addEventListener("click", () => this.modal.modal());

        document.getElementById("requestRoom").addEventListener("click", () => {
            let roomName = document.getElementById("room-name").value;
            lobbyService.addRoom({name: roomName});

        });
    },
    showRooms(rooms) {
        rooms.forEach(this.addRoomToList, this);
        this.spinner.hide();
        this.addBtn.style.display = "inline-block";
        this.table.style.display = "table";
    },
    generateRow(room) {
        let isDisabled = (room.count > 1) ||
            (room.ownerName === lobbyService.username) || (room.opponentName === lobbyService.username);
        return [
            `<td>${room.name}</td>`,
            `<td>${room.count}/2</td>`,
            `<td>${room.ownerName}</td>`,
            `<td>vs</td>`,
            `<td>${room.opponentName ? room.opponentName : "-"}</td>`,
            `<td><button id="join-${room.id}" class="btn btn-add" ${isDisabled ? "disabled" : ""}>+</button></td>`
            `<td><button id="join-${room.id}" class="btn-flat" ${isDisabled ? "disabled" : ""}>+</button></td>`,
            `<td><a href="/room/${room.id}">TEST VIEW</a> </td>`
        ].join("");
    },
    addRoomToList(room) {
        let tr = document.createElement("tr");
        tr.setAttribute("id", "room-" + room.id);
        tr.innerHTML = this.generateRow(room);
        tr.getElementsByTagName("button")[0].addEventListener("click", () => {
            lobbyService.joinRoom({"id": room.id});
        });
        this.tbody.appendChild(tr);
    },
    updateRoomInList(room) {
        let tr = document.getElementById("room-" + room.id);
        if (tr) {
            tr.innerHTML = this.generateRow(room);
            tr.getElementsByTagName("button")[0].addEventListener("click", () => {
                lobbyService.joinRoom({"id": room.id});
            });
        }
    },
    disableAll() {
        this.addBtn.disabled = true;
        this.tbody.querySelectorAll("tr").forEach(tr => {
            tr.getElementsByTagName("button")[0].disabled = true;
        });
    }
};

(function () {
    lobbyService.connect().then(function (username) {
        lobbyService.username = username;
        return lobbyService.loadRooms();
    }).then(function (rooms) {
        lobbyDisplay.init();
        lobbyDisplay.showRooms(rooms);
        lobbyService.fetchNewRoom().then(function (room) {
            lobbyDisplay.addRoomToList(room);
            lobbyDisplay.disableAll();
            lobbyDisplay.modal.modal("hide");
            utils.showSnackBar("Room " + room.name + " has been created.");
        });
        lobbyService.fetchRoomUpdate().then(function (room) {
            lobbyDisplay.updateRoomInList(room);
        });
    }, function () {
        utils.showSnackBar("Could not connect to server.");
    });
})();


