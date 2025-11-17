<template>
    <v-snackbar
        v-model="snackbar"
        :timeout="data.timeout"
        :color="color"
        location="bottom right"
    >
        {{ data.message }}
        <template v-slot:actions>
            <v-btn
                color="white"
                variant="text"
                @click="snackbar = false"
            >
                Close
            </v-btn>
        </template>
    </v-snackbar>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue';
import { ShinyNotification } from '../../models/feedback/notification/ShinyNotification';

const props = defineProps<{
    data: ShinyNotification
}>()

const snackbar = ref(true);

const color = computed(() => {
    switch (props.data.type) {
        case 'SUCCESS': return 'success';
        case 'INFO': return 'info';
        case 'WARNING': return 'warning';
        case 'ERROR': return 'error';
        default: return 'info';
    }
});

watch(() => props.data, () => {
    snackbar.value = true;
});
</script>
