Vue.component('v-shiny-card-component', {
    props: ['data'],
    template: `
    <v-card
        :width="cardWidth"
        :class="['shiny-card', 'shiny-card--' + data.format.toLowerCase()]"
        :href="data.link"
        :target="data.link ? '_blank' : null"
        :ripple="data.link ? true : false"
    >
        <v-img
            v-if="data.imageUrl"
            :src="data.imageUrl"
            :alt="data.imageAlt || data.title"
            class="white--text align-end"
            height="200px"
        >
            <v-card-title v-if="data.title" class="d-flex align-center">
                <v-icon v-if="data.icon" left>{{ 'mdi-' + data.icon }}</v-icon>
                {{ data.title }}
            </v-card-title>
        </v-img>
        <v-card-title v-else-if="data.title" class="d-flex align-center">
            <v-icon v-if="data.icon" left>{{ 'mdi-' + data.icon }}</v-icon>
            {{ data.title }}
        </v-card-title>

        <v-card-subtitle v-if="data.subtitle" class="pb-0">
            {{ data.subtitle }}
        </v-card-subtitle>

        <v-card-text v-if="data.description" class="text--primary">
            <div>{{ data.description }}</div>
        </v-card-text>

        <v-card-actions v-if="data.badgeLabel || data.link">
            <v-chip
                v-if="data.badgeLabel"
                class="ma-2"
                :color="data.badgeColor || 'primary'"
                label
                small
            >
                {{ data.badgeLabel }}
            </v-chip>
            <v-spacer v-if="data.link"></v-spacer>
            <v-btn
                v-if="data.link"
                color="primary"
                text
                :href="data.link"
                target="_blank"
            >
                Voir plus
            </v-btn>
        </v-card-actions>
    </v-card>
    `,
    computed: {
        cardWidth() {
            switch (this.data.format) {
                case 'S': return '250px';
                case 'M': return '350px';
                case 'L': return '450px';
                default: return '350px';
            }
        }
    },
    mounted() {
        // Initialize Lucide icons
        if (this.data.icon) {
            lucide.createIcons();
        }
    }
});