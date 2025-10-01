Vue.component('v-chakra-table-component', {
    props: ['data'],
    template: `
    <div :id="divId" class="chakra-table-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px; overflow-x: auto;">
        <h3 class="chakra-table-title" style="color: #CBD5E0; margin-bottom: 10px;">{{ data.title || 'Chakra Table' }}</h3>
        <table class="chakra-table" style="width: 100%; border-collapse: collapse;">
            <thead v-if="data.rows && data.rows.length > 0">
                <tr>
                    <th v-for="(h, index) in data.header" 
                        :key="index" 
                        @click="sort(index)"
                        style="padding: 8px 12px; border-bottom: 2px solid #4A5568; text-align: left; color: #CBD5E0; background-color: #2D3748;"
                        :style="data.sortable ? 'cursor: pointer;' : ''">
                        {{ h }}
                        <span v-if="data.sortable && data.sortColumn === index">{{ data.sortDirection === 'asc' ? ' ▲' : ' ▼' }}</span>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-if="!data.rows || data.rows.length === 0">
                    <td :colspan="data.header ? data.header.length : 1" style="text-align: center; color: #A0AEC0; padding: 8px;">{{ data.noDataFound || 'No data found.' }}</td>
                </tr>
                <tr v-for="(row, rowIndex) in data.rows" :key="rowIndex" :style="{ backgroundColor: rowIndex % 2 === 0 ? '#2D3748' : '#1A202C' }">
                    <td v-for="(cell, cellIndex) in row" :key="cellIndex" style="padding: 8px 12px; border-bottom: 1px solid #4A5568; color: #E2E8F0;">{{ cell }}</td>
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
