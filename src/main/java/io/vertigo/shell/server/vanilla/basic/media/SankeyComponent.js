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