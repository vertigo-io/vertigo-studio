<template>
  <v-chip
    :color="data.color"
    :variant="data.variant"
    :closable="data.closable"
    :prepend-icon="data.icon"
  >
    {{ data.text }}
  </v-chip>
</template>

<script setup lang="ts">
import { ShinyChipCell } from '../../../../models/data/table/cell/ShinyChipCell';

defineProps<{
  data: ShinyChipCell;
}>();
</script>
