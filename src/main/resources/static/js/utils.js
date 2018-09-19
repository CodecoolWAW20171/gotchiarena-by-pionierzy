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

