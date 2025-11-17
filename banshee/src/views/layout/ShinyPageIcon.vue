<template>
    <v-card class="ma-2 pa-2 text-center" @click="dialog = true">
        <v-icon size="64">mdi-file-document-outline</v-icon>
        <v-card-title>{{ data.title }}</v-card-title>

        <ShinyFullPage :data="data" v-model:dialog="dialog"></ShinyFullPage>
    </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ShinyPage } from '../../models/layout/page/ShinyPage';
import ShinyFullPage from './ShinyFullPage.vue';

const props = defineProps<{
    data: ShinyPage
}>()

const dialog = ref(false);
</script>
