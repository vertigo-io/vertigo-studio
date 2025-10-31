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
            :style="state?.getBooleanValue('sortable') ? 'cursor: pointer;' : ''"
          >
            {{ h }}
            <span v-if="state?.getBooleanValue('sortable') && state?.getIntValue('sortColumn') === index">{{ state?.getValue('sortDirection') === 'asc' ? ' ▲' : ' ▼' }}</span>
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
      v-if="(state?.getIntValue('pageCount') ?? 0) > 0"
      :length="pageCount"
      v-model="currentPage"
      @update:modelValue="updatePage"
      :total-visible="7"
    ></v-pagination>
  </div>
</template>

<script setup lang="ts">
import { computed, inject } from 'vue';
import { ShinyTable } from '../../models/data/table/ShinyTable';
import { ShinyRegistry } from '../ShinyRegistry';
import { ShinyState } from '../../models/ShinyState';
import { BansheeCommand } from '../../models/core/BansheeCommand';
import { BansheeManager } from '../../models/core/BansheeManager';

//Inject
const shinyRegistry = inject<ShinyRegistry>('shinyRegistry');
const bansheeManager = inject<BansheeManager>('bansheeManager');
if (!shinyRegistry || !bansheeManager) {
  throw new Error('shinyRegistry / bansheeManager must be provided');
}
//--

const props = defineProps<{
  data: ShinyTable;
}>();


const state = computed(() => props.data?.state ? new ShinyState(props.data.state.props) : undefined);

const currentPage = computed<number>({
  get(): number {
    if (state.value) {
      return state.value.getIntValue('page') ?? 1;
    } else {
      return 1;
    }
  },
  set(value: number) {
    if (state.value) {
        const pageProp = state.value.props.find(p => p.key === 'page');
        if (pageProp) {
            pageProp.value = value.toString();
        }
    }
  }
});

const pageCount = computed<number>(() => {
  return state.value ? state.value.getIntValue('pageCount') ?? 0 : 0;
});

const sort = (columnIndex: number) => {
  if (!state.value?.getBooleanValue('sortable')) {
    return;
  }
  const newSortDirection = state.value?.getValue('sortDirection') === 'asc' ? 'desc' : 'asc';
  const command = new BansheeCommand('sort', props.data.id, new ShinyState([
    { key: 'columnIndex', value: columnIndex.toString() },
    { key: 'sortDirection', value: newSortDirection }
  ]));
  bansheeManager.send(command);
};

const updatePage = () => {
  const command = new BansheeCommand('table2', props.data.id, props.data.state);
  bansheeManager.send(command);
};

const resolveCellComponent = (shinyType: string) => {
  return shinyRegistry.resolve(shinyType);
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