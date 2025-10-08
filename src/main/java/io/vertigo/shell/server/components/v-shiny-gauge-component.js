Vue.component('v-shiny-gauge-component', {
    props: ['data'],
    template: '<canvas :id="canvasId" class="gauge-canvas"></canvas>',
    data() {
        return {
            canvasId: `gauge-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    mounted() {
        const target = document.getElementById(this.canvasId);
        if (!target) {
            return;
        }
        const gauge = new Gauge(target);
        gauge.maxValue = this.data.maxValue || 100;
        gauge.setMinValue(this.data.min || 0);
        gauge.set(this.data.value || 0);
    }
});