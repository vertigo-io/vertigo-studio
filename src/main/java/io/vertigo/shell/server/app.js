// ========================================================================
// Application Logic
// ========================================================================

const CONFIG = {
	WEBSOCKET_URL: 'ws://localhost:8080',
}

const chat = document.getElementById("chat");
const liveMap = new Map();

// Initialize WebSocket Manager
const wsManager = new WebSocketManager(
	CONFIG.WEBSOCKET_URL,
	document.getElementById("status"),
	document.getElementById("status-text")
);

// Set up message handler
wsManager.onMessageHandler = (event) => {
	try {
		const parsed = JSON.parse(event.data);
		const componentMap = {
			barChart: BarChartComponent,
			gauge: GaugeComponent,
			geoMap: GeoMapComponent,
		    json: JsonComponent,
			list: ListComponent,
		    progressBar: ProgressBarComponent,
			sankey:SankeyComponent,
		    sparkLine: SparkLineComponent,
			table: TableComponent,
			tree:TreeComponent,
			youtube: YouTubeComponent,
			spotify: SpotifyComponent,
			photo: PhotoComponent};
		
		if (componentMap[parsed.type]) {
			const component = new componentMap[parsed.type](parsed.data);
			addCollapsible(parsed.type, component.title, component.toHtml());
			setTimeout(() => component.activate(), 0);
			if(component instanceof LiveComponent){
				liveMap.set(component.id, component);
			}
		} else if (parsed.type === 'live') {
			//Update a component that inherits LiveComponent
			const liveData = parsed.data;
			const liveComponent = liveMap.get(liveData.id);
			if (liveComponent) {
				if (liveData.action === "update") {
					liveComponent.update(liveData.value);
				} else if (liveData.action === "complete") {
					liveComponent.complete();
					liveMap.delete(liveData.id);
				}
			}
		} else {
		    addMessage(JSON.stringify(parsed), 'response');
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
		try {
			wsManager.sendMessage(message);
			input.value = "";
		} catch (error) {
			alert(`Erreur d'envoi : ${error.message}`);
		}
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
		'barChart': 'chart-bar',
		'gauge': 'gauge', 
		'geoMap': 'map-pin',
		'json': 'code-2',
		'list': 'list',
		'progressBar': 'rabbit',
		'sankey':'waves',
		'sparkLine': 'trending-up',
		'table': 'table-2',
		'tree': 'folder-tree'
		};
	return iconMap[type] || 'traffic-cone';
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

	lucide.createIcons({
		nameAttr: 'data-lucide'
	});

	chat.scrollTop = chat.scrollHeight;
}

document.addEventListener('DOMContentLoaded', function() {
	lucide.createIcons();
	const promptForm = document.getElementById('prompt-form');
	promptForm.addEventListener('submit', (event) => {
		event.preventDefault();
		sendMessage();
	});

	// Connect WebSocket when DOM is loaded
	wsManager.connect();
});
