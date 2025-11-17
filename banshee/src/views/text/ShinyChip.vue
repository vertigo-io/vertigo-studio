<template>
  <div class="pa-2 d-inline-block">
    <v-chip
      :color="data.color"
      :variant="data.variant || 'flat'"
      :closable="data.closable"
    >
      <v-icon v-if="data.icon" start :icon="data.icon"></v-icon>
      {{ data.text }}
    </v-chip>
  </div>
</template>

<script setup lang="ts">
import type { ShinyChip } from '@/models/text/chip/ShinyChip';

defineProps<{
  data: ShinyChip
}>()
</script>
