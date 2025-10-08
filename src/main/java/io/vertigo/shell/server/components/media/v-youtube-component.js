Vue.component('v-youtube-component', {
    props: ['data'],
    template: `
    <div class="youtube-container">
        <iframe v-if="data.videoId" class="youtube-player" :src="'https://www.youtube.com/embed/' + data.videoId" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
        <div v-else>No video ID provided.</div>
    </div>
    `
});