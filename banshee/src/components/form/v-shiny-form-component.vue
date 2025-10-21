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
                <template v-if="field.type === 'STRING'">
                  <v-text-field
                    :label="field.label"
                    :value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    :placeholder="field.placeholder"
                    :rules="getFieldRules(field)"
                    filled
                    dark
                    dense
                    color="primary"
                  ></v-text-field>
                </template>
                <template v-else-if="field.type === 'NUMBER'">
                  <v-text-field
                    :label="field.label"
                    :value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    :placeholder="field.placeholder"
                    :rules="getFieldRules(field)"
                    filled
                    dark
                    dense
                    type="number"
                    color="primary"
                  ></v-text-field>
                </template>
                <template v-else-if="field.type === 'DATE'">
                  <v-text-field
                    :label="field.label"
                    :value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    :placeholder="field.placeholder"
                    :rules="getFieldRules(field)"
                    filled
                    dark
                    dense
                    color="primary"
                  ></v-text-field>
                </template>
                <template v-else-if="field.type === 'BOOLEAN'">
                  <v-switch
                    :label="field.label"
                    :input-value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    inset
                    color="primary"
                  ></v-switch>
                </template>
                <template v-else-if="field.type === 'IMAGE'">
                  <v-img
                    :src="field.value"
                    :alt="field.label"
                    contain
                    max-height="150"
                    class="mb-2"
                  ></v-img>
                  <div class="text-caption white--text">{{ field.label }}</div>
                </template>
                <template v-else-if="field.type === 'SELECT'">
                  <v-select
                    :label="field.label"
                    :value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    :items="field.options"
                    item-text="label"
                    item-value="value"
                    filled
                    dark
                    dense
                    color="primary"
                  ></v-select>
                </template>
                <template v-else-if="field.type === 'RADIO'">
                  <v-radio-group
                    :label="field.label"
                    :value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    dark
                    dense
                    color="primary"
                  >
                    <v-radio
                      v-for="(option, optionIndex) in field.options"
                      :key="optionIndex"
                      :label="option.label"
                      :value="option.value"
                    ></v-radio>
                  </v-radio-group>
                </template>
                <template v-else-if="field.type === 'CHECKBOX_GROUP'">
                  <v-container fluid class="pa-0">
                    <v-subheader dark>{{ field.label }}</v-subheader>
                    <v-checkbox
                      v-for="(option, optionIndex) in field.options"
                      :key="optionIndex"
                      :label="option.label"
                      :input-value="field.value && field.value.includes(option.value)"
                      :readonly="field.readOnly"
                      :required="field.required"
                      dark
                      dense
                      color="primary"
                    ></v-checkbox>
                  </v-container>
                </template>
                <template v-else-if="field.type === 'TEXTAREA'">
                  <v-textarea
                    :label="field.label"
                    :value="field.value"
                    :readonly="field.readOnly"
                    :required="field.required"
                    :placeholder="field.placeholder"
                    :rules="getFieldRules(field)"
                    filled
                    dark
                    dense
                    color="primary"
                  ></v-textarea>
                </template>
                <div v-if="field.helpText" class="text-caption grey--text text--lighten-1">{{ field.helpText }}</div>
              </v-col>
            </v-row>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-card-text>
  </v-card>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ShinyForm } from '../../models/data/form/ShinyForm';
import { ShinyFormField } from '../../models/data/form/ShinyFormField';

const props = defineProps<{
  data: ShinyForm
}>()

const openPanels = ref(props.data.sections.map((_: any, index: number) => index));

const getFieldRules = (field: ShinyFormField): ((value: any) => boolean | string)[] => {
  const rules: ((value: any) => boolean | string)[] = [];
  if (field.required) {
    rules.push(v => !!v || 'This field is required');
  }
  if (field.validator){
    if (field.validator.minLength !== undefined) {
      rules.push(v => (v && v.length >= field.validator.minLength) || `Min length is ${field.validator.minLength}`);
    }
    if (field.validator.maxLength !== undefined) {
      rules.push(v => (v && v.length <= field.validator.maxLength) || `Max length is ${field.validator.maxLength}`);
    }
    if (field.validator.minValue !== undefined && (field.type === 'NUMBER' || field.type === 'DATE')) {
      rules.push(v => (v >= field.validator.minValue) || `Min value is ${field.validator.minValue}`);
    }
    if (field.validator.maxValue !== undefined && (field.type === 'NUMBER' || field.type === 'DATE')) {
      rules.push(v => (v <= field.validator.maxValue) || `Max value is ${field.validator.maxValue}`);
    }
    if (field.validator.pattern) {
      const regex = new RegExp(field.validator.pattern);
      rules.push(v => regex.test(v) || 'Invalid format');
  }
}
  return rules;
};
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