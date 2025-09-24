class ChakraTreeComponent extends Component {
    constructor({ rootNode }) {
        super();
        this.rootNode = rootNode;
        this.divId = `chakra-tree-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.divId}" class="chakra-tree" style="color: #CBD5E0; background-color: #1A202C; padding: 10px; border-radius: 8px;">
                    ${this._renderNode(this.rootNode)}
                </div>`;
    }

    _renderNode(node) {
        if (!node) return '';
        let html = `<div style="margin-left: 15px;">
                        <span style="font-weight: bold; color: #90CDF4;">${node.label}</span>`;
        if (node.children && node.children.length > 0) {
            html += `<div style="margin-left: 10px;">
`;
            node.children.forEach(child => {
                html += this._renderNode(child);
            });
            html += `</div>`;
        }
        html += `</div>`;
        return html;
    }
}

class ChakraJsonComponent extends Component {
    constructor({ title, json }) {
        super();
        this.title = title || 'Chakra JSON';
        this.json = json || '{}';
        this.divId = `chakra-json-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        // Chakra-inspired styling for dark theme
        return `<div id="${this.divId}" class="chakra-json-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px; font-family: monospace; font-size: 0.9em;">
                    <h3 class="chakra-json-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <pre class="chakra-json-content" style="white-space: pre-wrap; word-wrap: break-word; color: #A0AEC0;"></pre>
                </div>`;
    }

    activate() {
        const container = document.getElementById(this.divId);
        const preElement = container.querySelector('.chakra-json-content');
        if (!preElement) return;

        try {
            const obj = JSON.parse(this.json);
            const formattedJson = JSON.stringify(obj, null, 2);

            // Apply syntax highlighting inspired by Chakra colors
            const highlightedJson = formattedJson
                .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;') // HTML escape
                .replace(/"(\w+)":/g, '<span style="color: #90CDF4;">"$1"</span>:') // Keys (labelColor)
                .replace(/"([^"]*)"/g, '<span style="color: #F6AD55;">"$1"</span>') // Strings (stringColor)
                .replace(/\b(\d+(\.\d+)?)\b/g, '<span style="color: #68D391;">$1</span>') // Numbers (numberColor)
                .replace(/\b(true|false)\b/g, '<span style="color: #805AD5;">$1</span>') // Booleans (booleanColor)
                .replace(/\b(null)\b/g, '<span style="color: #E53E3E;">$1</span>'); // Null (nullColor)

            preElement.innerHTML = highlightedJson;

        } catch (e) {
            preElement.textContent = `Error parsing JSON: ${e.message}\n${this.json}`;
            preElement.style.color = 'red';
        }
    }
}

class ChakraTableComponent extends Component {
    constructor({ id, title, noDataFound, header, rows, sortable }) {
        super();
        this.id = id; // Add id
        this.title = title || 'Chakra Table';
        this.noDataFound = noDataFound || 'No data found.';
        this.header = header || [];
        this.rows = rows || [];
        this.sortable = sortable || false; // Add sortable property
        this.divId = `chakra-table-${Math.random().toString(36).substr(2, 9)}`;
        this.sortColumn = -1;
        this.sortDirection = 'asc'; // 'asc' or 'desc'
    }

    toHtml() {
        let tableContent = '';
        if (this.rows.length === 0) {
            tableContent = `<tr><td colspan="${this.header.length}" style="text-align: center; color: #A0AEC0; padding: 8px;">${this.noDataFound}</td></tr>`;
        } else {
            const headerHtml = this.header.map((h, index) => {
                let sortIndicator = '';
                if (this.sortable && this.sortColumn === index) {
                    sortIndicator = this.sortDirection === 'asc' ? ' ▲' : ' ▼';
                }
                return `<th style="padding: 8px 12px; border-bottom: 2px solid #4A5568; text-align: left; color: #CBD5E0; background-color: #2D3748; ${this.sortable ? 'cursor: pointer;' : ''}" data-column-index="${index}" data-sort-direction="${this.sortDirection}">${h}${sortIndicator}</th>`;
            }).join('');
            const rowsHtml = this.rows.map((row, rowIndex) => {
                const rowColor = rowIndex % 2 === 0 ? '#2D3748' : '#1A202C'; // Alternating row colors
                const cellsHtml = row.map(cell => `<td style="padding: 8px 12px; border-bottom: 1px solid #4A5568; color: #E2E8F0;">${cell}</td>`).join('');
                return `<tr style="background-color: ${rowColor};">${cellsHtml}</tr>`;
            }).join('');
            tableContent = `<thead><tr>${headerHtml}</tr></thead><tbody>${rowsHtml}</tbody>`;
        }

        return `<div id="${this.divId}" class="chakra-table-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px; overflow-x: auto;">
                    <h3 class="chakra-table-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <table class="chakra-table" style="width: 100%; border-collapse: collapse;">
                        ${tableContent}
                    </table>
                </div>`;
    }

    activate() {
        if (this.sortable) {
            const tableElement = document.getElementById(this.divId);
            if (tableElement) {
                const headers = tableElement.querySelectorAll('th');
                headers.forEach(header => {
                    header.addEventListener('click', () => {
                        const columnIndex = parseInt(header.dataset.columnIndex);
                        const currentSortDirection = header.dataset.sortDirection;
                        const newSortDirection = currentSortDirection === 'asc' ? 'desc' : 'asc';

                        // Send message to server to sort
                        const message = {
                            type: 'sortChakraTable',
                            data: {
                                id: this.id,
                                columnIndex: columnIndex,
                                sortDirection: newSortDirection
                            }
                        };
                        // Assuming wsManager is available in the global scope or passed
                        try {
                            wsManager.sendMessage(JSON.stringify(message));
                       } catch (error) {
                            console.error('Error while sending sort message.'+ error);
                        }
                    });
                });
            }
        }
    }

    update(newData) {
        // Update the component's data and re-render
        this.header = newData.header || this.header;
        this.rows = newData.rows || this.rows;
        this.sortColumn = newData.sortColumn !== undefined ? newData.sortColumn : this.sortColumn;
        this.sortDirection = newData.sortDirection || this.sortDirection;

        const tableContainer = document.getElementById(this.divId);
        if (tableContainer) {
            tableContainer.innerHTML = this.toHtml();
            this.activate(); // Re-attach event listeners
        }
    }
}