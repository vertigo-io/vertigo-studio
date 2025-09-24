class SparkLineComponent extends Component {
	constructor({ title, values }) {
		super();
		this.title = title || 'Sparkline';
		this.values = Array.isArray(values) ? values : []; // Ensure values is an array                                                                                                               
		this.canvasId = `sparkline-${Math.random().toString(36).substr(2, 9)}`;
	}

	toHtml() {
		return `<canvas id="${this.canvasId}" class="sparkline-canvas"></canvas>`;
	}

	activate() {
		const target = document.getElementById(this.canvasId);
		if (!target) {
			throw new Error(`Sparkline canvas not found for ID: ${this.canvasId}`);
		}

		// Validate values
		if (this.values.length === 0) {
			console.warn('No data provided for sparkline chart');
			return;
		}

		const data = {
			labels: this.values.map((_, i) => i + 1),
			datasets: [{
				data: this.values,
				borderColor: 'rgba(75, 192, 192, 1)',
				backgroundColor: 'rgba(75, 192, 192, 0.2)',
				borderWidth: 1,
				pointRadius: 0,
				fill: true,
				tension: 0.4
			}]
		};

		const options = {
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
		};

		new Chart(target, {
			type: 'line',
			data: data,
			options: options
		});
	}
}