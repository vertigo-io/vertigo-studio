class ChakraBarChartComponent extends Component {
    constructor({ title, labels, data }) {
        super();
        this.title = title || 'Chakra Bar Chart';
        this.labels = labels || [];
        this.data = data || [];
        this.canvasId = `chakra-barchart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-barchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-barchart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: this.labels,
                datasets: [{
                    label: this.title,
                    data: this.data,
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
}

class ChakraRadarChartComponent extends Component {
    constructor({ title, labels, datasets }) {
        super();
        this.title = title || 'Chakra Radar Chart';
        this.labels = labels || [];
        this.datasets = datasets || [];
        this.canvasId = `chakra-radarchart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-radarchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-radarchart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'radar',
            data: {
                labels: this.labels,
                datasets: this.datasets.map(dataset => ({
                    ...dataset,
                    backgroundColor: 'rgba(144, 205, 244, 0.2)',
                    borderColor: 'rgba(144, 205, 244, 1)',
                    pointBackgroundColor: 'rgba(144, 205, 244, 1)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(144, 205, 244, 1)'
                }))
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