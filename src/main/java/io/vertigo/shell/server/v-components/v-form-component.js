Vue.component('v-form-component', {
    props: {
        data: {
            type: Object,
            required: true,
            validator: function (value) {
                if (typeof value.title !== 'string') {
                    console.warn('Prop "data.title" should be a string.');
                    return false;
                }
                if (!Array.isArray(value.sections) || !value.sections.every(section =>
                    typeof section === 'object' &&
                    typeof section.title === 'string' &&
                    Array.isArray(section.fields) &&
                    section.fields.every(field =>
                        typeof field === 'object' &&
                        typeof field.name === 'string' &&
                        typeof field.label === 'string' &&
                        typeof field.type === 'string'
                    )
                )) {
                    console.warn('Prop "data.sections" is not valid.');
                    return false;
                }
                return true;
            }
        }
    },
    template: `
    <v-card class="form-card" max-width="800" dark>
        <v-card-title class="text-h5 font-weight-bold primary--text">{{ data.title }}</v-card-title>
        <v-card-text class="pa-2">
            <v-expansion-panels flat multiple>
                <v-expansion-panel
                    v-for="(section, sectionIndex) in data.sections"
                    :key="sectionIndex"
                    class="form-section-panel mb-2"
                    :value="section.initiallyCollapsed ? null : 0"
                >
                    <v-expansion-panel-header class="text-h6 font-weight-medium white--text">{{ section.title }}</v-expansion-panel-header>
                    <v-expansion-panel-content class="pa-2">
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
                                        hide-details
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
                                        hide-details
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
                                        hide-details
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
                                        hide-details
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
                                        hide-details
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
    <style scoped>
    .form-card {
        margin-top: 1em;
        background-color: #2D3748 !important; /* Override Vuetify default */
    }
    .form-section-panel {
        background-color: #1A202C !important; /* Override Vuetify default */
    }
    </style>
    `,
    methods: {
        getFieldRules(field) {
            const rules = [];
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
        }
    }
});