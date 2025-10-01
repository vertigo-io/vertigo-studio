Vue.component('v-chakra-area-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-areachart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-areachart-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Area Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-areachart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const colors = [
            'rgba(49, 130, 206, 0.8)', // blue
            'rgba(72, 187, 120, 0.8)', // green
            'rgba(229, 62, 62, 0.8)', // red
            'rgba(237, 137, 54, 0.8)', // orange
            'rgba(159, 64, 255, 0.8)', // purple
        ];

        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: this.data.labels || [],
                datasets: this.data.series.map((serie, index) => ({
                    label: serie.name,
                    data: serie.data || [],
                    borderColor: colors[index % colors.length].replace('0.8', '1'),
                    backgroundColor: colors[index % colors.length].replace('0.8', '0.2'),
                    fill: true,
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