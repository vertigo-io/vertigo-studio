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