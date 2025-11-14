<template>
    <v-card class="ma-2 pa-2 text-center" @click="openFullPage">
        <v-icon size="64">mdi-file-document-outline</v-icon>
        <v-card-title>{{ data.title }}</v-card-title>
    </v-card>
</template>

<script setup lang="ts">
import { ShinyPage } from '../../models/layout/page/ShinyPage';

const props = defineProps<{
    data: ShinyPage
}>()

const emit = defineEmits(['open-full-page']);

const openFullPage = () => {
    emit('open-full-page', props.data);
};
</script>
