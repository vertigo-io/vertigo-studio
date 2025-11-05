<template>
  <v-rating
    :model-value="data.value"
    :length="data.scale === 'SCALE_5' ? 5 : 10"
    :half-increments="data.allowHalfRating"
    readonly
    density="compact"
    color="yellow-darken-3"
  ></v-rating>
</template>

<script setup lang="ts">
import { ShinyRatingCell } from '../../../../models/data/table/cell/ShinyRatingCell';

defineProps<{
  data: ShinyRatingCell;
}>();
</script>
