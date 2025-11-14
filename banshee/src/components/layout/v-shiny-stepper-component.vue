<template>
    <v-stepper v-model="currentStep">
        <v-stepper-header>
            <template v-for="(step, index) in data.steps" :key="index">
                <v-stepper-item :title="step.title" :subtitle="step.subtitle" :value="index + 1" :complete="currentStep > index + 1"></v-stepper-item>
                <v-divider v-if="index < data.steps.length - 1"></v-divider>
            </template>
        </v-stepper-header>

        <v-stepper-window>
            <v-stepper-window-item v-for="(step, index) in data.steps" :key="index" :value="index + 1">
                <component :is="shinyRegistry.resolve(step.content.shinyType)" :data="step.content"></component>
            </v-stepper-window-item>
        </v-stepper-window>

        <v-stepper-actions>
            <template #prev>
                <v-btn @click="currentStep--" :disabled="currentStep === 1">
                    Back
                </v-btn>
            </template>
            <template #next>
                <v-btn @click="currentStep++" :disabled="currentStep === data.steps.length">
                    Next
                </v-btn>
            </template>
        </v-stepper-actions>
    </v-stepper>
</template>

<script setup lang="ts">
import { ref, inject } from 'vue';
import { ShinyStepper } from '../../models/layout/stepper/ShinyStepper';
import { ShinyRegistry } from '../ShinyRegistry';

const props = defineProps<{
    data: ShinyStepper
}>()

const shinyRegistry = inject<ShinyRegistry>('shinyRegistry')!;
const currentStep = ref(1);
</script>
