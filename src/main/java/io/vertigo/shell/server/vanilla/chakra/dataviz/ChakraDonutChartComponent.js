class ChakraDonutChartComponent extends Component {
    constructor({ title, labels, data }) {
        super();
        this.title = title || 'Chakra Donut Chart';
        this.labels = labels || [];
        this.data = data || [];
        this.canvasId = `chakra-donutchart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-donutchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-donutchart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: this.labels,
                datasets: [{
                    data: this.data,
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
}