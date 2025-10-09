<template>
  <div class="shiny-figlet-container">
    <svg :id="svgId" class="shiny-figlet-svg"></svg>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

interface FigletCharSet {
  [key: string]: number[][];
}

export default defineComponent({
  name: 'VShinyFigletComponent',
  props: {
    data: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      svgId: `figlet-${Math.random().toString(36).substr(2, 9)}`,
    };
  },
  mounted() {
    this.renderFiglet();
  },
  methods: {
    renderFiglet() {
      const svg = document.getElementById(this.svgId) as SVGElement | null;
      if (!svg) {
        console.error(`SVG element not found for ID: ${this.svgId}`);
        return;
      }

      const charSet: FigletCharSet = {
        'A': [[1, 1, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1], [1, 0, 1]],
        'B': [[1, 1, 0], [1, 0, 1], [1, 1, 0], [1, 0, 1], [1, 1, 0]],
        'C': [[1, 1, 1], [1, 0, 0], [1, 0, 0], [1, 0, 0], [1, 1, 1]],
        'D': [[1, 1, 0], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 0]],
        'E': [[1, 1, 1], [1, 0, 0], [1, 1, 0], [1, 0, 0], [1, 1, 1]],
        'F': [[1, 1, 1], [1, 0, 0], [1, 1, 0], [1, 0, 0], [1, 0, 0]],
        'G': [[1, 1, 1], [1, 0, 0], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
        'H': [[1, 0, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1], [1, 0, 1]],
        'I': [[1, 1, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0], [1, 1, 1]],
        'J': [[0, 1, 1], [0, 0, 1], [0, 0, 1], [1, 0, 1], [1, 1, 0]],
        'K': [[1, 0, 1], [1, 0, 0], [1, 1, 0], [1, 0, 0], [1, 0, 1]],
        'L': [[1, 0, 0], [1, 0, 0], [1, 0, 0], [1, 0, 0], [1, 1, 1]],
        'M': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1]],
        'N': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1]],
        'O': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
        'P': [[1, 1, 0], [1, 0, 1], [1, 1, 0], [1, 0, 0], [1, 0, 0]],
        'Q': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1], [0, 0, 1]],
        'R': [[1, 1, 0], [1, 0, 1], [1, 1, 0], [1, 0, 1], [1, 0, 1]],
        'S': [[1, 1, 1], [1, 0, 0], [1, 1, 1], [0, 0, 1], [1, 1, 1]],
        'T': [[1, 1, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0], [0, 1, 0]],
        'U': [[1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
        'V': [[1, 0, 1], [1, 0, 1], [1, 0, 1], [0, 1, 0], [0, 1, 0]],
        'W': [[1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1]],
        'X': [[1, 0, 1], [1, 0, 1], [0, 1, 0], [1, 0, 1], [1, 0, 1]],
        'Y': [[1, 0, 1], [1, 0, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0]],
        'Z': [[1, 1, 1], [0, 0, 1], [0, 1, 0], [1, 0, 0], [1, 1, 1]],
        ' ': [[0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0]],
        '0': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
        '1': [[0, 1, 0], [1, 1, 0], [0, 1, 0], [0, 1, 0], [1, 1, 1]],
        '2': [[1, 1, 0], [0, 0, 1], [0, 1, 0], [1, 0, 0], [1, 1, 1]],
        '3': [[1, 1, 1], [0, 0, 1], [0, 1, 1], [0, 0, 1], [1, 1, 1]],
        '4': [[1, 0, 1], [1, 0, 1], [1, 1, 1], [0, 0, 1], [0, 0, 1]],
        '5': [[1, 1, 1], [1, 0, 0], [1, 1, 0], [0, 0, 1], [1, 1, 0]],
        '6': [[1, 1, 1], [1, 0, 0], [1, 1, 1], [1, 0, 1], [1, 1, 1]],
        '7': [[1, 1, 1], [0, 0, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0]],
        '8': [[1, 1, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1], [1, 1, 1]],
        '9': [[1, 1, 1], [1, 0, 1], [1, 1, 1], [0, 0, 1], [1, 1, 1]]
      };

      const charWidth = 3;
      const charHeight = 5;
      const pixelSize = 4;
      const charSpacing = 1;
      const text = (this.data.text || '').toUpperCase();

      let svgWidth = 0;
      if (text.length > 0) {
        svgWidth = (text.length * (charWidth + charSpacing) - charSpacing) * pixelSize;
      }
      const svgHeight = charHeight * pixelSize;

      svg.setAttribute('width', svgWidth.toString());
      svg.setAttribute('height', svgHeight.toString());
      svg.setAttribute('viewBox', `0 0 ${svgWidth} ${svgHeight}`);

      let currentX = 0;
      for (let i = 0; i < text.length; i++) {
        const char = text[i];
        const charMatrix = charSet[char] || charSet[' '];

        for (let y = 0; y < charMatrix.length; y++) {
          for (let x = 0; x < charMatrix[y].length; x++) {
            if (charMatrix[y][x] === 1) {
              const rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
              rect.setAttribute('x', (currentX + x * pixelSize).toString());
              rect.setAttribute('y', (y * pixelSize).toString());
              rect.setAttribute('width', pixelSize.toString());
              rect.setAttribute('height', pixelSize.toString());
              rect.setAttribute('fill', 'currentColor');
              svg.appendChild(rect);
            }
          }
        }
        currentX += (charWidth + charSpacing) * pixelSize;
      }
    },
  },
});
</script>

<style scoped>
.shiny-figlet-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #1A202C;
  border-radius: 8px;
  color: #90CDF4; /* Default color for the figlet text */
}

.shiny-figlet-svg {
  /* SVG will scale based on its viewBox and parent container */
  max-width: 100%;
  height: auto;
}
</style>