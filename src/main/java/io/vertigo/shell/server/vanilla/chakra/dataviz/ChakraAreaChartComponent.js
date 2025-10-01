class ChakraAreaChartComponent extends Component {
    constructor({ title, labels, data }) {
        super();
        this.title = title || 'Chakra Area Chart';
        this.labels = labels || [];
        this.data = data || [];
        this.canvasId = `chakra-areachart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-areachart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-areachart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: this.labels,
                datasets: [{
                    data: this.data,
                    borderColor: '#3182CE',
                    backgroundColor: 'rgba(49, 130, 206, 0.2)',
                    fill: true,
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
}