<template>
  <div class="pa-4">
    <v-alert
      :type="data.alertType"
      :title="data.title"
      :closable="data.closable"
      :icon="data.icon"
      :prominent="data.prominent"
      variant="tonal"
    >
      {{ data.content }}
    </v-alert>
  </div>
</template>

<script setup lang="ts">
import type { ShinyAlert } from '@/models/core/alert/ShinyAlert';

defineProps<{
  data: ShinyAlert
}>()
</script>
