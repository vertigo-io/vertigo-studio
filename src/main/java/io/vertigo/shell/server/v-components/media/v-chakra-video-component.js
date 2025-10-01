Vue.component('v-chakra-video-component', {
    props: ['data'],
    template: `
    <div class="chakra-video-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px;">
        <h3 class="chakra-video-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title }}</h3>
        <iframe width="100%" height="315" :src="'https://www.youtube.com/embed/' + data.videoId" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
    </div>
    `
});