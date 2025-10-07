Vue.component('v-shiny-donut-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-chart-container">
        <h3 class="chakra-component-title">{{ data.title || 'Chakra Donut Chart' }}</h3>
        <div class="chart-canvas-wrapper">
            <canvas :id="canvasId"></canvas>
        </div>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-donutchart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        const baseColors = ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9', '#F6AD55', '#F6E05E', '#FEB2B2', '#BEE3F8', '#C6F6D5'];

        let datasetsConfig;

        if (this.data.series && this.data.series.length > 0) {
            datasetsConfig = this.data.series.map((serie, serieIndex) => ({
                label: serie.name,
                data: serie.data || [],
                backgroundColor: baseColors.map(color => color.replace('1', (0.8 - serieIndex * 0.1).toFixed(1))),
                borderColor: baseColors.map(color => color.replace('1', (1 - serieIndex * 0.1).toFixed(1))),
                borderWidth: 1,
            }));
        } else {
            datasetsConfig = [{
                data: [],
                backgroundColor: baseColors,
                borderColor: baseColors.map(color => color.replace('0.8', '1')),
                borderWidth: 1
            }];
        }

        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: this.data.labels,
                datasets: datasetsConfig
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