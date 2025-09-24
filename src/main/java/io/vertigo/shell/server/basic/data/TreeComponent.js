class TreeComponent extends Component {
    constructor({ title, rootNode }) {
        super();
        this.title = title || 'Tree';
        this.rootNode = rootNode || { 
			label: 'Root', 
			nodes: [], 
			icon: 'NONE' };
    }

    toHtml() {
        // Recursively generate HTML for the tree
        const generateNodeHtml = (node) => {
            const iconHtml = node.icon && node.icon !== 'NONE' ? `<i data-lucide="${node.icon}" class="tree-icon"></i>` : '';
            const childrenHtml = node.nodes && node.nodes.length > 0
                ? `<ul class="tree-nodes">${node.nodes.map(child => `<li>${generateNodeHtml(child)}</li>`).join('')}</ul>`
                : '';
            return `
                <div class="tree-node">
                    ${iconHtml}
                    <span class="node-label">${node.label}</span>
                    ${childrenHtml}
                </div>
            `;
        };

        return `
            <div class="tree-container">
                <ul class="tree-nodes">
                    <li>${generateNodeHtml(this.rootNode)}</li>
                </ul>
            </div>
        `;
    }

    activate() {
        // Initialize Lucide icons for the tree
        lucide.createIcons({ nameAttr: 'data-lucide' });
    }
}