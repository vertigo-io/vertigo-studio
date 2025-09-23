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

class BarChartComponent extends Component {
	constructor({ title, header, values }) {
		super();
		this.title = title || 'Bar Chart';
		this.header = header || [];
		this.values = values || [];
		this.canvasId = `barchart-${Math.random().toString(36).substr(2, 9)}`;
	}

	toHtml() {
		return `<canvas id="${this.canvasId}" class="chart-canvas"></canvas>`;
	}

	activate() {
		const canvas = document.getElementById(this.canvasId);
		if (!canvas) {
			throw new Error(`Canvas not found for ID: ${this.canvasId}`);
		}
		const chartConfig = {
			type: 'bar',
			data: {
				labels: this.header,
				datasets: [{
					label: this.title,
					data: this.values,
					backgroundColor: 'rgba(29, 161, 242, 0.7)',
					borderColor: '#1DA1F2',
					borderWidth: 1,
					barThickness: 20,
					categoryPercentage: 0.8
				}]
			},
			options: {
				indexAxis: 'y',
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						display: true,
						labels: {
							color: '#E6E8EA',
							font: {
								family: "'Inter', 'Segoe UI', system-ui, sans-serif",
								size: 12
							}
						}
					}
				},
				scales: {
					x: {
						beginAtZero: true,
						ticks: {
							color: '#E6E8EA',
							font: {
								family: "'Inter', 'Segoe UI', system-ui, sans-serif",
								size: 12
							}
						},
						grid: {
							color: 'rgba(255, 255, 255, 0.1)',
							borderColor: '#3C4047'
						}
					},
					y: {
						ticks: {
							color: '#E6E8EA',
							font: {
								family: "'Inter', 'Segoe UI', system-ui, sans-serif",
								size: 12
							}
						},
						grid: {
							color: 'rgba(255, 255, 255, 0.1)',
							borderColor: '#3C4047'
						}
					}
				}
			}
		};
		new Chart(canvas.getContext('2d'), chartConfig);
	}
}

class GaugeComponent extends Component {
    constructor({ title, value, min, maxValue, label }) {
        super();
        this.title = title || 'Gauge';
        this.value = value || 0;
        this.min = min || 0;
        this.max = maxValue || 100;
        this.label = label || '';
        this.canvasId = `gauge-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<canvas id="${this.canvasId}" class="gauge-canvas"></canvas>`;
    }

    activate() {
        const target = document.getElementById(this.canvasId);
        if (!target) {
            throw new Error(`Gauge canvas not found for ID: ${this.canvasId}`);
        }
        
        // Use minimal options for debugging
        const gauge = new Gauge(target);
        gauge.maxValue = this.max;
        gauge.setMinValue(this.min);
        gauge.set(this.value);
    }
}

class StatusComponent extends Component {
    constructor({ title, types }) {
        super();
        this.title = title || 'Status';
        this.types = types || [];
        this.divId = `status-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.divId}" class="status-container">
                    <h3 class="status-title">${this.title}</h3>
                    <div class="status-indicators"></div>
                </div>`;
    }

    activate() {
        const indicatorsContainer = document.querySelector(`#${this.divId} .status-indicators`);
        if (!indicatorsContainer) {
            throw new Error(`Status indicators container not found for ID: ${this.divId}`);
        }

        const colorMap = {
            SUCCESS: 'green',
            ERROR: 'red',
            WARNING: 'orange',
            INFO: 'blue',
            NEUTRAL: 'gray'
        };

        this.types.forEach(type => {
            const indicator = document.createElement('div');
            indicator.className = 'status-indicator';
            indicator.style.backgroundColor = colorMap[type] || 'gray';
            indicatorsContainer.appendChild(indicator);
        });
    }
}

class RatingComponent extends Component {
    constructor({ label, value, scale, customMaxValue, showValue, showPercentage, showBox, separator, allowHalfRating }) {
        super();
        this.label = label || '';
        this.value = value || 0;
        this.scale = scale || 'SCALE_5';
        this.customMaxValue = customMaxValue || -1;
        this.showValue = showValue || false;
        this.showPercentage = showPercentage || false;
        this.showBox = showBox || false;
        this.separator = separator || '/';
        this.allowHalfRating = allowHalfRating || false;
        this.divId = `rating-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        let containerClasses = "rating-container";
        if (this.showBox) {
            containerClasses += " rating-box";
        }
        return `<div id="${this.divId}" class="${containerClasses}">
                    <span class="rating-label">${this.label}</span>
                    <div class="rating-stars"></div>
                    <span class="rating-value"></span>
                </div>`;
    }

    activate() {
        const container = document.getElementById(this.divId);
        const starsContainer = container.querySelector('.rating-stars');
        const valueSpan = container.querySelector('.rating-value');

        const filledColor = 'gold';
        const emptyColor = 'lightgray';
        const filledIcon = '★';
        const emptyIcon = '☆';
        const halfIcon = '½'; // This is not in the original enum, but useful for half ratings

        let maxValue;
        if (this.customMaxValue !== -1) {
            maxValue = this.customMaxValue;
        } else {
            switch (this.scale) {
                case 'SCALE_10':
                    maxValue = 10;
                    break;
                case 'SCALE_100':
                    maxValue = 100;
                    break;
                default:
                    maxValue = 5;
            }
        }

        starsContainer.innerHTML = '';
        for (let i = 1; i <= maxValue; i++) {
            const star = document.createElement('span');
            if (i <= this.value) {
                star.textContent = filledIcon;
                star.style.color = filledColor;
            } else if (this.allowHalfRating && i - 0.5 === this.value) {
                // This is a simplification. A real half-star implementation is more complex.
                // For now, we just show a character.
                star.textContent = halfIcon;
                star.style.color = filledColor;
            } else {
                star.textContent = emptyIcon;
                star.style.color = emptyColor;
            }
            starsContainer.appendChild(star);
        }

        if (this.showValue) {
            let valueText = this.value;
            if (this.showPercentage) {
                valueText += '%';
            }
            valueSpan.textContent = ` ${this.separator} ${valueText}`;
        }
    }
}


