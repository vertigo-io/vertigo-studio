<template>
  <v-card class="form-card" dark>
    <v-card-title 
      class="text-h5 font-weight-bold form-title" 
      :style="{ borderBottom: `1px solid var(--yellow-accent)`, paddingBottom: '8px' }"
    >
      {{ data.title }}
    </v-card-title>
    <v-card-text class="pa-0">
      <v-expansion-panels flat multiple v-model="openPanels">
        <v-expansion-panel
          v-for="(section, sectionIndex) in data.sections"
          :key="sectionIndex"
          class="form-section-panel"
        >
          <v-expansion-panel-header 
            class="text-h6 font-weight-medium white--text"
          >
            {{ section.title }}
          </v-expansion-panel-header>
          <v-expansion-panel-content class="pa-4">
            <v-row dense>
              <v-col 
                cols="12" 
                sm="6" 
                md="4" 
                v-for="(field, fieldIndex) in section.fields" 
                :key="fieldIndex"
              >
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
                    type="date"
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
                    dark
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
                    :rules="getFieldRules(field)"
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
                    :rules="getFieldRules(field)"
                    dark
                    color="primary"
                  >
                    <v-radio
                      v-for="(option, optionIndex) in field.options"
                      :key="optionIndex"
                      :label="option.label"
                      :value="option.value"
                      dark
                    ></v-radio>
                  </v-radio-group>
                </template>
                
                <template v-else-if="field.type === 'CHECKBOX_GROUP'">
                  <v-container fluid class="pa-0">
                    <v-subheader class="white--text px-0">{{ field.label }}</v-subheader>
                    <v-checkbox
                      v-for="(option, optionIndex) in field.options"
                      :key="optionIndex"
                      :label="option.label"
                      :input-value="field.value && field.value.includes(option.value)"
                      :readonly="field.readOnly"
                      :required="field.required"
                      dark
                      color="primary"
                      dense
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
                    rows="3"
                  ></v-textarea>
                </template>
                
                <div 
                  v-if="field.helpText" 
                  class="text-caption grey--text text--lighten-1 mt-n2"
                >
                  {{ field.helpText }}
                </div>
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
}>();

const openPanels = ref(props.data.sections.map((_: any, index: number) => index));

const getFieldRules = (field: ShinyFormField): ((value: any) => boolean | string)[] => {
  const rules: ((value: any) => boolean | string)[] = [];
  
  if (field.required) {
    rules.push(v => !!v || 'This field is required');
  }
  
 if (field.validator) {
    const validator = field.validator;
    
    if (validator.minLength !== undefined) {
      rules.push(v => (!v || v.length >= validator.minLength!) 
        || `Min length is ${validator.minLength}`);
    }
    
    if (validator.maxLength !== undefined) {
      rules.push(v => (!v || v.length <= validator.maxLength!) 
        || `Max length is ${validator.maxLength}`);
    }
    
    if (validator.minValue !== undefined && (field.type === 'NUMBER' || field.type === 'DATE')) {
      rules.push(v => (!v || v >= validator.minValue!) 
        || `Min value is ${validator.minValue}`);
    }
    
    if (validator.maxValue !== undefined && (field.type === 'NUMBER' || field.type === 'DATE')) {
      rules.push(v => (!v || v <= validator.maxValue!) 
        || `Max value is ${validator.maxValue}`);
    }
    
    if (validator.pattern) {
      const regex = new RegExp(validator.pattern);
      rules.push(v => (!v || regex.test(v)) 
        || 'Invalid format');
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
  margin-bottom: 8px !important;
}

.form-title {
  color: var(--yellow-accent) !important;
}

/* Amélioration de l'affichage des panels */
.v-expansion-panel-content >>> .v-expansion-panel-content__wrap {
  padding: 0 !important;
}
</style>