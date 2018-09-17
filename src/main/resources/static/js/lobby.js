class Spinner {
    constructor(id) {
        this.spinner = document.getElementById(id);
    }

    show() {
        if (this.spinner) this.spinner.style.display = "block";
    }

    hide() {
        if (this.spinner) this.spinner.style.display = "none";
    }
}

function showSnackbar(msg) {
    let snackbar = document.getElementById("snackbar");
    if (snackbar) {
        snackbar.textContent = msg;
        snackbar.style.display = "block";
        window.setTimeout(() => {
            snackbar.classList.add("show");
        }, 5);
        setTimeout(() => {
            snackbar.classList.remove("show");
            window.setTimeout(() => {
                snackbar.style.display = "none";
                snackbar.textContent = null;
            }, 5);
        }, 3000);
    }
}

class WrappedSocket {
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

    subscribeSingle(destination) {
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

let lobbyService = {
    client: new WrappedSocket("/lobby"),
    connect() {
        return this.client.connect();
    },
    loadRooms() {
        return this.client.subscribeSingle("/app/rooms");
    },
    addRoom(room) {
        return this.client.send("/app/add-room", JSON.stringify(room));
    }
};

let lobby = {
    service: lobbyService,
    table: document.getElementById("rooms"),
    spinner: new Spinner("spinner"),
    loadRooms(rooms) {
        let tbody = this.table.getElementsByTagName("tbody");
        if (tbody) {
            tbody = tbody[0];
            rooms.forEach(function (room) {
                let tr = document.createElement("tr");
                let row = [
                    `<td>${room.name}</td>`,
                    `<td>${room.count}/2</td>`,
                    `<td>${room.owner.name}</td>`,
                    `<button id="" class="btn-flat">+</button>`
                ].join("");
                tr.innerHTML = row;
                tbody.appendChild(tr);
            });
        }
    }
};

(function () {
    document.querySelectorAll("form").forEach(elem =>
        elem.addEventListener("submit", evt => evt.preventDefault()));

    document.getElementById("addRoom").addEventListener("click", () => {
        let room = {name: "Adding test..."};
        lobbyService.addRoom(room);
    });

    lobbyService.connect().then(function () {
        return lobbyService.loadRooms();
    }).then(function (rooms) {
        lobby.loadRooms(rooms);
        lobby.spinner.hide();
        lobby.table.style.display = "table";
    }, function () {
        showSnackbar("Could not connect to server.");
    });
})();



