Vue.component('v-shiny-error-component', {
    props: ['data'],
    template: `
    <div class="error-message">{{ data.text || 'An error occurred' }}</div>
    `
});