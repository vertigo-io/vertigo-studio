// ========================================================================
// Component Classes
// ========================================================================

class Table {
    constructor({ title, header, rows }) {
        this.title = title || 'Table';
        this.header = header || [];
        this.rows = rows || [];
    }

    toHtml() {
        const headerHtml = this.header.map(h => `<th>${h}</th>`).join('');
        const rowsHtml = this.rows.map(row =>
            `<tr>${row.map(cell => `<td>${cell}</td>`).join('')}</tr>`
        ).join('');

        return `<table>
                    <thead>
                        <tr>${headerHtml}</tr>
                    </thead>
                    <tbody>
                        ${rowsHtml}
                    </tbody>
                </table>`;
    }
}

class ListComponent {
    static TYPES = {
        ORDERED: 'ORDERED',
        UNORDERED: 'UNORDERED',
        DASHED: 'DASHED'
    };

    constructor({ title, type, items }) {
        this.title = title || '';
        this.type = type || ListComponent.TYPES.UNORDERED;
        this.items = (items || []).map(item => {
            if (typeof item === 'object' && item !== null) {
                return new ListComponent(item);
            }
            return item;
        });
    }

    toHtml() {
        let tag;
        let className = '';
        switch (this.type) {
            case ListComponent.TYPES.ORDERED:
                tag = 'ol';
                break;
            case ListComponent.TYPES.UNORDERED:
                tag = 'ul';
                break;
            case ListComponent.TYPES.DASHED:
                tag = 'ul';
                className = 'dashed-list';
                break;
            default:
                throw new Error(`Unknown list type: ${this.type}`);
        }

        const itemsHtml = this.items.map(item => {
            if (item instanceof ListComponent) {
                return `<li><span class="nested-title">${item.title}</span>${item.toHtml()}</li>`;
            }
            return `<li>${item}</li>`;
        }).join('');

        return `<${tag} class="${className}">${itemsHtml}</${tag}>`;
    }
}

class JsonComponent {
    constructor({ title, json }) {
        this.title = title || 'JSON';
        this.json = json || {};
    }

    _syntaxHighlight(json) {
        if (typeof json != 'string') {
            json = JSON.stringify(json, null, 2);
        }
        // Don't escape here anymore.
        return json.replace(/("(\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(?:\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
            // Escape the HTML characters from the JSON content itself before wrapping in spans
            let escapedMatch = match.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
            if (/^"/.test(match)) {
                return /:$/.test(match) ? `<span class="json-key">${escapedMatch}</span>` : `<span class="json-string">${escapedMatch}</span>`;
            } else if (/true|false/.test(match)) {
                return `<span class="json-boolean">${escapedMatch}</span>`;
            } else if (/null/.test(match)) {
                return `<span class="json-null">${escapedMatch}</span>`;
            } else {
                return `<span class="json-number">${escapedMatch}</span>`;
            }
        });
    }

    toHtml() {
        const highlightedJson = this._syntaxHighlight(this.json);
        return `<div class="json-container">${highlightedJson}</div>`;
    }
}

class BarChartComponent {
    constructor({ title, header, values }) {
        this.title = title || 'Bar Chart';
        this.header = header || [];
        this.values = values || [];
        this.canvasId = `barchart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<canvas id="${this.canvasId}" class="chart-canvas"></canvas>`;
    }

    activate() {
        const canvas = document.getElementById(this.canvasId);
        if (!canvas) {
            throw new Error(`Canvas not found for ID: ${this.canvasId}`);
        }
        const chartConfig = {
            type: 'bar',
            data: {
                labels: this.header,
                datasets: [{
                    label: this.title,
                    data: this.values,
                    backgroundColor: 'rgba(29, 161, 242, 0.7)',
                    borderColor: '#1DA1F2',
                    borderWidth: 1,
                    barThickness: 20,
                    categoryPercentage: 0.8
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        labels: {
                            color: '#E6E8EA',
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
                            color: '#E6E8EA',
                            font: {
                                family: "'Inter', 'Segoe UI', system-ui, sans-serif",
                                size: 12
                            }
                        },
                        grid: {
                            color: 'rgba(255, 255, 255, 0.1)',
                            borderColor: '#3C4047'
                        }
                    },
                    y: {
                        ticks: {
                            color: '#E6E8EA',
                            font: {
                                family: "'Inter', 'Segoe UI', system-ui, sans-serif",
                                size: 12
                            }
                        },
                        grid: {
                            color: 'rgba(255, 255, 255, 0.1)',
                            borderColor: '#3C4047'
                        }
                    }
                }
            }
        };
        new Chart(canvas.getContext('2d'), chartConfig);
    }
}

// Base class for components that can be updated after being rendered.
class LiveComponent {
    constructor({ id }) {
        if (!id) {
            throw new Error("LiveComponent requires an id");
        }
        this.id = id;
    }

    update(data) {
        throw new Error(`LiveComponent.update() must be implemented by subclasses. Component ID: ${this.id}`);
    }

    complete() {
        throw new Error(`LiveComponent.complete() must be implemented by subclasses. Component ID: ${this.id}`);
    }
}

class ProgressBarComponent extends LiveComponent {
    constructor({ id, title, value, total }) {
        super({ id }); // Call the parent constructor
        this.title = title || 'Progression';
        this.value = value || 0;
        this.total = total || 100;
    }

    toHtml() {
        const percentage = Math.min((this.value / this.total) * 100, 100);
        return (
            `<div class="progress-container" id="${this.id}">
                <div class="progress-bar" style="width: ${percentage}%"></div>
                <div class="progress-text">${Math.round(percentage)}%</div>
            </div>
        `
        );
    }

    update(value) {
        this.value = value;
        const container = document.getElementById(this.id);
        if (!container) return;
        const newPercentage = Math.min((this.value / this.total) * 100, 100);
        const bar = container.querySelector('.progress-bar');
        const text = container.querySelector('.progress-text');
        if (bar && text) {
            bar.style.width = `${newPercentage}%`;
            text.textContent = `${Math.round(newPercentage)}%`;
            bar.classList.toggle('completed', newPercentage >= 100);
        }
    }

    complete() {
        this.update(this.total);
    }
}


const socket = new WebSocket("ws://localhost:8080");
const chat = document.getElementById("chat");
const statusDiv = document.getElementById("status");
const statusText = document.getElementById("status-text");

socket.onopen = () => {
    statusDiv.className = 'status-connected';
    statusText.textContent = 'Connected';
};

socket.onclose = () => {
    statusDiv.className = 'status-disconnected';
    statusText.textContent = 'Disconnected';
};

socket.onerror = () => {
    statusDiv.className = 'status-error';
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
    if (!progressData.id) {
        throw new Error("ProgressBar data must have an id.");
    }
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
});
