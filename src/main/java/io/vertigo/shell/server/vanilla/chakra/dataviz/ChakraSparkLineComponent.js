class ChakraSparkLineComponent extends Component {
    constructor({ title, data }) {
        super();
        this.title = title || 'Chakra Sparkline';
        this.data = data || [];
        this.canvasId = `chakra-sparkline-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div class="chakra-sparkline-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
                    <h3 class="chakra-sparkline-title" style="color: #CBD5E0; margin-bottom: 10px;">${this.title}</h3>
                    <canvas id="${this.canvasId}"></canvas>
                </div>`;
    }

    activate() {
        const ctx = document.getElementById(this.canvasId).getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: this.data.map((_, i) => i),
                datasets: [{
                    data: this.data,
                    borderColor: '#3182CE',
                    borderWidth: 2,
                    fill: false,
                    pointRadius: 0,
                }]
            },
            options: {
                scales: {
                    x: { display: false },
                    y: { display: false }
                },
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: false }
                }
            }
        });
    }
}