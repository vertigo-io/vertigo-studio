/* All components must inherit Component */
class TableComponent extends Component {
	constructor({ title, header, rows }) {
		super();
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