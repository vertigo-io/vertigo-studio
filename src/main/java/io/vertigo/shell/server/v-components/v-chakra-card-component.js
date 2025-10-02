Vue.component('v-chakra-card-component', {
    props: ['data'],
    template: `
    <div class="chakra-card">
        <img v-if="data.imageUrl" :src="data.imageUrl" :alt="data.title" class="chakra-card-image">
        <div class="chakra-card-body">
            <h4 class="chakra-card-title">{{ data.title }}</h4>
            <p class="chakra-card-description">{{ data.description }}</p>
        </div>
    </div>
    `
});