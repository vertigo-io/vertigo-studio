Vue.component('v-shiny-list-component', {
    props: ['data'],
    template: `
    <div class="chakra-list-container">
      <h3 class="chakra-component-title">{{ data.title || 'Chakra List' }}</h3>
      <div v-html="itemsHtml"></div>
    </div>
    `,
    computed: {
        itemsHtml() {
            const listType = this.data.type || 'UNORDERED';
            const items = this.data.items || [];

            let tag;
            let listStyle = 'list-style-type: none; padding: 0;';
            switch (listType) {
                case 'ORDERED':
                    tag = 'ol';
                    listStyle = 'list-style-type: decimal; padding-left: 1.5em;';
                    break;
                case 'UNORDERED':
                    tag = 'ul';
                    listStyle = 'list-style-type: disc; padding-left: 1.5em;';
                    break;
                case 'DASHED':
                    tag = 'ul';
                    listStyle = 'list-style-type: none; padding: 0;'; // Custom dashed style
                    break;
                default:
                    tag = 'ul';
            }

            const itemsHtml = items.map(item => {
                let itemContent;
                if (typeof item === 'object' && item !== null && item.type && item.items) {
                    // This is a naive implementation for nested lists.
                    // A recursive component would be better, but for now this will do.
                    itemContent = `<span style="font-weight: bold; color: #90CDF4;">${item.title}</span>` + this.generateItemsHtml(item.items, item.type);
                } else {
                    itemContent = item;
                }

                const liStyle = listType === 'DASHED'
                    ? 'margin-bottom: 0.5em; color: #CBD5E0; position: relative; padding-left: 1em;'
                    : 'margin-bottom: 0.5em; color: #CBD5E0;';
                const beforeStyle = listType === 'DASHED'
                    ? 'content: "- "; position: absolute; left: 0;'
                    : '';

                return `<li style="${liStyle}"><span style="${beforeStyle}"></span>${itemContent}</li>`;
            }).join('');

            return `<${tag} style="${listStyle}">${itemsHtml}</${tag}>`;
        }
    },
    methods: {
        generateItemsHtml(items, listType) {
            let tag;
            let listStyle = 'list-style-type: none; padding: 0;';
            switch (listType) {
                case 'ORDERED':
                    tag = 'ol';
                    listStyle = 'list-style-type: decimal; padding-left: 1.5em;';
                    break;
                case 'UNORDERED':
                    tag = 'ul';
                    listStyle = 'list-style-type: disc; padding-left: 1.5em;';
                    break;
                case 'DASHED':
                    tag = 'ul';
                    listStyle = 'list-style-type: none; padding: 0;'; // Custom dashed style
                    break;
                default:
                    tag = 'ul';
            }

            const itemsHtml = items.map(item => {
                const liStyle = listType === 'DASHED'
                    ? 'margin-bottom: 0.5em; color: #CBD5E0; position: relative; padding-left: 1em;'
                    : 'margin-bottom: 0.5em; color: #CBD5E0;';
                const beforeStyle = listType === 'DASHED'
                    ? 'content: "- "; position: absolute; left: 0;'
                    : '';
                return `<li style="${liStyle}"><span style="${beforeStyle}"></span>${item}</li>`;
            }).join('');
            return `<${tag} style="${listStyle}">${itemsHtml}</${tag}>`;
        }
    }
});
