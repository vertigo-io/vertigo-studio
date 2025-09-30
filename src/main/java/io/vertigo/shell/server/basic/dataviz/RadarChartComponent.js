class RadarChartComponent extends Component {
    constructor({ title, labels, series }) {
        super();
        this.title = title || 'Radar Chart';
        this.labels = labels || [];
        this.series = series || [];
        this.canvasId = `radarchart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="radarchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="radarchart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        const datasets = this.series.map((serie, index) => {
            const color = ['#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9'][index % 5];
            return {
                label: serie.name,
                data: serie.data,
                backgroundColor: color + '80', // Add alpha transparency
                borderColor: color,
                borderWidth: 1
            };
        });

        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: this.labels,
                datasets: datasets
            },
            options: {
                scales: {
                    r: {
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
                            backdropColor: '#1A202C'
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