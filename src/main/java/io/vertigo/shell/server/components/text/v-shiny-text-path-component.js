Vue.component('v-shiny-text-path-component', {
    props: ['data'],
    template: '<div class="text-path" v-html="pathHtml"></div>',
    computed: {
        pathHtml() {
            const parts = this.data.path.split(this.data.separator || '/');
            let html = '';
            parts.forEach((part, index) => {
                let partClass = 'path-node';
                if (index === 0) {
                    partClass = 'path-root';
                } else if (index === parts.length - 1) {
                    partClass = 'path-leaf';
                }
                html += `<span class="${partClass}">${part}</span>`;
                if (index < parts.length - 1) {
                    html += `<span class="path-separator">${this.data.separator || '/'}</span>`;
                }
            });
            return html;
        }
    }
});