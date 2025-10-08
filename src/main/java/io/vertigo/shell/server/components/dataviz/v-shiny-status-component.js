Vue.component('v-shiny-status-component', {
    props: ['data'],
    template: `
    <div class="status-container">
        <h3 class="status-title">{{ data.title || 'Status' }}</h3>
        <div class="status-indicators">
            <div v-for="(type, index) in data.types" :key="index" class="status-indicator" :style="{ backgroundColor: colorMap[type] || 'gray' }"></div>
        </div>
    </div>
    `,
    data() {
        return {
            colorMap: {
                SUCCESS: 'green',
                ERROR: 'red',
                WARNING: 'orange',
                INFO: 'blue',
                NEUTRAL: 'gray'
            }
        };
    }
});