<template>
    <v-card class="ma-2 pa-2 text-center" @click="dialog = true">
        <v-icon size="64">mdi-file-document-outline</v-icon>
        <v-card-title>{{ data.title }}</v-card-title>

        <v-shiny-full-page-component :data="data" v-model:dialog="dialog"></v-shiny-full-page-component>
    </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ShinyPage } from '../../models/layout/page/ShinyPage';
import VShinyFullPageComponent from './v-shiny-full-page-component.vue';

const props = defineProps<{
    data: ShinyPage
}>()

const dialog = ref(false);
</script>
