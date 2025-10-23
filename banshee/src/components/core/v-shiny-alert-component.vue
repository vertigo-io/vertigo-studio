<template>
  <div class="pa-4">
    <v-alert
      :type="model.alertType"
      :title="model.title"
      :closable="model.closable"
      :icon="model.icon"
      :prominent="model.prominent"
      variant="tonal"
    >
      {{ model.content }}
    </v-alert>
  </div>
</template>

<script setup lang="ts">
import type { ShinyAlert } from '@/models/feedback/alert/ShinyAlert';

defineProps<{
  model: ShinyAlert
}>()
</script>
