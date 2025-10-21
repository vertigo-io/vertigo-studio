<template>
  <v-avatar :image="data.src" :alt="data.alt" :size="data.size"></v-avatar>
</template>

<script setup lang="ts">
import { ShinyAvatarCell } from '../../../models/data/table/cell/ShinyAvatarCell';

defineProps<{
  data: ShinyAvatarCell;
}>();
</script>
