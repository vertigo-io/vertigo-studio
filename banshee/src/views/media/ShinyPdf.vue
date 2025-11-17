<template>
  <v-card :height="data.height || '600px'" class="d-flex flex-column shiny-pdf-card">
    <v-card-title class="bg-primary shiny-pdf-card-title">
      <span class="text-h6">{{ data.title || 'Document PDF' }}</span>
      <v-spacer></v-spacer>
      <v-btn icon @click="$emit('close')">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-card-title>

    <v-card-text class="flex-grow-1 pa-0 d-flex flex-column shiny-pdf-card-text">
      <div class="pdf-controls pa-2 bg-grey-lighten-3 d-flex align-center justify-center shiny-pdf-controls">
        <v-btn icon size="small" @click="prevPage" :disabled="currentPage <= 1">
          <v-icon>mdi-chevron-left</v-icon>
        </v-btn>

        <span class="mx-4">
          Page {{ currentPage }} / {{ numPages || '...' }}
        </span>

        <v-btn icon size="small" @click="nextPage" :disabled="currentPage >= numPages">
          <v-icon>mdi-chevron-right</v-icon>
        </v-btn>

        <v-divider vertical class="mx-3"></v-divider>

        <v-btn icon size="small" @click="zoomOut" :disabled="scale <= 0.5">
          <v-icon>mdi-minus</v-icon>
        </v-btn>

        <span class="mx-2">{{ Math.round(scale * 100) }}%</span>

        <v-btn icon size="small" @click="resetZoom" class="ml-2">
          <v-icon>mdi-restore</v-icon>
        </v-btn>
      </div>

      <div class="pdf-container flex-grow-1" style="overflow: auto; position: relative; background-color: var(--general-bg);">
        <v-progress-circular
          v-if="loading"
          indeterminate
          color="primary"
          style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%);"
        ></v-progress-circular>

        <v-alert
          v-if="error"
          type="error"
          class="ma-4"
        >
          {{ error }}
        </v-alert>

        <canvas
          ref="pdfCanvas"
          style="display: block; margin: 0 auto;"
        ></canvas>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, onBeforeUnmount } from 'vue';
import { ShinyPdf } from '../../models/media/pdf/ShinyPdf';

// Declare PDF.js types if not available globally
declare global {
  interface Window {
    pdfjsLib: any;
  }
}

interface PdfDocument {
  numPages: number;
  getPage(pageNumber: number): Promise<any>;
  destroy(): void;
}

const props = defineProps<{
  data: ShinyPdf
}>()

const pdfDoc = ref<PdfDocument | null>(null);
const currentPage = ref(1);
const numPages = ref(0);
const scale = ref(1.0);
const loading = ref(true);
const error = ref<string | null>(null);
const pdfCanvas = ref<HTMLCanvasElement | null>(null);

const loadPdf = async () => {
  loading.value = true;
  error.value = null;

  try {
    if (typeof window.pdfjsLib === 'undefined') {
      throw new Error('PDF.js n\'est pas chargé. Ajoutez le script dans votre index.html');
    }

    const loadingTask = window.pdfjsLib.getDocument(props.data.pdfPath);
    pdfDoc.value = await loadingTask.promise;
    numPages.value = pdfDoc.value.numPages;

    if (currentPage.value > numPages.value) {
      currentPage.value = 1;
    }

    await renderPage();
  } catch (err: any) {
    error.value = `Erreur lors du chargement du PDF: ${err.message}`;
    console.error(err);
  } finally {
    loading.value = false;
  }
};

const renderPage = async () => {
  if (!pdfDoc.value || !pdfCanvas.value) return;

  try {
    const page = await pdfDoc.value.getPage(currentPage.value);
    const context = pdfCanvas.value.getContext('2d');

    if (!context) {
      throw new Error('Could not get 2D context for canvas');
    }

    const viewport = page.getViewport({ scale: scale.value });
    pdfCanvas.value.height = viewport.height;
    pdfCanvas.value.width = viewport.width;

    const renderContext = {
      canvasContext: context,
      viewport: viewport,
    };

    await page.render(renderContext).promise;
  } catch (err: any) {
    error.value = `Erreur lors du rendu de la page: ${err.message}`;
    console.error(err);
  }
};

const nextPage = () => {
  if (currentPage.value < numPages.value) {
    currentPage.value++;
  }
};

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--;
  }
};

const zoomIn = () => {
  if (scale.value < 3) {
    scale.value += 0.25;
  }
};

const zoomOut = () => {
  if (scale.value > 0.5) {
    scale.value -= 0.25;
  }
};

const resetZoom = () => {
  scale.value = 1.0;
};

onMounted(() => {
  currentPage.value = props.data.initialPage || 1;
  loadPdf();
});

watch(() => props.data.pdfPath, () => {
  loadPdf();
});

watch(currentPage, () => {
  renderPage();
});

watch(scale, () => {
  renderPage();
});

onBeforeUnmount(() => {
  if (pdfDoc.value) {
    pdfDoc.value.destroy();
  }
});
</script>

<style scoped>
.shiny-pdf-card {
  background-color: var(--general-surface) !important;
  color: var(--general-text) !important;
}

.shiny-pdf-card-title {
  background-color: var(--assistant-bg) !important;
  color: var(--assistant-text) !important;
  padding: 10px 15px;
  display: flex;
  align-items: center;
}

.shiny-pdf-card-text {
  background-color: var(--general-bg) !important;
}

.shiny-pdf-controls {
  background-color: var(--assistant-bg) !important;
  color: var(--general-text) !important;
  border-bottom: 1px solid var(--assistant-accent);
}

.pdf-container {
  /* Specific styles for the PDF rendering area */
}
</style>
