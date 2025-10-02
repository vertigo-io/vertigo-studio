Vue.component('v-chakra-video-component', {
    props: ['data'],
    template: `
    <div class="chakra-chart-container">
        <h3 class="chakra-component-title">{{ data.title }}</h3>
        <iframe width="100%" height="315" :src="'https://www.youtube.com/embed/' + data.videoId" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
    </div>
    `
});