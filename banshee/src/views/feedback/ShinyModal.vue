<template>
    <v-dialog
        v-model="dialog"
        :persistent="data.persistent"
        max-width="500"
    >
        <v-card>
            <v-card-title class="headline">{{ data.title }}</v-card-title>
            <v-card-text>
                <component :is="shinyRegistry.resolve(data.content.shinyType)" :data="data.content"></component>
            </v-card-text>
            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="dialog = false">Close</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script setup lang="ts">
import { ref, inject, watch } from 'vue';
import { ShinyModal } from '../../models/feedback/modal/ShinyModal';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
    data: ShinyModal
}>()

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry')!;
const dialog = ref(true); // Modal is open by default when rendered

watch(() => props.data, () => {
    dialog.value = true; // Reopen if data changes
});
</script>
