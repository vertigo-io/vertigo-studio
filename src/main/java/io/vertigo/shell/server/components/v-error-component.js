Vue.component('v-error-component', {
    props: ['data'],
    template: `
    <div class="error-message">{{ data.text || 'An error occurred' }}</div>
    `
});