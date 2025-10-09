<template>
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
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyCardComponent } from '../../models/data/card/ShinyCardComponent';
import { ShinyCardFormat } from '../../models/data/card/ShinyCardFormat';

declare const lucide: any; // Declare lucide to avoid TypeScript errors

export default defineComponent({
  name: 'VShinyCardComponent',
  props: {
    data: {
      type: Object as () => ShinyCardComponent,
      required: true,
    },
  },
  computed: {
    cardWidth(): string {
      switch (this.data.format) {
        case ShinyCardFormat.S: return '250px';
        case ShinyCardFormat.M: return '350px';
        case ShinyCardFormat.L: return '450px';
        default: return '350px';
      }
    },
  },
  mounted() {
    // Initialize Lucide icons
    if (this.data.icon) {
      lucide.createIcons();
    }
  },
});
</script>

<style scoped>
.shiny-card {
  background-color: var(--shiny-card-bg) !important;
  color: var(--chakra-text) !important;
  border-radius: 8px;
  box-shadow: 0 4px 6px var(--general-shadow);
}

.shiny-card--s {
  max-width: 250px;
}

.shiny-card--m {
  max-width: 350px;
}

.shiny-card--l {
  max-width: 450px;
}

.v-card__title {
  background-color: var(--general-bg) !important;
  color: var(--chakra-title-text) !important;
}

.v-card__subtitle {
  color: var(--chakra-paragraph-text) !important;
}

.v-card__text {
  color: var(--chakra-text) !important;
}

.v-btn {
  text-transform: none !important;
}
</style>