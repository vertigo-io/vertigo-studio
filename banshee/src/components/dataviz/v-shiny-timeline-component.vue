<template>
  <div class="pa-4">
    <h3 v-if="data.title" class="text-h5 mb-4">{{ data.title }}</h3>
    <v-timeline side="end">
      <v-timeline-item
        v-for="(item, index) in data.items"
        :key="index"
        :dot-color="item.color || 'primary'"
        :icon="item.icon"
        fill-dot
      >
        <v-card>
          <v-card-title :class="[item.color ? 'white--text' : '', 'text-h6']" :style="{ backgroundColor: item.color || '#1976D2' }">
            {{ item.title }}
          </v-card-title>
          <v-card-text class="bg-white text--primary">
            <p v-if="item.content" class="mt-2">{{ item.content }}</p>
          </v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
  </div>
</template>

<script setup lang="ts">
import type { ShinyTimeline } from '@/models/dataviz/timeline/ShinyTimeline';

defineProps<{
  data: ShinyTimeline
}>()
</script>
