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
          <td v-for="(cell, cellIndex) in row" :key="cellIndex">
            <component :is="resolveCellComponent(cell.shinyType)" :data="cell"></component>
          </td>
        </tr>
      </tbody>
    </table>
    <v-pagination
      v-if="pageProp"
      :length="pageCount"
      v-model="currentPage"
      @update:modelValue="updatePage"
      :total-visible="7"
    ></v-pagination>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, inject } from 'vue';
import { ShinyTable } from '../../models/data/table/ShinyTable';
import { ShinyProp } from '../../models/ShinyProp';
import { ShinyRegistry } from '../../models/core/ShinyRegistry';
import { ShinyTableCell } from '../../models/data/table/cell/ShinyTableCell';

const props = defineProps<{
  data: ShinyTable;
}>();

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry');

const pageProp = computed(() => props.data.props?.find(p => p.key === 'page'));

const currentPage = computed<number>({
  get() {
    return pageProp.value ? parseInt(pageProp.value.value, 10) : 1;
  },
  set(value: number) {
    if (pageProp.value) {
      pageProp.value.value = value.toString();
    }
  }
});

const pageCount = computed<number>(() => {
  const pageCountProp = props.data.props?.find(p => p.key === 'pageCount');
  return pageCountProp ? parseInt(pageCountProp.value, 0) : 0;
});

const sort = (columnIndex: number) => {
  if (!props.data.sortable) {
    return;
  }
  const newSortDirection = props.data.sortDirection === 'asc' ? 'desc' : 'asc';
  const message = {
    type: 'sortShinyTable',
    data: {
      id: props.data.id,
      columnIndex: columnIndex,
      sortDirection: newSortDirection,
    },
  };
  if ((window as any).ws) {
    (window as any).ws.send(JSON.stringify(message));
  } else {
    console.error("WebSocket connection not found on window instance.");
  }
};

const updatePage = () => {
  const bansheeEvent = {
    command : 'table2',
    id: props.data.id,
    props: props.data.props
  };
  if ((window as any).ws) {
    (window as any).ws.send(JSON.stringify(bansheeEvent));
  } else {
    console.error("WebSocket connection not found on window instance.");
  }
};

const resolveCellComponent = (shinyType: string) => {
  return shinyRegistry?.resolve(shinyType);
};
</script>

<style scoped>
/* All styles are now handled by the global style.css */
.shiny-table-no-data {
  text-align: center;
  font-style: italic;
  color: var(--chakra-paragraph-text);
  padding: 1rem;
}

.table-icon {
	width: 16px;
	height: 16px;
	stroke: var(--icon-stroke);
	stroke-width: 2;
	opacity: 0.7;
	flex-shrink: 0;
}
</style>