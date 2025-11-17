<template>
    <v-card>
        <v-card-title v-if="data.title">{{ data.title }}</v-card-title>
        <v-data-table
            :headers="headers"
            :items="data.data"
            :search="search"
        >
            <template v-slot:top>
                <v-text-field
                    v-model="search"
                    label="Search"
                    class="pa-4"
                ></v-text-field>
            </template>
        </v-data-table>
    </v-card>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue';
import { ShinyDataGrid } from '../../models/data/datagrid/ShinyDataGrid';

const props = defineProps<{
    data: ShinyDataGrid
}>()

const search = ref('');

const headers = computed(() => {
    return props.data.columns.map(col => ({
        title: col.header,
        key: col.field,
        sortable: col.sortable,
    }));
});

</script>
