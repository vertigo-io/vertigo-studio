<template>
  <div class="list-container">
    <div class="table-title" v-if="data.title">{{ data.title }}</div>
    <v-list :class="listClass" density="compact">
      <template v-for="(item, index) in data.items" :key="index">
        <template v-if="isShinyList(item)">
          <v-list-group>
            <template v-slot:activator="{ props }">
              <v-list-item
                v-bind="props"
                :title="item.title"
              ></v-list-item>
            </template>
            <div class="sublist-indent">
              <v-shiny-list-component :data="{ items: item.items }"></v-shiny-list-component>
            </div>
          </v-list-group>
        </template>
        <template v-else>
          <v-list-item :title="item"></v-list-item>
        </template>
      </template>
    </v-list>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { ShinyList } from '../../models/data/list/ShinyList';
import { ShinyListType } from '../../models/data/list/ShinyListType';

const props = defineProps<{
  data: ShinyList
}>()

const listClass = computed(() => {
  // Vuetify lists don't directly support 'dashed' or 'ordered' types via classes
  // Custom styling might be needed or interpret these as visual cues.
  // For now, we'll just return an empty string or a generic class if needed.
  return props.data.listType === ShinyListType.DASHED ? 'dashed-list' : '';
});

const isShinyList = (item: string | ShinyList): item is ShinyList => {
  return typeof item === 'object' && item !== null && 'items' in item;
};
</script>


<style scoped>
.sublist-indent {
  padding-left: 24px;
}
  
/* Styles pour les listes dans les conteneurs collapsible */
.list-container ul, .list-container ol {
	margin: 1rem 0;
	padding-left: 1.5rem;
	list-style-position: outside;
	font-family: 'Inter', 'Segoe UI', system-ui, sans-serif;
	font-size: 13px;
	color: var(--assistant-text);
}

.list-container li {
	margin-bottom: 0.5rem;
	line-height: 1.4;
}

.list-container li span.nested-title {
	font-weight: 600;
	display: inline-block;
	margin-right: 0.5rem;
	color: var(--json-key);
}

/* Style spécifique pour DASHED : tirets personnalisés */
.list-container .dashed-list {
	list-style: none;
	padding-left: 0;
}

.list-container .dashed-list li {
	position: relative;
	padding-left: 1.8rem;
	margin-bottom: 0.6rem;
}

.list-container .dashed-list li::before {
	content: "–"; /* Tiret en dash */
	position: absolute;
	left: 0;
	top: 0.1em;
	font-size: 1.3em;
	color: var(--x-neon-blue); /* Bleu néon de X */
	font-weight: bold;
	text-shadow: 0 0 4px var(--progress-glow);
}

/* Imbrication : indentation pour les sous-listes */
.list-container ul ul, .list-container ol ol, .list-container ul ol,
	.list-container ol ul {
	margin-left: 1rem;
	padding-left: 1rem;
	border-left: 2px solid var(--assistant-accent);
}

.list-container ul, .list-container ol {
		padding-left: 1rem;
	}
	.list-container .dashed-list li {
		padding-left: 1.4rem;
	}
	.list-container .dashed-list li::before {
		font-size: 1.1em; /* Réduction légère pour mobile */
	}

</style>