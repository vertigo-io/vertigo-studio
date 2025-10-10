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
</style>