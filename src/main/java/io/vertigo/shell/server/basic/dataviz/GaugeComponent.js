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