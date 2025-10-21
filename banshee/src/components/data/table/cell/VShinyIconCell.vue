<template>
  <v-icon :color="data.color" :size="data.size">{{ data.icon }}</v-icon>
</template>

<script setup lang="ts">
import { ShinyIconCell } from '../../../models/data/table/cell/ShinyIconCell';

defineProps<{
  data: ShinyIconCell;
}>();
</script>
