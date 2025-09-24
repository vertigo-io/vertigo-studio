class ListComponent extends Component {
	static TYPES = {
		ORDERED: 'ORDERED',
		UNORDERED: 'UNORDERED',
		DASHED: 'DASHED'
	};

	constructor({ title, type, items }) {
		super();
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