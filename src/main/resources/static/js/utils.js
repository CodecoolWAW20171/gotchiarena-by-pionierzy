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
        let deferred = $.Deferred();
        if (!this.client) {
            deferred.reject("STOMP client not created");
        } else {
            this.client.subscribe(destination, function (message) {
                deferred.notify(JSON.parse(message.body));
            });
        }
        return deferred.promise();
    }

    send(destination, object, headers) {
        this.client.send(destination, headers, object);
    }
}

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

let utils = {
    showSnackBar(msg) {
        let snackBar = document.getElementById("snackBar");
        if (snackBar) {
            snackBar.textContent = msg;
            snackBar.style.display = "block";
            window.setTimeout(() => {
                snackBar.classList.add("show");
            }, 5);
            setTimeout(() => {
                snackBar.classList.remove("show");
                window.setTimeout(() => {
                    snackBar.style.display = "none";
                    snackBar.textContent = null;
                }, 5);
            }, 3000);
        }
    }
};

