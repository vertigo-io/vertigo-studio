Vue.component('v-chakra-pie-chart-component', {
  props: ['data'],
  template: `
    <div class="chakra-chart-container">
      <h3 class="chakra-component-title">{{ data.title || 'Chakra Pie Chart' }}</h3>
      <div class="chart-canvas-wrapper">
        <canvas :id="canvasId"></canvas>
      </div>
    </div>
  `,
  data() {
    return {
      canvasId: `chakra-piechart-${Math.random().toString(36).substr(2, 9)}`
    };
  },
  mounted() {
    const ctx = document.getElementById(this.canvasId).getContext('2d');
    const baseColors = [
      '#3182CE', '#63B3ED', '#4299E1', '#319795', '#81E6D9', 
      '#F6AD55', '#F6E05E', '#FEB2B2', '#BEE3F8', '#C6F6D5'
    ];

    let chartType;
    let datasetsConfig;

    if (this.data.series && this.data.series.length > 1) {
      // Multi-series: utiliser des doughnuts concentriques
      chartType = 'doughnut';
      const seriesCount = this.data.series.length;
      
      datasetsConfig = this.data.series.map((serie, serieIndex) => {
        // Calculer le cutout pour créer des anneaux concentriques
        const cutoutPercentage = 30 + (serieIndex * (50 / seriesCount));
        
        // Générer des couleurs distinctes pour chaque série
        const colorOffset = serieIndex * Math.floor(baseColors.length / seriesCount);
        const serieColors = serie.data.map((_, index) => {
          const colorIndex = (colorOffset + index) % baseColors.length;
          return baseColors[colorIndex];
        });
        
        return {
          label: serie.name,
          data: serie.data || [],
          backgroundColor: serieColors,
          borderColor: '#1A202C',
          borderWidth: 2,
          // Configurer le rayon pour chaque dataset
          weight: 1
        };
      });
    } else if (this.data.series && this.data.series.length === 1) {
      // Single series: pie chart standard
      chartType = 'pie';
      datasetsConfig = [{
        data: this.data.series[0].data || [],
        backgroundColor: baseColors.slice(0, this.data.series[0].data.length),
        borderColor: '#1A202C',
        borderWidth: 2
      }];
    } else {
      // Cas par défaut
      chartType = 'pie';
      datasetsConfig = [{
        data: [],
        backgroundColor: baseColors,
        borderColor: '#1A202C',
        borderWidth: 2
      }];
    }

    new Chart(ctx, {
      type: chartType,
      data: {
        labels: this.data.labels,
        datasets: datasetsConfig
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
        plugins: {
          legend: {
            position: 'right',
            labels: {
              color: '#A0AEC0',
              padding: 15,
              font: {
                size: 12
              }
            }
          },
          tooltip: {
            backgroundColor: '#2D3748',
            titleColor: '#E2E8F0',
            bodyColor: '#CBD5E0',
            borderColor: '#4A5568',
            borderWidth: 1
          }
        }
      }
    });
  }
});