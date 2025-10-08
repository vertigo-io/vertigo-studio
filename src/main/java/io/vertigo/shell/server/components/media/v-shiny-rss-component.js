Vue.component('v-shiny-rss-component', {
    props: ['data'],
    template: `
    <div class="rss-feed">
        <div v-if="!data.items || data.items.length === 0">No items in the feed.</div>
        <div v-for="(item, index) in data.items" :key="index" class="rss-card">
            <div v-if="item.image" class="rss-image"><img :src="item.image" :alt="item.title" loading="lazy" onerror="this.style.display='none';"></div>
            <div v-else class="rss-image-placeholder"></div>
            <div class="rss-content">
                <h3 class="rss-headline"><a :href="item.link" target="_blank" rel="noopener noreferrer">{{ item.title }}</a></h3>
                <p class="rss-summary">{{ summary(item.description) }}</p>
            </div>
        </div>
    </div>
    `,
    methods: {
        summary(description) {
            return description.length > 150 ? description.substring(0, 150) + '...' : description;
        }
    }
});