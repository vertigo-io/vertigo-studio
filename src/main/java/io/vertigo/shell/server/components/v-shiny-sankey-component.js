Vue.component('v-shiny-sankey-component', {
    props: ['data'],
    template: '<canvas :id="canvasId" class="sankey-canvas"></canvas>',
    data() {
        return {
            canvasId: `sankey-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const target = document.getElementById(this.canvasId);
        if (!target) return;

        new Chart(target, {
            type: 'sankey',
            data: {
                datasets: [{
                    data: this.data.data,
                    colorFrom: (c) => 'rgba(75, 192, 192, 0.8)',
                    colorTo: (c) => 'rgba(153, 102, 255, 0..8)',
                    colorMode: 'gradient'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: this.data.title
                    },
                    legend: { display: false },
                    tooltip: {
                        callbacks: {
                            label: (context) => {
                                const { raw } = context;
                                return `${raw.from} -> ${raw.to}: ${raw.flow}`;
                            }
                        }
                    }
                }
            }
        });
    }
});