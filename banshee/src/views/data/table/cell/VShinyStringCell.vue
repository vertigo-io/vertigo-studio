<template>
  <span>{{ data.value }}</span>
</template>

<script setup lang="ts">
import { ShinyStringCell } from '../../../../models/data/table/cell/ShinyStringCell';

defineProps<{
  data: ShinyStringCell;
}>();
</script>
