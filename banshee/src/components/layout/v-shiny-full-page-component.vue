<template>
    <v-dialog
        v-model="dialog"
        fullscreen
        :scrim="false"
        transition="dialog-bottom-transition"
    >
        <v-card>
            <v-toolbar dark color="primary">
                <v-btn icon dark @click="dialog = false">
                    <v-icon>mdi-close</v-icon>
                </v-btn>
                <v-toolbar-title>{{ data.title }}</v-toolbar-title>
                <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>
                <component :is="shinyRegistry.resolve(data.layout.shinyType)" :data="data.layout"></component>
            </v-card-text>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref, inject, watch } from 'vue';
import { ShinyPage } from '../../models/layout/page/ShinyPage';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
    data: ShinyPage
}>()

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry')!;
const dialog = ref(false); // Controlled by parent

watch(() => props.data, () => {
    dialog.value = true; // Open dialog when new data is passed
});
</script>
