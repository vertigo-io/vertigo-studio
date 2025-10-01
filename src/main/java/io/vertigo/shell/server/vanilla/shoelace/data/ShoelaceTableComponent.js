class ShoelaceTableComponent extends Component {
    constructor({ id, title, noDataFound, header, rows, sortable }) {
        super();
        this.id = id;
        this.title = title || 'Shoelace Table';
        this.noDataFound = noDataFound || 'No data found.';
        this.header = header || [];
        this.rows = rows || [];
        this.sortable = sortable || false;
        this.tableId = `shoelace-table-${Math.random().toString(36).substr(2, 9)}`;
        this.sortColumn = -1;
        this.sortDirection = 'asc'; // 'asc' or 'desc'
    }

    toHtml() {
        let tableContent = '';
        if (this.rows.length === 0) {
            tableContent = `<tr><td colspan="${this.header.length}">${this.noDataFound}</td></tr>`;
        } else {
            const headerHtml = this.header.map((h, index) => {
                let sortIndicator = '';
                if (this.sortable && this.sortColumn === index) {
                    sortIndicator = this.sortDirection === 'asc' ? ' ▲' : ' ▼';
                }
                return `<th data-column-index="${index}" data-sort-direction="${this.sortDirection}" style="${this.sortable ? 'cursor: pointer;' : ''}">${h}${sortIndicator}</th>`;
            }).join('');
            const rowsHtml = this.rows.map(row =>
                `<tr>${row.map(cell => `<td>${cell}</td>`).join('')}</tr>`
            ).join('');
            tableContent = `<thead><tr>${headerHtml}</tr></thead><tbody>${rowsHtml}</tbody>`;
        }

        return `<sl-card style="margin-bottom: 1em;">
                    <div slot="header">${this.title}</div>
                    <sl-table id="${this.tableId}">
                        ${tableContent}
                    </sl-table>
                </sl-card>`;
    }

    activate() {
        if (this.sortable) {
            const tableElement = document.getElementById(this.tableId);
            if (tableElement) {
                const headers = tableElement.querySelectorAll('th');
                headers.forEach(header => {
                    header.addEventListener('click', () => {
                        const columnIndex = parseInt(header.dataset.columnIndex);
                        const currentSortDirection = header.dataset.sortDirection;
                        const newSortDirection = currentSortDirection === 'asc' ? 'desc' : 'asc';

                        const message = {
                            type: 'sortShoelaceTable', // New message type for Shoelace tables
                            data: {
                                id: this.id,
                                columnIndex: columnIndex,
                                sortDirection: newSortDirection
                            }
                        };
                        if (window.wsManager) {
                            window.wsManager.sendMessage(JSON.stringify(message));
                        } else {
                            console.error('wsManager not found for sending sort message.');
                        }
                    });
                });
            }
        }
    }

    update(newData) {
        this.header = newData.header || this.header;
        this.rows = newData.rows || this.rows;
        this.sortColumn = newData.sortColumn !== undefined ? newData.sortColumn : this.sortColumn;
        this.sortDirection = newData.sortDirection || this.sortDirection;

        const tableContainer = document.getElementById(this.tableId);
        if (tableContainer) {
            tableContainer.innerHTML = this.toHtml();
            this.activate();
        }
    }
}