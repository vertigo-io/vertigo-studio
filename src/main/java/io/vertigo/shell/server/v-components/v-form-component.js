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
        <v-card-text>
            <v-expansion-panels flat multiple>
                <v-expansion-panel v-for="(section, sectionIndex) in data.sections" :key="sectionIndex" class="form-section-panel mb-4">
                    <v-expansion-panel-header class="text-h6 font-weight-medium white--text">{{ section.title }}</v-expansion-panel-header>
                    <v-expansion-panel-content>
                        <v-row dense>
                            <v-col cols="12" sm="6" md="4" v-for="(field, fieldIndex) in section.fields" :key="fieldIndex">
                                <template v-if="field.type === 'STRING'">
                                    <v-text-field
                                        :label="field.label"
                                        :value="field.value"
                                        readonly
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
                                        readonly
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
                                        readonly
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
                                        readonly
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
});