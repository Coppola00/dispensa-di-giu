document.addEventListener("DOMContentLoaded", function() {
    
    const searchInput = document.getElementById("barraRicerca");
    const resultsBox = document.getElementById("suggerimentiRicerca");

    searchInput.addEventListener("keyup", function() {
        const query = searchInput.value.trim();

        if (query.length >= 2) {
            
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

    // Nascondi se clicca in un altra parte della pagina
    document.addEventListener("click", function(event) {
        if (event.target !== searchInput && event.target !== resultsBox) {
            resultsBox.style.display = "none";
        }
    });
});
