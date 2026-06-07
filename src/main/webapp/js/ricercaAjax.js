document.addEventListener("DOMContentLoaded", function() {
    
    // --- ELEMENTI DEL DOM ---
    const btnToggleRicerca = document.getElementById("btn-toggle-ricerca");
    const contenitoreRicerca = document.getElementById("contenitore-ricerca-nascosto");
    const searchInput = document.getElementById("barraRicerca");
    const resultsBox = document.getElementById("suggerimentiRicerca");

    // --- 1. LOGICA TOGGLE (Mostra/Nascondi la barra) ---
    btnToggleRicerca.addEventListener("click", function(event) {
        event.stopPropagation(); // Evita che il click si propaghi e chiuda subito la barra
        
        // Se è nascosto, mostralo. Se è visibile, nascondilo.
        if (contenitoreRicerca.style.display === "none") {
            contenitoreRicerca.style.display = "block";
            searchInput.focus(); // Metti subito il cursore dentro per scrivere
        } else {
            contenitoreRicerca.style.display = "none";
            resultsBox.style.display = "none"; // Chiudi anche gli eventuali suggerimenti
            searchInput.value = ""; // Pulisci il campo
        }
    });

    // --- 2. LOGICA AJAX (Quella che avevamo già fatto) ---
    searchInput.addEventListener("keyup", function() {
        const query = searchInput.value.trim();

        if (query.length >= 2) {
            // Chiamata alla Servlet RicercaAjaxServlet
            fetch(contextPath + "/RicercaAjax?q=" + encodeURIComponent(query))
                .then(response => {
                    if (!response.ok) throw new Error("Errore di rete");
                    return response.json();
                })
                .then(data => {
                    resultsBox.innerHTML = ""; 
                    if (data.length > 0) {
                        data.forEach(prodotto => {
                            const div = document.createElement("div");
                            div.className = "suggerimento-item";
                            div.textContent = prodotto.nome;
                            div.onclick = () => window.location.href = "DettaglioProdotto?id=" + prodotto.id;
                            resultsBox.appendChild(div);
                        });
                        resultsBox.style.display = "block";
                    } else {
                        resultsBox.style.display = "none";
                    }
                })
                .catch(error => console.error("Errore AJAX:", error));
        } else {
            resultsBox.style.display = "none";
            resultsBox.innerHTML = "";
        }
    });

    // --- 3. CHIUSURA AUTOMATICA ---
    // Nascondi tutto se l'utente clicca da qualsiasi altra parte nella pagina
    document.addEventListener("click", function(event) {
        // Se il click NON è dentro il contenitore di ricerca e NON è sul bottone
        if (!contenitoreRicerca.contains(event.target) && event.target !== btnToggleRicerca) {
            contenitoreRicerca.style.display = "none";
            resultsBox.style.display = "none";
        }
    });
});