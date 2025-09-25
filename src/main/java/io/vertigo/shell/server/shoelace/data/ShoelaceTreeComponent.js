class ShoelaceTreeComponent extends Component {
    constructor({ title, rootNode }) {
        super();
        this.title = title || 'Shoelace Tree';
        this.rootNode = rootNode;
        this.treeId = `shoelace-tree-${Math.random().toString(36).substr(2, 9)}`;
    }

    _renderNode(node) {
        if (!node) return '';
        const childrenHtml = node.nodes && node.nodes.length > 0
            ? node.nodes.map(child => this._renderNode(child)).join('')
            : '';
        
        return `<sl-tree-item ${node.nodes && node.nodes.length > 0 ? 'expanded' : ''}>
                    ${node.label}
                    ${childrenHtml}
                </sl-tree-item>`;
    }

    toHtml() {
        return `<sl-card style="margin-bottom: 1em;">
                    <div slot="header">${this.title}</div>
                    <sl-tree id="${this.treeId}">
                        ${this._renderNode(this.rootNode)}
                    </sl-tree>
                </sl-card>`;
    }
}