<template>
    <v-card>
        <v-card-title>Code Editor ({{ data.language }})</v-card-title>
        <v-card-text>
            <Codemirror
                v-model:value="code"
                :options="cmOptions"
                border
                placeholder="Code goes here..."
                :height="200"
            />
        </v-card-text>
    </v-card>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue';
import { Codemirror } from 'vue-codemirror';
import { javascript } from '@codemirror/lang-javascript';
import { oneDark } from '@codemirror/theme-one-dark';
import { ShinyCodeEditor } from '../../models/input/codeeditor/ShinyCodeEditor';

const props = defineProps<{
    data: ShinyCodeEditor
}>()

const code = ref(props.data.content);

const cmOptions = computed(() => ({
    extensions: [javascript({ jsx: true }), oneDark],
    lineNumbers: true,
    mode: props.data.language,
}));

watch(() => props.data.content, (newContent) => {
    code.value = newContent;
});

</script>
