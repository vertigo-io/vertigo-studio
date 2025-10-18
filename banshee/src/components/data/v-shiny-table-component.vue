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
    <v-pagination
      v-if="pageProp"
      :length="pageCount"
      v-model="page"
      @update:modelValue="updatePage"
      :total-visible="7"
    ></v-pagination>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { ShinyTable } from '../../models/data/table/ShinyTable';
import { ShinyProps } from '../../models/ShinyProps';

export default defineComponent({
  name: 'VShinyTableComponent',
  props: {
    data: {
      type: Object as () => ShinyTable,
      required: true,
    },
  },
  computed: {
    pageProp(): ShinyProps | undefined {

      console.log("props >> " +this.data.props);
      return this.data.props?.find(p => p.key === 'page');
    },
    page: {
      get(): number {
        return this.pageProp ? parseInt(this.pageProp.value, 10) : 1;
      },
      set(value: number) {
        if (this.pageProp) {
          this.pageProp.value = value.toString();
        }
      }
    },
    rowsPerPage(): number {
      const prop = this.data.props?.find(p => p.key === 'rows-per-page');
      // Default to 10 if not provided, Vuetify default is 10
      return prop ? parseInt(prop.value, 10) : 10;
    },
    count(): number {
      // The user specified that 'count' is available on 'data'
      return (this.data as any).count || 0;
    },
    pageCount(): number {
      if (this.count === 0) {
        return 1;
      }
      return Math.ceil(this.count / this.rowsPerPage);
    }
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
      if ((this as any).$root.ws) {
        (this as any).$root.ws.send(JSON.stringify(message));
      } else {
        console.error("WebSocket connection not found on root instance.");
      }
    },
    updatePage() {
      // The v-model binding for 'page' already updates the prop value.
      // We just need to send the update message.
      const message = {
        type: 'updateShinyComponent',
        data: {
          id: this.data.id,
          type: 'ShinyTable',
          props: this.data.props
        }
      };
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

.table-icon {
	width: 16px;
	height: 16px;
	stroke: var(--icon-stroke);
	stroke-width: 2;
	opacity: 0.7;
	flex-shrink: 0;
}
</style>