<template>
    <v-file-input
        :label="data.label"
        :multiple="data.multiple"
        :accept="data.accept"
        @change="onFileChange"
    ></v-file-input>
</template>

<script setup lang="ts">
import { ShinyFileUpload } from '../../models/input/fileupload/ShinyFileUpload';

const { data } = defineProps<{
    data: ShinyFileUpload
}>()

function onFileChange(event: Event) {
    const target = event.target as HTMLInputElement;
    if (target.files) {
        // You can handle the files here, e.g., upload them to a server.
        console.log(target.files);
    }
}
</script>
