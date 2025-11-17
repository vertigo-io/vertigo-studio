<template>
    <v-dialog
        :model-value="dialog"
        @update:model-value="val => dialog = val"
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
import { ref, inject, watch, computed } from 'vue';
import { ShinyPage } from '../../models/layout/page/ShinyPage';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
    data: ShinyPage,
    dialog: boolean
}>()

const emit = defineEmits(['update:dialog']);

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry')!;

const dialog = computed({
    get() {
        return props.dialog;
    },
    set(value) {
        emit('update:dialog', value);
    }
});
</script>
