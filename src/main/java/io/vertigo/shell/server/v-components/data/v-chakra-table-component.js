Vue.component('v-chakra-table-component', {
    props: ['data'],
    template: `
    <div :id="divId" class="chakra-table-container">
        <h3 class="chakra-component-title">{{ data.title || 'Chakra Table' }}</h3>
        <table class="chakra-table">
            <thead v-if="data.rows && data.rows.length > 0">
                <tr>
                    <th v-for="(h, index) in data.header" 
                        :key="index" 
                        @click="sort(index)"
                        class="chakra-table-th"
                        :style="data.sortable ? 'cursor: pointer;' : ''">
                        {{ h }}
                        <span v-if="data.sortable && data.sortColumn === index">{{ data.sortDirection === 'asc' ? ' ▲' : ' ▼' }}</span>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="!data.rows || data.rows.length === 0">
                    <td :colspan="data.header ? data.header.length : 1" class="chakra-table-no-data">{{ data.noDataFound || 'No data found.' }}</td>
                </tr>
                <tr v-for="(row, rowIndex) in data.rows" :key="rowIndex" :style="{ backgroundColor: rowIndex % 2 === 0 ? '#2D3748' : '#1A202C' }">
                    <td v-for="(cell, cellIndex) in row" :key="cellIndex" class="chakra-table-td">{{ cell }}</td>
                </tr>
            </tbody>
        </table>
    </div>
    `,
    data() {
        return {
            divId: `chakra-table-${Math.random().toString(36).substr(2, 9)}`
        };
    },
    methods: {
        sort(columnIndex) {
            if (!this.data.sortable) {
                return;
            }
            const newSortDirection = this.data.sortDirection === 'asc' ? 'desc' : 'asc';
            const message = {
                type: 'sortChakraTable',
                data: {
                    id: this.data.id,
                    columnIndex: columnIndex,
                    sortDirection: newSortDirection
                }
            };
            // Assuming ws is available from the parent Vue instance
            this.$root.ws.send(JSON.stringify(message));
        }
    }
});
