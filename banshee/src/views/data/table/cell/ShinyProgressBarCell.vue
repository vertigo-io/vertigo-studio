<template>
  <v-progress-linear
    :model-value="(data.value / data.maxValue) * 100"
    :color="data.color"
    height="10"
    rounded
  ></v-progress-linear>
</template>

<script setup lang="ts">
import { ShinyProgressBarCell } from '../../../../models/data/table/cell/ShinyProgressBarCell';

defineProps<{
  data: ShinyProgressBarCell;
}>();
</script>
