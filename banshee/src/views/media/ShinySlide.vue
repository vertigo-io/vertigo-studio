<template>
    <div class="slide-container">
        <div class="slide-title">{{ data.title || 'Slides' }}</div>
        <div class="slide-wrapper">
            <iframe :srcdoc="slidevTemplate" class="slide-iframe"></iframe>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ShinySlide } from '../../models/media/slide/ShinySlide';

/**
 * Vue component that renders presentation slides from Markdown content using an iframe.
 * It expects a `data` prop containing the Markdown for the slides.
 */
const props = defineProps<{
    /**
     * The data for the slide component, which includes the Markdown content for the slides.
     */
    data: ShinySlide
}>()

const slidevTemplate = ref(`
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Slidev</title>
  <link href="https://unpkg.com/slidev-theme-default/dist/style.css" rel="stylesheet">
  <link href="https://unpkg.com/@slidev/client/dist/style.css" rel="stylesheet">
</head>
<body>
  <div id="app">
    ${props.data.markdown}
  </div>
  <script src="https://unpkg.com/vue@3/dist/vue.global.prod.js"><\/script>
  <script src="https://unpkg.com/@slidev/client/dist/main.js"><\/script>
</body>
</html>
`);

</script>

<style scoped>
.slide-container {
    width: 100%;
    max-width: 100%;
    overflow: hidden;
    border-radius: 8px;
    box-shadow: 0 4px 16px var(--general-shadow);
}

.slide-title {
    font-size: 1.2em;
    font-weight: bold;
    padding: 10px;
    background-color: var(--chakra-colors-gray-100);
}

.slide-wrapper {
    width: 100%;
    height: 500px;
}

.slide-iframe {
    width: 100%;
    height: 100%;
    border: none;
}
</style>
