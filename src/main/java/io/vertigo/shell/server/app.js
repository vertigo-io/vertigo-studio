
		const socket = new WebSocket("ws://localhost:8080");
		const chat = document.getElementById("chat");
		const status = document.getElementById("status");
		const statusText = document.getElementById("status-text");

		socket.onopen = () => {
			status.className = 'status-connected';
			statusText.textContent = 'Connected';
		};

		socket.onclose = () => {
			status.className = 'status-disconnected';
			statusText.textContent = 'Disconnected';
		};

		socket.onerror = () => {
			status.className = 'status-error';
			statusText.textContent = 'Error';
		};

		const liveMap = new Map();
		socket.onmessage = (event) => {
			try {
				const parsed = JSON.parse(event.data);
				if (parsed.type === "table") {
					addTable(parsed.data);
				} else if (parsed.type === "json") {
					addJson(parsed.data);					
				} else if (parsed.type === "progressBar") {
					addProgressBar(parsed.data);
				} else if (parsed.type === "live") {
					updateLive(parsed.data);
				} else if (parsed.type === "list") {
					addList(parsed.data);					
				} else if (parsed.type === "barChart") {
					addBarChart(parsed.data);					
				} else {
					addMessage(JSON.stringify(parsed), "response");
				}
			} catch (error) {
			    alert(`Erreur : ${error.message}`);
				addMessage(event.data, "response");
			}
		};

		function sendMessage() {
			const input = document.getElementById("prompt");
			const message = input.value.trim();
			if (message) {
				addMessage(message, "user");
				socket.send(message);
				input.value = "";
			}
		}

		function addMessage(text, type) {
			const div = document.createElement("div");
			div.className = `chat-message ${type === "user" ? "user-message" : "response-message"}`;
			div.textContent = text;
			chat.appendChild(div);
			chat.scrollTop = chat.scrollHeight;
		}

		function toggleCollapse(element) {
			const content = element.nextElementSibling;
			const collapseIcon = element.querySelector('.collapse-icon');
			const container = element.parentElement;

			if (content.classList.contains('collapsed')) {
				content.classList.remove('collapsed');
				container.classList.remove('collapsed');
			} else {
				content.classList.add('collapsed');
				container.classList.add('collapsed');
			}
		}

		function getDataTypeIcon(type) {
			const iconMap = {
				//on récupère des icones de Lucid à partir des types
				'table': 'table-2',
				'json': 'code-2',
				'list': 'list',
				'tree': 'git-branch',
				'grid': 'grid-3x3',
				'database': 'database',
				'barChart': 'chart-bar', 
				'progressBar': 'rabbit' 
			};
			return iconMap[type] || 'traffic-cone'; // si non trouvé on met un traffic-cone
		}

		function addCollapsible(type, title, content) {
			const iconName = getDataTypeIcon(type);
			const html = `<div class="table-title" onclick="toggleCollapse(this)">
          	<div class="table-title-content">
            		<i data-lucide="${iconName}" class="table-icon"></i>
           	 	${title}
          	</div>
          	<i data-lucide="chevron-down" class="collapse-icon"></i>
        		</div>
			<div class="collapsible-content">${content}</div>`;

			const div = document.createElement("div");
			div.className = "chat-message response-message";
			div.innerHTML = html;
			chat.appendChild(div);

			// Initialiser les icônes Lucide pour ce nouveau contenu
			lucide.createIcons({
				nameAttr: 'data-lucide'
			});

			chat.scrollTop = chat.scrollHeight;
		}

		function addTable(tableData) {
			// Ajouter le tableau dans un conteneur collapsible
			const content = `<table><thead><tr>
        ${tableData.header.map(h => `<th>${h}</th>`).join('')}
      </tr></thead><tbody>
        ${tableData.rows.map(row =>
						`<tr>${row.map(cell => `<td>${cell}</td>`).join('')}</tr>`
						).join('')}
      </tbody></table>`;
			
			addCollapsible("table", tableData.title, content);
		}

		function addJson(jsonData) {
			const content = `<div class="json-container">${syntaxHighlight(jsonData.json)}</div>`;

			addCollapsible("json", jsonData.title, content);
		}

		function updateLive(liveData){
			const liveComponent = liveMap.get(liveData.id);
			if (liveData.action==="update") {
				liveComponent.update (liveData.value);
			}else if(liveData.action==="complete"){
				liveComponent.complete();
				liveMap.delete(liveData.id);
			}
		}
		
		function addProgressBar(progressData) {
		    // Générer un ID unique pour la barre de progression
		    //const progressId = `progressbar-${Math.random().toString(36).substr(2, 9)}`;
		    const progressId = progressData.id;
			
		    // Calculer le pourcentage initial
		    const percentage = Math.min((progressData.value / progressData.total) * 100, 100);
		    
		    // Créer le conteneur HTML pour la barre de progression
		    const content = 
		        `<div class="progress-container" id="${progressId}">
		            <div class="progress-bar" style="width: ${percentage}%"></div>
		            <div class="progress-text">${Math.round(percentage)}%</div>
		        </div>
		    `;
		    
		    // Ajouter dans un conteneur collapsible
		    addCollapsible("progressBar", progressData.title || "Progression", content);
		    
			// Créer l'objet de gestion de la barre
		    const progressBar = {
		        id: progressId,
		        update(value) {
		            // Mettre à jour la valeur
		            const container = document.getElementById(progressId);
		            if (!container) {
		                console.error("Progress container not found for ID:", progressId);
		                return;
		            }
		            const newPercentage = Math.min((value / progressData.total) * 100, 100);
		            const bar = container.querySelector('.progress-bar');
		            const text = container.querySelector('.progress-text');
		            bar.style.width = `${newPercentage}%`;
		            text.textContent = `${Math.round(newPercentage)}%`;
		            
		            // Ajouter une classe pour indiquer la complétion si 100%
		            if (newPercentage >= 100) {
		                bar.classList.add('completed');
		            } else {
		                bar.classList.remove('completed');
		            }
		        },
		        complete() {
		            // Marquer comme complet (100%)
		            this.update(progressData.total);
		        }
		    };
			// Ajouter la barre à la Map
			liveMap.set(progressId, progressBar);
		}
		
		function addList(listData) {
			// Fonction récursive pour construire le HTML de la liste
			function buildList(items, type, level = 0) {
				let html = '';
				let tag = 'ol'; // Par défaut pour ORDERED
				let className = '';

				if (type === 'UNORDERED') {
					tag = 'ul';
				} else if (type === 'DASHED') {
					tag = 'ul';
					className = 'dashed-list';
				}

				html += `<${tag} class="${className}">`;

				items.forEach(item => {
					if (typeof item === 'object' && item !== null && 'items' in item) {
						// Élément avec sous-liste imbriquée
						const subType = item.type || type; // Hérite du type parent si non spécifié
						const itemTitle = item.title || ''; // Gère title: null comme chaîne vide
						html += `<li><span class="nested-title">${itemTitle}</span>`;
						html += buildList(item.items, subType, level + 1);
						html += '</li>';
					} else {
						// Élément simple
						html += `<li>${item}</li>`;
					}
				});

				html += `</${tag}>`;
				return html;
			}

			// Génère le contenu HTML de la liste
			const listHtml = buildList(listData.items, listData.type);

			// Ajoute dans un conteneur collapsible (similaire à addTable)
			addCollapsible("list", listData.title, listHtml);
		}

		function addBarChart(chartData) {
		    // Générer un ID unique pour le canvas
		    const canvasId = `barchart-${Math.random().toString(36).substr(2, 9)}`;
		    
		    // Créer le conteneur HTML pour le canvas
		    const content = `<canvas id="${canvasId}" class="chart-canvas"></canvas>`;
		    
		    // Ajouter dans un conteneur collapsible (similaire à addList et addTable)
		    addCollapsible("barChart", chartData.title, content);
		    
		    // Obtenir le contexte du canvas après ajout au DOM
		    const canvas = document.getElementById(canvasId);
		    if (!canvas) {
		        console.error("Canvas not found for ID:", canvasId);
		        return;
		    }
		    
		    // Créer la configuration Chart.js pour un graphique à barres horizontales
		    const chartConfig = {
		        type: 'bar',
		        data: {
		            labels: chartData.header,
		            datasets: [{
		                label: chartData.title,
		                data: chartData.values,
		                backgroundColor: 'rgba(29, 161, 242, 0.7)', // Bleu néon (#1DA1F2) avec transparence
		                borderColor: '#1DA1F2',
		                borderWidth: 1,
						barThickness: 20, // Épaisseur fixe des barres en pixels
						categoryPercentage: 0.8 // 80% de l'espace de la catégorie est utilisé
		            }]
		        },
		        options: {
		            indexAxis: 'y', // Rend les barres horizontales
		            responsive: true,
		            maintainAspectRatio: false,
		            plugins: {
		                legend: {
		                    display: true,
		                    labels: {
		                        color: '#E6E8EA', // --general-text
		                        font: {
		                            family: "'Inter', 'Segoe UI', system-ui, sans-serif",
		                            size: 12
		                        }
		                    }
		                }
		            },
		            scales: {
		                x: {
		                    beginAtZero: true,
		                    ticks: {
		                        color: '#E6E8EA', // --general-text
		                        font: {
		                            family: "'Inter', 'Segoe UI', system-ui, sans-serif",
		                            size: 12
		                        }
		                    },
		                    grid: {
		                        color: 'rgba(255, 255, 255, 0.1)',
		                        borderColor: '#3C4047' // --assistant-accent
		                    }
		                },
		                y: {
		                    ticks: {
		                        color: '#E6E8EA', // --general-text
		                        font: {
		                            family: "'Inter', 'Segoe UI', system-ui, sans-serif",
		                            size: 12
		                        }
		                    },
		                    grid: {
		                        color: 'rgba(255, 255, 255, 0.1)',
		                        borderColor: '#3C4047' // --assistant-accent
		                    }
		                }
		            }
		        }
		    };
		    
		    // Créer le graphique
		    new Chart(canvas.getContext('2d'), chartConfig);
		}
		function syntaxHighlight(json) {
			if (typeof json != 'string') json = JSON.stringify(json, null, 2);
			json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
			return json.replace(/("(\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(?:\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d+)?(?:[eE][+\-]?\d+)?)/g, function (match) {
				if (/^"/.test(match)) {
					return /:$/.test(match) ? `<span class="json-key">${match}</span>` : `<span class="json-string">${match}</span>`;
				} else if (/true|false/.test(match)) {
					return `<span class="json-boolean">${match}</span>`;
				} else if (/null/.test(match)) {
					return `<span class="json-null">${match}</span>`;
				} else {
					return `<span class="json-number">${match}</span>`;
				}
			});
		}

		// Initialiser les icônes Lucide au chargement
		document.addEventListener('DOMContentLoaded', function () {
			lucide.createIcons();

			const promptForm = document.getElementById('prompt-form');
		    promptForm.addEventListener('submit', (event) => {
		        event.preventDefault(); // Prevent page reload
		        sendMessage();
    		});
		});
