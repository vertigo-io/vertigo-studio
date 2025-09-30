Vue.component('v-chakra-card-component', {
    props: ['data'],
    template: `
    <div class="chakra-card" style="background-color: #2D3748; border-radius: 8px; overflow: hidden; color: #CBD5E0; max-width: 300px;">
        <img v-if="data.imageUrl" :src="data.imageUrl" :alt="data.title" style="width: 100%; height: auto;">
        <div class="chakra-card-body" style="padding: 15px;">
            <h4 class="chakra-card-title" style="font-weight: bold; margin-bottom: 10px;">{{ data.title }}</h4>
            <p class="chakra-card-description">{{ data.description }}</p>
        </div>
    </div>
    `
});