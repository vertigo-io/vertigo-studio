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

class ProgressBarComponent extends LiveComponent {
	constructor({ id, title, value, total }) {
		super({ id });
		this.title = title || 'Progression';
		this.value = value || 0;
		this.total = total || 100;
		if (!this.id) {
			throw new Error("ProgressBar data must have an id.");
		}
	}

	toHtml() {
		const percentage = Math.min((this.value / this.total) * 100, 100);
		return `<div class="progress-container" id="${this.id}"><div class="progress-bar" style="width: ${percentage}%"></div><div class="progress-text">${Math.round(percentage)}%</div></div>`;
	}

	update(value) {
		this.value = value;
		const container = document.getElementById(this.id);
		if (!container) return;
		const newPercentage = Math.min((this.value / this.total) * 100, 100);
		const bar = container.querySelector('.progress-bar');
		const text = container.querySelector('.progress-text');
		if (bar && text) {
			bar.style.width = `${newPercentage}%`;
			text.textContent = `${Math.round(newPercentage)}%`;
			bar.classList.toggle('completed', newPercentage >= 100);
		}
	}

	complete() {
		this.update(this.total);
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

class SankeyComponent extends Component {
  constructor({ title, data }) {
    super();
    this.title = title || 'Sankey Flow';
    this.data = data || []; // format attendu: [{ from, to, flow }]
    this.canvasId = `sankey-${Math.random().toString(36).substr(2, 9)}`;
    this.chart = null;
  }

  toHtml() {
    return `<canvas id="${this.canvasId}" class="sankey-canvas"></canvas>`;
  }

  activate() {
    const target = document.getElementById(this.canvasId);
    if (!target) throw new Error(`Sankey canvas not found: ${this.canvasId}`);

    // Détruire l'ancien graphe si besoin
    if (this.chart) this.chart.destroy();

    this.chart = new Chart(target, {
      type: 'sankey',
      data: {
        datasets: [{
          data: this.data,
          colorFrom: (c) => 'rgba(75, 192, 192, 0.8)',
          colorTo: (c) => 'rgba(153, 102, 255, 0.8)',
          colorMode: 'gradient'
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          title: {
            display: true,
            text: this.title
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
}

class GeoMapComponent extends Component {
    constructor({ title, latitude, longitude, label }) {
        super();
        this.title = title || 'Map';
        this.latitude = latitude || 0;
        this.longitude = longitude || 0;
        this.label = label || '';
        this.mapId = `map-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.mapId}" class="map-canvas"></div>`;
    }

    activate() {
        const mapContainer = document.getElementById(this.mapId);
        if (!mapContainer) {
            throw new Error(`Map container not found for ID: ${this.mapId}`);
        }

        // Initialize Leaflet map
        const map = L.map(this.mapId).setView([this.latitude, this.longitude], 12);

        // Add OpenStreetMap tiles
        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            maxZoom: 18,
            tileSize: 256
        }).addTo(map);

        // Add marker for POI
        const marker = L.marker([this.latitude, this.longitude]).addTo(map);
        if (this.label) {
            marker.bindPopup(this.label).openPopup();
        }
    }
}
