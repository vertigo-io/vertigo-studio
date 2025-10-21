<template>
  <v-badge :content="data.content" :color="data.color" inline></v-badge>
</template>

<script setup lang="ts">
import { ShinyBadgeCell } from '../../../models/data/table/cell/ShinyBadgeCell';

defineProps<{
  data: ShinyBadgeCell;
}>();
</script>
