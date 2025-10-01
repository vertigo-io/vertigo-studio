class ChakraChartComponent extends Component {
    constructor({ title, type, labels, series }) {
        super();
        this.title = title || 'Chakra Chart';
        this.type = type || 'bar'; // bar, pie, doughnut, line, radar
        this.labels = labels || [];
        this.series = series || []; // [{ label: 'Series 1', data: [10, 20, 30] }]
        this.canvasId = `chakra-chart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-chart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-chart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        const datasets = this.series.map((serie, index) => {
            const colors = ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9'];
            const backgroundColor = this.type === 'line' || this.type === 'radar' ? `rgba(49, 130, 206, 0.2)` : colors[index % colors.length];
            const borderColor = colors[index % colors.length];

            return {
                label: serie.label,
                data: serie.data,
                backgroundColor: backgroundColor,
                borderColor: borderColor,
                borderWidth: 1,
                fill: this.type === 'line' || this.type === 'radar',
                pointRadius: this.type === 'line' || this.type === 'radar' ? 0 : 3,
            };
        });

        new Chart(ctx, {
            type: this.type,
            data: {
                labels: this.labels,
                datasets: datasets
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
                    },
                    r: { // For radar charts
                        angleLines: {
                            color: '#4A5568'
                        },
                        grid: {
                            color: '#4A5568'
                        },
                        pointLabels: {
                            color: '#A0AEC0'
                        },
                        ticks: {
                            color: '#A0AEC0',
                            backdropColor: 'transparent'
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