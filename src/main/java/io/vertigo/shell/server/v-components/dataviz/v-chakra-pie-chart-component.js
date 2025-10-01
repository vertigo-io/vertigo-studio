Vue.component('v-chakra-pie-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-piechart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-piechart-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Pie Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-piechart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: this.data.labels,
                datasets: [{
                    data: this.data.series[0].data,
                    backgroundColor: ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9'],
                }]
            },
            options: {
                plugins: {
                    legend: {
                        labels: {
                            color: '#A0AEC0'
                        }
                    }
                }
            }
        });
    }
});