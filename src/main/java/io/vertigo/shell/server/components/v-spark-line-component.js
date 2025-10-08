Vue.component('v-spark-line-component', {
    props: ['data'],
    template: '<canvas :id="canvasId" class="sparkline-canvas"></canvas>',
    data() {
        return {
            canvasId: `sparkline-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const target = document.getElementById(this.canvasId);
        if (!target) {
            return;
        }
        if (!this.data.values || this.data.values.length === 0) {
            return;
        }
        new Chart(target, {
            type: 'line',
            data: {
                labels: this.data.values.map((_, i) => i + 1),
                datasets: [{
                    data: this.data.values,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderWidth: 1,
                    pointRadius: 0,
                    fill: true,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: { display: false },
                    tooltip: { enabled: false }
                },
                scales: {
                    x: { display: false },
                    y: { display: false }
                },
                elements: {
                    line: {
                        borderWidth: 1
                    }
                }
            }
        });
    }
});