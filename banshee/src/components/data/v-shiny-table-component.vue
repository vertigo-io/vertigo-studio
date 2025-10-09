<template>
  <div class="collapsible-content">
    <div class="table-title">
      <div class="table-title-content">
        <svg class="table-icon" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 20h.01"/><path d="M17.5 20h.01"/><path d="M4.5 20h.01"/><path d="M20 15.5v.01"/><path d="M20 10.5v.01"/><path d="M20 5.5v.01"/><path d="M15.5 4v.01"/><path d="M10.5 4v.01"/><path d="M5.5 4v.01"/><path d="M4 20v-5.5"/><path d="M20 4v5.5"/><path d="M4 4h5.5"/><path d="M14.5 20H9a.5.5 0 0 0-.5.5v0A.5.5 0 0 0 9 21h1.5"/><path d="M20 20h-5.5"/><path d="M4 14.5V9a.5.5 0 0 1 .5-.5h0A.5.5 0 0 1 5 9v1.5"/></svg>
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

export default defineComponent({
  name: 'VShinyTableComponent',
  props: {
    data: {
      type: Object,
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