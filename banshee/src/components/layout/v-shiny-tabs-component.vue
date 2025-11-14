<template>
    <v-card>
        <v-tabs v-model="activeTab" bg-color="primary">
            <v-tab v-for="(tab, index) in data.tabs" :key="index" :value="index">
                {{ tab.title }}
            </v-tab>
        </v-tabs>
        <v-window v-model="activeTab">
            <v-window-item v-for="(tab, index) in data.tabs" :key="index" :value="index">
                <v-card-text>
                    <component :is="shinyRegistry.resolve(tab.content.shinyType)" :data="tab.content"></component>
                </v-card-text>
            </v-window-item>
        </v-window>
    </v-card>
</template>

<script setup lang="ts">
import { ref, inject } from 'vue';
import { ShinyTabs } from '../../models/layout/tabs/ShinyTabs';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
    data: ShinyTabs
}>()

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry')!;
const activeTab = ref(0);
</script>
