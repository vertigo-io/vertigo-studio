<template>
  <div class="collapsible-content">
    <div class="table-title">
      <div class="table-title-content">
        <svg class="table-icon" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M12 3v18"/>
        <rect width="18" height="18" x="3" y="3" rx="2"/>
        <path d="M3 9h18"/>
        <path d="M3 15h18"/>
      </svg>
        <span>{{ data.title || 'Table' }}</span>
      </div>
    </div>
    <table class="table">
      <thead>
        <tr v-if="data.rows && data.rows.length > 0">
          <th
            v-for="(h, index) in data.header"
            :key="index"
            @click="sort(index)"
            :style="data.sortable ? 'cursor: pointer;' : ''"
          >
            {{ h }}
            <span v-if="data.sortable && data.sortColumn === index">{{ data.sortDirection === 'asc' ? ' ▲' : ' ▼' }}</span>
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="!data.rows || data.rows.length === 0">
          <td :colspan="data.header ? data.header.length : 1" class="shiny-table-no-data">{{ data.noDataFound || 'No data found.' }}</td>
        </tr>
        <tr v-for="(row, rowIndex) in data.rows" :key="rowIndex">
          <td v-for="(cell, cellIndex) in row" :key="cellIndex">{{ cell }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyTable } from '../../models/data/table/ShinyTable';

export default defineComponent({
  name: 'VShinyTableComponent',
  props: {
    data: {
      type: Object as () => ShinyTable,
      required: true,
    },
  },
  methods: {
    sort(columnIndex: number) {
      if (!this.data.sortable) {
        return;
      }
      const newSortDirection = this.data.sortDirection === 'asc' ? 'desc' : 'asc';
      const message = {
        type: 'sortShinyTable',
        data: {
          id: this.data.id,
          columnIndex: columnIndex,
          sortDirection: newSortDirection,
        },
      };
      // Assuming ws is available from the parent Vue instance
      // This might need adjustment depending on how state is managed (e.g., Pinia, props drilling)
      if ((this as any).$root.ws) {
        (this as any).$root.ws.send(JSON.stringify(message));
      } else {
        console.error("WebSocket connection not found on root instance.");
      }
    },
  },
});
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.shiny-table-no-data {
  text-align: center;
  font-style: italic;
  color: var(--chakra-paragraph-text);
  padding: 1rem;
}
</style>