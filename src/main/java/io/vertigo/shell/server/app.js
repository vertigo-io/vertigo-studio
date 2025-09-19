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
	document.getElementById("status-text"));

// Set up message handler
wsManager.onMessageHandler = (event) => {
	try {
		const parsed = JSON.parse(event.data);
		let component = null;
		switch (parsed.type) {
			case "table":
				component = new TableComponent(parsed.data);
				break;
			case "json":
				component = new JsonComponent(parsed.data);
				break;
			case "progressBar":
				component  = new ProgressBarComponent(parsed.data);
				liveMap.set(component.id, component);
				break;
			case "live":
				updateLive(parsed.data);
				break;
			case "list":
				component  = new ListComponent(parsed.data);
				break;
			case "barChart":
				component  = new BarChartComponent(parsed.data);
				break;
			case "gauge":
				component  =  new GaugeComponent(parsed.data);
				break; 
			case "sparkLine":
				component  = new SparkLineComponent(parsed.data);
				break;
			default:
				addMessage(JSON.stringify(parsed), "response");
				break;
		}
		//=== The system core
		if (component){
			addCollapsible(parsed.type, component.title, component.toHtml());
			setTimeout(() => component.activate(), 0);
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
		'table': 'table-2',
		'json': 'code-2',
		'list': 'list',
		'tree': 'git-branch',
		'grid': 'grid-3x3',
		'database': 'database',
		'barChart': 'chart-bar',
		'progressBar': 'rabbit',
		'gauge': 'gauge'
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

function updateLive(liveData) {
	const liveComponent = liveMap.get(liveData.id);
	if (liveComponent) {
		if (liveData.action === "update") {
			liveComponent.update(liveData.value);
		} else if (liveData.action === "complete") {
			liveComponent.complete();
			liveMap.delete(liveData.id);
		}
	}
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