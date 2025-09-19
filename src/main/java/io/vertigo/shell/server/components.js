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

        return `<table><thead><tr>${headerHtml}</tr></thead><tbody>${rowsHtml}</tbody></table>`;
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
        return json.replace(/("(\u[a-zA-Z0-9]{4}|\\.[^u]|[^\\"])*"(?:\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
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
            options: { indexAxis: 'y', responsive: true, maintainAspectRatio: false, plugins: { legend: { display: true, labels: { color: '#E6E8EA', font: { family: "'Inter', 'Segoe UI', system-ui, sans-serif", size: 12 } } } }, scales: { x: { beginAtZero: true, ticks: { color: '#E6E8EA', font: { family: "'Inter', 'Segoe UI', system-ui, sans-serif", size: 12 } }, grid: { color: 'rgba(255, 255, 255, 0.1)', borderColor: '#3C4047' } }, y: { ticks: { color: '#E6E8EA', font: { family: "'Inter', 'Segoe UI', system-ui, sans-serif", size: 12 } }, grid: { color: 'rgba(255, 255, 255, 0.1)', borderColor: '#3C4047' } } } }
        };
        new Chart(canvas.getContext('2d'), chartConfig);
    }
}

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
        super({ id });
        this.title = title || 'Progression';
        this.value = value || 0;
        this.total = total || 100;
        if (!this.id) {
            throw new Error("ProgressBar data must have an id.");
        }
    }

    toHtml() {
        const percentage = Math.min((this.value / this.total) * 100, 100);
        return `<div class="progress-container" id="${this.id}"><div class="progress-bar" style="width: ${percentage}%"></div><div class="progress-text">${Math.round(percentage)}%</div></div>`;
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
