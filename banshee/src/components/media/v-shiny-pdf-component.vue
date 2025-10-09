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

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyPdfComponent } from '../../models/media/pdf/ShinyPdfComponent';

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

export default defineComponent({
  name: 'VShinyPdfComponent',
  props: {
    data: {
      type: Object as () => ShinyPdfComponent,
      required: true,
    },
  },
  data() {
    return {
      pdfDoc: null as PdfDocument | null,
      currentPage: 1,
      numPages: 0,
      scale: 1.0,
      loading: true,
      error: null as string | null,
    };
  },
  mounted() {
    this.currentPage = this.data.initialPage || 1;
    this.loadPdf();
  },
  watch: {
    'data.pdfPath'() {
      this.loadPdf();
    },
    currentPage() {
      this.renderPage();
    },
    scale() {
      this.renderPage();
    },
  },
  methods: {
    async loadPdf() {
      this.loading = true;
      this.error = null;

      try {
        if (typeof window.pdfjsLib === 'undefined') {
          throw new Error('PDF.js n\'est pas chargé. Ajoutez le script dans votre index.html');
        }

        const loadingTask = window.pdfjsLib.getDocument(this.data.pdfPath);
        this.pdfDoc = await loadingTask.promise;
        this.numPages = this.pdfDoc.numPages;

        if (this.currentPage > this.numPages) {
          this.currentPage = 1;
        }

        await this.renderPage();
      } catch (err: any) {
        this.error = `Erreur lors du chargement du PDF: ${err.message}`;
        console.error(err);
      }
    },
    async renderPage() {
      if (!this.pdfDoc) return;

      try {
        const page = await this.pdfDoc.getPage(this.currentPage);
        const canvas = this.$refs.pdfCanvas as HTMLCanvasElement;
        const context = canvas.getContext('2d');

        if (!context) {
          throw new Error('Could not get 2D context for canvas');
        }

        const viewport = page.getViewport({ scale: this.scale });
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        const renderContext = {
          canvasContext: context,
          viewport: viewport,
        };

        await page.render(renderContext).promise;
      } catch (err: any) {
        this.error = `Erreur lors du rendu de la page: ${err.message}`;
        console.error(err);
      }
    },
    nextPage() {
      if (this.currentPage < this.numPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    },
    zoomIn() {
      if (this.scale < 3) {
        this.scale += 0.25;
      }
    },
    zoomOut() {
      if (this.scale > 0.5) {
        this.scale -= 0.25;
      }
    },
    resetZoom() {
      this.scale = 1.0;
    },
  },
  beforeUnmount() {
    if (this.pdfDoc) {
      this.pdfDoc.destroy();
    }
  },
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
