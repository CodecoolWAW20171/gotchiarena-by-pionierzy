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
    },
    askForGotchiList(){
        this.client.send("/app/room/gotchi/" + this.username);
    },
    fetchGotchiList(){
        return this.client.subscribe("/topic/getGotchi/" + this.username);
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
        //lobbyService.fetchGotchiList();
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
            `<td><button id="join-${room.id}" class="btn btn-add" `,
            `${isDisabled ? "disabled" : ""}>`,
            `<i class="material-icons">add</i></button></td>`,
            `<td>${room.ownerName}'s room</td>`,
            `<td>${room.count}/2</td>`,
            `<td>${room.ownerName}</td>`,
            `<td>vs</td>`,
            `<td>${room.opponentName ? room.opponentName : "-"}</td>`
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
        lobbyService.askForGotchiList();
        $("#modalTitle").text(room.ownerName + "'s room");
        $("#leave").click( ()=> {
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
    },
    generateModalRow(gotchi, i){
        return [
            `<td>${gotchi.name}</td>`,
            `<td>${gotchi.type}</td>`,
            `<td>${gotchi.secondaryAttack}</td>`,
            `<td>${gotchi.speed} / ${gotchi.attack} / ${gotchi.defence}</td>`,
            `<td><input type="radio" name="gotchiNumber" value="${i}"> </td>`
        ].join("");
    },
    createRadio(message){
        console.log("INFO>>>>>>>>,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        console.log(message);
        let spinner = document.getElementById("sp");
        document.getElementById("modalContent").removeChild(spinner);
        let contentDiv = document.getElementById("t");
        let th = "<table id='gotchistable' class='table table-responsive-md table-dark'><tr><td>Name</td><td>Type</td><td>Second</td><td>Spd/Att/Def</td><td>Check</td></tr></table>";
        contentDiv.innerHTML = th;
        let gotchisTable = document.getElementById("gotchistable");
        contentDiv.appendChild(gotchisTable);
        for(let i=0; i<message.length; i++){
            let tr = document.createElement("tr");
            tr.innerHTML = this.generateModalRow(message[i], i);
            console.log(tr);
            console.log(gotchisTable);
            gotchisTable.appendChild(tr);
        }
    },
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
        lobbyService.fetchGotchiList().then(null, null, message => lobbyDisplay.createRadio(message));
        lobbyDisplay.init();
        lobbyDisplay.showRooms(response.rooms);
    }, function () {
        utils.showSnackBar("Could not connect to server.");
    });
})();


