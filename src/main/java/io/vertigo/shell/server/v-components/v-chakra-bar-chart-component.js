Vue.component('v-chakra-bar-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-barchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-barchart-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Bar Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
    data() {
        return {
            canvasId: `chakra-barchart-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: this.data.header || [],
                datasets: [{
                    label: this.data.title,
                    data: this.data.values || [],
                    backgroundColor: 'rgba(144, 205, 244, 0.8)',
                    borderColor: 'rgba(144, 205, 244, 1)',
                    borderWidth: 1
                }]
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