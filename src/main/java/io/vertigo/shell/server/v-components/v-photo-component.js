Vue.component('v-photo-component', {
    props: ['data'],
    template: `
    <div class="photo-container">
        <img v-if="data.url" :src="data.url" :alt="data.alt" class="photo-image">
        <div v-else>No image URL provided.</div>
    </div>
    `
});