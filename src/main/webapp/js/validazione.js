document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("formRegistrazione");
    const emailInput = document.getElementById("email");
    const inputs = form.querySelectorAll("input");

    // Espressioni regolari per la validazione
    const regexNome = /^[a-zA-Z\s]{2,50}$/;
    const regexCognome = /^[a-zA-Z\s]{2,50}$/;
    const regexEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const regexPassword = /^(?=.*[A-Z])(?=.*\d)[A-Za-z\d@$!%*?&]{8,}$/;

    let emailValidaDb = false; // Flag per la validazione asincrona

    // Evidenzia il campo attivo (Focus)
    inputs.forEach(input => {
        input.addEventListener("focus", function() {
            this.style.borderColor = "var(--giallo-sole)";
            this.style.boxShadow = "0 0 5px var(--giallo-sole)";
        });
        input.addEventListener("blur", function() {
            this.style.borderColor = "var(--grigio-neutro)";
            this.style.boxShadow = "none";
        });
    });

    // Controllo Email in tempo reale con AJAX (Fetch API)
    emailInput.addEventListener("blur", function() {
        const email = this.value;
        const erroreEmail = document.getElementById("errore-email");

        if (regexEmail.test(email)) {
            // Se la regex passa, chiedo al server se esiste già
            fetch('/dispensa-di-giu/VerificaEmailServlet?email=' + encodeURIComponent(email))
                .then(response => response.json())
                .then(data => {
                    if (data.esiste) {
                        mostraErrore(emailInput, erroreEmail, "Questa email è già registrata.");
                        emailValidaDb = false;
                    } else {
                        nascondiErrore(emailInput, erroreEmail);
                        emailValidaDb = true;
                    }
                })
                .catch(error => console.error('Errore Fetch:', error));
        }
    });

    // Validazione finale al Submit
    form.addEventListener("submit", function(event) {
        let isValid = true;

        // Validazione Nome
        const nomeInput = document.getElementById("nome");
        const erroreNome = document.getElementById("errore-nome");
        if (!regexNome.test(nomeInput.value)) {
            mostraErrore(nomeInput, erroreNome, "Inserisci un nome valido (solo lettere, min 2).");
            isValid = false;
        } else {
            nascondiErrore(nomeInput, erroreNome);
        }

        // Validazione Cognome
        const cognomeInput = document.getElementById("cognome");
        const erroreCognome = document.getElementById("errore-cognome");
        if (!regexCognome.test(cognomeInput.value)) {
            mostraErrore(cognomeInput, erroreCognome, "Inserisci un cognome valido (solo lettere, min 2).");
            isValid = false;
        } else {
            nascondiErrore(cognomeInput, erroreCognome);
        }
        
        // Validazione Password
        const passwordInput = document.getElementById("password");
        const errorePassword = document.getElementById("errore-password");
        if (!regexPassword.test(passwordInput.value)) {
            mostraErrore(passwordInput, errorePassword, "La password deve avere almeno 8 caratteri, un numero e una maiuscola.");
            isValid = false;
        } else {
            nascondiErrore(passwordInput, errorePassword);
        }

        // Validazione Email (Regex + Flag DB)
        if (!regexEmail.test(emailInput.value) || !emailValidaDb) {
            mostraErrore(emailInput, document.getElementById("errore-email"), "Email non valida o già in uso.");
            isValid = false;
        }

        // Se ci sono errori, blocco l'invio al server
        if (!isValid) {
            event.preventDefault();
        }
    });

    // Funzioni di supporto per mostrare/nascondere errori inline (NO ALERT)
    function mostraErrore(input, spanErrore, messaggio) {
        input.style.borderColor = "var(--rosso-pomodoro)";
        spanErrore.textContent = messaggio;
        spanErrore.style.display = "block";
    }

    function nascondiErrore(input, spanErrore) {
        input.style.borderColor = "var(--grigio-neutro)";
        spanErrore.textContent = "";
        spanErrore.style.display = "none";
    }
});

