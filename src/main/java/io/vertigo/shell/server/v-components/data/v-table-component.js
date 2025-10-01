Vue.component('v-table-component', {
    props: ['data'], 
    template: `
    <div>
        <h3>{{ data.title || 'Table' }}</h3>
        <table>
            <thead>
                <tr>
                    <th v-for="(h, index) in data.header" :key="index">{{ h }}</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(row, rowIndex) in data.rows" :key="rowIndex">
                    <td v-for="(cell, cellIndex) in row" :key="cellIndex">{{ cell }}</td>
                </tr>
            </tbody>
        </table>
    </div>
    `
});