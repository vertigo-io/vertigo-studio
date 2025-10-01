Vue.component('v-chakra-donut-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-donutchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-donutchart-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Donut Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-donutchart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
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