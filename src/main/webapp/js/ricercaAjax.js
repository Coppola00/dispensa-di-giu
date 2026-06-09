document.addEventListener("DOMContentLoaded", function() {
    
    // --- ELEMENTI DEL DOM (Allineati al tuo header.jsp) ---
    const searchInput = document.getElementById("barraRicerca");
    const resultsBox = document.getElementById("suggerimentiRicerca");

    // --- LOGICA AJAX (Ascolto della digitazione) ---
    searchInput.addEventListener("keyup", function() {
        const query = searchInput.value.trim();

        // Si attiva solo se l'utente scrive almeno 2 caratteri
        if (query.length >= 2) {
            
            // Chiamata alla Servlet usando la variabile globale contextPath definita nell'header
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
                            
                            // Sicurezza sul link: aggiungiamo il contextPath per evitare rotte errate
                            div.onclick = () => {                            
							window.location.href = contextPath + "/Catalogo?action=dettaglio&idProdotto=" + prodotto.id;
                            };
                            
                            resultsBox.appendChild(div);
                        });
                        resultsBox.style.display = "block"; // Mostra la tendina
                    } else {
                        resultsBox.style.display = "none"; // Nascondi se vuoto
                    }
                })
                .catch(error => console.error("Errore AJAX:", error));
        } else {
            resultsBox.style.display = "none";
            resultsBox.innerHTML = "";
        }
    });

    // --- CHIUSURA AUTOMATICA ---
    // Nascondi la tendina dei suggerimenti se l'utente clicca altrove nella pagina
    document.addEventListener("click", function(event) {
        if (event.target !== searchInput && event.target !== resultsBox) {
            resultsBox.style.display = "none";
        }
    });
});
