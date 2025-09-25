class ShoelaceListComponent extends Component {
    static TYPES = {
        ORDERED: 'ORDERED',
        UNORDERED: 'UNORDERED',
        DASHED: 'DASHED'
    };

    constructor({ title, type, items }) {
        super();
        this.title = title || 'Shoelace List';
        this.type = type || ShoelaceListComponent.TYPES.UNORDERED;
        this.items = (items || []).map(item => {
            if (typeof item === 'object' && item !== null && item.type && item.items) {
                return new ShoelaceListComponent(item);
            }
            return item;
        });
    }

    toHtml() {
        let tag;
        let listStyle = ''; // Shoelace handles styling
        switch (this.type) {
            case ShoelaceListComponent.TYPES.ORDERED:
                tag = 'ol';
                break;
            case ShoelaceListComponent.TYPES.UNORDERED:
            case ShoelaceListComponent.TYPES.DASHED: // Shoelace doesn't have a native dashed list, use unordered
                tag = 'ul';
                break;
            default:
                tag = 'ul';
        }

        const itemsHtml = this.items.map(item => {
            let itemContent;
            if (item instanceof ShoelaceListComponent) {
                itemContent = `<span style="font-weight: bold;">${item.title}</span>${item.toHtml()}`;
            } else {
                itemContent = item;
            }
            return `<sl-list-item>${itemContent}</sl-list-item>`;
        }).join('');

        return `<sl-card style="margin-bottom: 1em;">
                    <div slot="header">${this.title}</div>
                    <sl-list>${itemsHtml}</sl-list>
                </sl-card>`;
    }
}