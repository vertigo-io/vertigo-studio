// ========================================================================
// Application Logic
// ========================================================================

const CONFIG = {
    WEBSOCKET_URL: 'ws://localhost:8080',
}

class WebSocketManager {
    constructor(url, statusElement, statusTextElement) {
        this.url = url;
        this.statusElement = statusElement;
        this.statusTextElement = statusTextElement;
        this.socket = null;
        this.onMessageHandler = () => {};

        document.addEventListener('visibilitychange', () => {
            if (document.visibilityState === 'visible') {
                this.tryReconnect();
            }
        });
    }

    connect() {
        console.log("Attempting to connect to WebSocket...");
        this.socket = new WebSocket(this.url);
        this.socket.onopen = () => this.onOpen();
        this.socket.onclose = () => this.onClose();
        this.socket.onerror = (error) => this.onError(error);
        this.socket.onmessage = (event) => this.onMessageHandler(event);
    }

    onOpen() {
        this.statusElement.className = 'status-connected';
        this.statusTextElement.textContent = 'Connected';
    }

    onClose() {
        console.log("WebSocket connection closed.");
        this.statusElement.className = 'status-disconnected';
        this.statusTextElement.textContent = 'Disconnected';
    }

    onError(error) {
        console.error("WebSocket error:", error);
        this.statusElement.className = 'status-error';
        this.statusTextElement.textContent = 'Error';
    }

    sendMessage(message) {
        if (this.socket && this.socket.readyState === WebSocket.OPEN) {
            this.socket.send(message);
        } else {
            throw new Error("Cannot send message, WebSocket is not open.");
        }
    }
}

// ========================================================================
// Application Logic
// ========================================================================

const chat = document.getElementById("chat");
const liveMap = new Map();

// Initialize WebSocket Manager
const statusDiv = document.getElementById("status");
const statusText = document.getElementById("status-text");
const wsManager = new WebSocketManager(CONFIG.WEBSOCKET_URL, statusDiv, statusText);

// Set up message handler
wsManager.onMessageHandler = (event) => {
    try {
        const parsed = JSON.parse(event.data);
        switch (parsed.type) {
            case "table":
                addTable(parsed.data);
                break;
            case "json":
                addJson(parsed.data);
                break;
            case "progressBar":
                addProgressBar(parsed.data);
                break;
            case "live":
                updateLive(parsed.data);
                break;
            case "list":
                addList(parsed.data);
                break;
            case "barChart":
                addBarChart(parsed.data);
                break;
            default:
                addMessage(JSON.stringify(parsed), "response");
                break;
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
        'progressBar': 'rabbit'
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

function addTable(tableData) {
    const table = new Table(tableData);
    const tableHtml = table.toHtml();
    addCollapsible("table", table.title, tableHtml);
}

function addJson(jsonData) {
    const jsonComponent = new JsonComponent(jsonData);
    const jsonHtml = jsonComponent.toHtml();
    addCollapsible("json", jsonComponent.title, jsonHtml);
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

function addProgressBar(progressData) {
    const progressBar = new ProgressBarComponent(progressData);
    const progressBarHtml = progressBar.toHtml();
    addCollapsible("progressBar", progressBar.title, progressBarHtml);
    liveMap.set(progressBar.id, progressBar);
}

function addList(listData) {
    const list = new ListComponent(listData);
    const listHtml = list.toHtml();
    addCollapsible("list", list.title, listHtml);
}

function addBarChart(chartData) {
    const chartComponent = new BarChartComponent(chartData);
    const chartHtml = chartComponent.toHtml();
    addCollapsible("barChart", chartComponent.title, chartHtml);

    // Activate the chart after it has been added to the DOM.
    setTimeout(() => chartComponent.activate(), 0);
}

document.addEventListener('DOMContentLoaded', function () {
    lucide.createIcons();
    const promptForm = document.getElementById('prompt-form');
    promptForm.addEventListener('submit', (event) => {
        event.preventDefault();
        sendMessage();
    });
    
    // Connect WebSocket when DOM is loaded
    wsManager.connect();
});