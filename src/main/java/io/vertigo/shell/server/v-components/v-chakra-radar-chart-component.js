Vue.component('v-chakra-radar-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-radarchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-radarchart-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Radar Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-radarchart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const component = new ChakraRadarChartComponent(this.data);
        component.canvasId = this.canvasId; // Ensure the canvasId is consistent
        component.activate();
    }
});