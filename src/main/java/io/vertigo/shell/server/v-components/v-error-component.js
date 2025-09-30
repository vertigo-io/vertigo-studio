Vue.component('v-error-component', {
    props: ['data'],
    template: `
    <div class="error-message" style="color: orange;">{{ data.text || 'An error occurred' }}</div>
    `
});