Vue.component('v-chakra-bar-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-chart-container">
        <h3 class="chakra-component-title">{{ data.title || 'Chakra Bar Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-barchart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const colors = [
            'rgba(144, 205, 244, 0.8)', // blue
            'rgba(160, 217, 144, 0.8)', // green
            'rgba(244, 144, 144, 0.8)', // red
            'rgba(244, 224, 144, 0.8)', // yellow
            'rgba(192, 144, 244, 0.8)', // purple
        ];

        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: this.data.labels || [],
                datasets: this.data.series.map((serie, index) => ({
                    label: serie.name,
                    data: serie.data || [],
                    backgroundColor: colors[index % colors.length],
                    borderColor: colors[index % colors.length].replace('0.8', '1'),
                    borderWidth: 1
                }))
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            color: '#A0AEC0'
                        },
                        grid: {
                            color: '#2D3748'
                        }
                    },
                    x: {
                        ticks: {
                            color: '#A0AEC0'
                        },
                        grid: {
                            color: '#2D3748'
                        }
                    }
                },
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