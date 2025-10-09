<template>
  <div class="shiny-table-container">
    <h3 class="shiny-component-title">{{ data.title || 'Shiny Table' }}</h3>
    <table class="shiny-table">
      <thead>
        <tr v-if="data.rows && data.rows.length > 0">
          <th
            v-for="(h, index) in data.header"
            :key="index"
            @click="sort(index)"
            class="shiny-table-th"
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
        <tr v-for="(row, rowIndex) in data.rows" :key="rowIndex" :style="{ backgroundColor: rowIndex % 2 === 0 ? '#2D3748' : '#1A202C' }">
          <td v-for="(cell, cellIndex) in row" :key="cellIndex" class="shiny-table-td">{{ cell }}</td>
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
  data() {
    return {
      divId: `shiny-table-${Math.random().toString(36).substr(2, 9)}`,
    };
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
      (this as any).$root.ws.send(JSON.stringify(message));
    },
  },
});
</script>

<style scoped>
/* Add your component-specific styles here */
.shiny-table-container {
  background-color: #1A202C;
  padding: 15px;
  border-radius: 8px;
  color: #CBD5E0;
}

.shiny-component-title {
  color: #E2E8F0;
  margin-bottom: 10px;
}

.shiny-table {
  width: 100%;
  border-collapse: collapse;
}

.shiny-table-th {
  padding: 8px;
  border-bottom: 1px solid #4A5568;
  text-align: left;
  color: #90CDF4;
}

.shiny-table-td {
  padding: 8px;
  border-bottom: 1px solid #4A5568;
}

.shiny-table-no-data {
  text-align: center;
  font-style: italic;
  color: #A0AEC0;
}
</style>