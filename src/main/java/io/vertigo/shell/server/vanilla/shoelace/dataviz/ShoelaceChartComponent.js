class ShoelaceChartComponent extends Component {
    constructor({ title, type, labels, series }) {
        super();
        this.title = title || 'Shoelace Chart';
        this.type = type || 'bar'; // bar, pie, doughnut, line, radar
        this.labels = labels || [];
        this.series = series || []; // [{ label: 'Series 1', data: [10, 20, 30] }]
        this.canvasId = `shoelace-chart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<sl-card style="margin-bottom: 1em;">
                    <div slot="header">${this.title}</div>
                    <canvas id="${this.canvasId}"></canvas>
                </sl-card>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        const datasets = this.series.map((serie, index) => {
            const colors = ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9']; // Shoelace-like colors
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
                            color: 'var(--sl-color-neutral-700)' // Shoelace-like color
                        },
                        grid: {
                            color: 'var(--sl-color-neutral-200)' // Shoelace-like color
                        }
                    },
                    x: {
                        ticks: {
                            color: 'var(--sl-color-neutral-700)' // Shoelace-like color
                        },
                        grid: {
                            color: 'var(--sl-color-neutral-200)' // Shoelace-like color
                        }
                    },
                    r: { // For radar charts
                        angleLines: {
                            color: 'var(--sl-color-neutral-200)'
                        },
                        grid: {
                            color: 'var(--sl-color-neutral-200)'
                        },
                        pointLabels: {
                            color: 'var(--sl-color-neutral-700)'
                        },
                        ticks: {
                            color: 'var(--sl-color-neutral-700)',
                            backdropColor: 'transparent'
                        }
                    }
                },
                plugins: {
                    legend: {
                        labels: {
                            color: 'var(--sl-color-neutral-700)' // Shoelace-like color
                        }
                    }
                }
            }
        });
    }
}