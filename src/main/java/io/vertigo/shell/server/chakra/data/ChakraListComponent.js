class ChakraListComponent extends Component {
  static TYPES = {
    ORDERED: 'ORDERED',
    UNORDERED: 'UNORDERED',
    DASHED: 'DASHED'
  };

  constructor({ title, type, items }) {
    super();
    this.title = title || 'Chakra List';
    this.type = type || ChakraListComponent.TYPES.UNORDERED;
    this.items = (items || []).map(item => {
      if (typeof item === 'object' && item !== null && item.type && item.items) {
        // Assuming nested item is also a ChakraListComponent data structure
        return new ChakraListComponent(item);
      }
      return item;
    });
  }

  toHtml() {
    return `<div style="background-color: #1A202C; padding: 15px; border-radius: 8px; margin-top: 1em;">
      <h3 style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
      ${this.itemsToHtml()}
    </div>`;
  }

  itemsToHtml() {
    let tag;
    let listStyle = 'list-style-type: none; padding: 0;';
    switch (this.type) {
      case ChakraListComponent.TYPES.ORDERED:
        tag = 'ol';
        listStyle = 'list-style-type: decimal; padding-left: 1.5em;';
        break;
      case ChakraListComponent.TYPES.UNORDERED:
        tag = 'ul';
        listStyle = 'list-style-type: disc; padding-left: 1.5em;';
        break;
      case ChakraListComponent.TYPES.DASHED:
        tag = 'ul';
        listStyle = 'list-style-type: none; padding: 0;'; // Custom dashed style
        break;
      default:
        tag = 'ul';
    }

    const itemsHtml = this.items.map(item => {
      let itemContent;
      if (item instanceof ChakraListComponent) {
        itemContent = `<span style="font-weight: bold; color: #90CDF4;">${item.title}</span>${item.itemsToHtml()}`;
      } else {
        itemContent = item;
      }

      const liStyle = this.type === ChakraListComponent.TYPES.DASHED
        ? 'margin-bottom: 0.5em; color: #CBD5E0; position: relative; padding-left: 1em;'
        : 'margin-bottom: 0.5em; color: #CBD5E0;';
      const beforeStyle = this.type === ChakraListComponent.TYPES.DASHED
        ? 'content: "- "; position: absolute; left: 0;'
        : '';

      return `<li style="${liStyle}"><span style="${beforeStyle}"></span>${itemContent}</li>`;
    }).join('');

    return `<${tag} style="${listStyle}">
      ${itemsHtml}
    </${tag}>`;
  }
}