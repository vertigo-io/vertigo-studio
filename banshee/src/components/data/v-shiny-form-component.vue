<template>
  <v-card class="form-card" dark>
    <v-card-title class="text-h5 font-weight-bold form-title" :style="{ borderBottom: `1px solid var(--yellow-accent)`, paddingBottom: '0px' }">{{ data.title }}</v-card-title>
    <v-card-text style="padding: 0px !important;">
      <v-expansion-panels flat multiple v-model="openPanels">
        <v-expansion-panel
          v-for="(section, sectionIndex) in data.sections"
          :key="sectionIndex"
          class="form-section-panel"
          style="margin-bottom: 0px !important;"
        >
          <v-expansion-panel-header class="text-h6 font-weight-medium white--text" style="padding-left: 16px !important; padding: 0 !important;">{{ section.title }}</v-expansion-panel-header>
          <v-expansion-panel-content class="pa-2" style="padding: 0 !important;">
            <v-row dense>
              <v-col cols="12" sm="6" md="4" v-for="(field, fieldIndex) in section.fields" :key="fieldIndex">
                <!-- Fields remain the same, Vuetify's 'dark' prop and theme should handle most styling -->
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
// Script content remains the same
import { defineComponent } from 'vue';

interface ShinyFormField {
  name: string;
  label: string;
  type: string;
  value: any;
  readOnly: boolean;
  required: boolean;
  placeholder?: string;
  rules?: any[];
  minLength?: number;
  maxLength?: number;
  minValue?: number;
  maxValue?: number;
  pattern?: string;
  options?: { label: string; value: any }[];
  helpText?: string;
}

interface ShinyFormSection {
  title: string;
  fields: ShinyFormField[];
  initiallyCollapsed?: boolean;
}

interface ShinyFormData {
  title: string;
  sections: ShinyFormSection[];
}

export default defineComponent({
  name: 'VShinyFormComponent',
  props: {
    data: {
      type: Object as () => ShinyFormData,
      required: true,
    },
  },
  data() {
    return {
      openPanels: this.data.sections.map((_: any, index: number) => index) as number[],
    };
  },
  methods: {
    getFieldRules(field: ShinyFormField): ((value: any) => boolean | string)[] {
      const rules: ((value: any) => boolean | string)[] = [];
      if (field.required) {
        rules.push(v => !!v || 'This field is required');
      }
      if (field.minLength !== undefined) {
        rules.push(v => (v && v.length >= field.minLength) || `Min length is ${field.minLength}`);
      }
      if (field.maxLength !== undefined) {
        rules.push(v => (v && v.length <= field.maxLength) || `Max length is ${field.maxLength}`);
      }
      if (field.minValue !== undefined && (field.type === 'NUMBER' || field.type === 'DATE')) {
        rules.push(v => (v >= field.minValue) || `Min value is ${field.minValue}`);
      }
      if (field.maxValue !== undefined && (field.type === 'NUMBER' || field.type === 'DATE')) {
        rules.push(v => (v <= field.maxValue) || `Max value is ${field.maxValue}`);
      }
      if (field.pattern) {
        const regex = new RegExp(field.pattern);
        rules.push(v => regex.test(v) || 'Invalid format');
      }
      return rules;
    },
  },
});
</script>

<style scoped>
.form-card {
  margin-top: 1em;
  background-color: var(--shiny-card-bg) !important;
}
.form-section-panel {
  background-color: var(--general-bg) !important;
}
.form-title {
  color: var(--yellow-accent) !important;
}
</style>