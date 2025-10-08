Vue.component('v-shiny-progress-bar-component', {
    props: ['data'],
    template: `
    <div class="progress-container" :id="data.id">
        <div class="progress-bar" :style="{ width: percentage + '%' }"></div>
        <div class="progress-text">{{ Math.round(percentage) }}%</div>
    </div>
    `,
    computed: {
        percentage() {
            return Math.min((this.data.value / this.data.total) * 100, 100);
        }
    }
});