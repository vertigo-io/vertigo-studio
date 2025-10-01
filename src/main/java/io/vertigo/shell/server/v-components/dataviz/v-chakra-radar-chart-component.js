Vue.component('v-chakra-radar-chart-component', {
    props: ['data'],
    template: `
    <div class="chakra-radarchart-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-radarchart-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Radar Chart' }}</h3>
        <canvas :id="canvasId"></canvas>
    </div>
    `,
	data() {
	    return {
	        canvasId: `chakra-radarchart-${Math.random().toString(36).substr(2, 9)}`
	    };
	},
	mounted() {
	    const ctx = document.getElementById(this.canvasId).getContext('2d');
        const datasets = this.data.series.map((serie, index) => {
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
                labels: this.data.labels,
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
});