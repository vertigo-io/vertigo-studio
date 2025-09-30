Vue.component('v-text-path-component', {
    props: ['data'],
    template: '<div class="text-path" v-html="pathHtml"></div>',
    computed: {
        pathHtml() {
            const rootColor = 'green';
            const nodeColor = 'yellow';
            const leafColor = '#87CEEB'; // skyblue
            const separatorColor = 'red';
            const parts = this.data.path.split(this.data.separator || '/');
            let html = '';
            parts.forEach((part, index) => {
                let color = nodeColor;
                if (index === 0) {
                    color = rootColor;
                } else if (index === parts.length - 1) {
                    color = leafColor;
                }
                html += `<span style="color: ${color}">${part}</span>`;
                if (index < parts.length - 1) {
                    html += `<span style="color: ${separatorColor}">${this.data.separator || '/'}</span>`;
                }
            });
            return html;
        }
    }
});