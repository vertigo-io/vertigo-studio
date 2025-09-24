class ChakraPieChartComponent extends Component {
    constructor({ title, labels, data }) {
        super();
        this.title = title || 'Chakra Pie Chart';
        this.labels = labels || [];
        this.data = data || [];
        this.canvasId = `chakra-piechart-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-piechart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-piechart-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'pie',
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