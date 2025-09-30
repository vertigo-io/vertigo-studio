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
    <v-card class="mx-auto" max-width="800" style="margin-top: 1em;">
        <v-card-title class="headline">{{ data.title }}</v-card-title>
        <v-card-text>
            <div v-for="(section, sectionIndex) in data.sections" :key="sectionIndex">
                <h4 class="mt-4 mb-2">{{ section.title }}</h4>
                <v-row>
                    <v-col cols="12" sm="6" md="4" v-for="(field, fieldIndex) in section.fields" :key="fieldIndex">
                        <template v-if="field.type === 'STRING'">
                            <v-text-field
                                :label="field.label"
                                :value="field.value"
                                readonly
                                outlined
                            ></v-text-field>
                        </template>
                        <template v-else-if="field.type === 'NUMBER'">
                            <v-text-field
                                :label="field.label"
                                :value="field.value"
                                readonly
                                outlined
                                type="number"
                            ></v-text-field>
                        </template>
                        <template v-else-if="field.type === 'DATE'">
                            <v-text-field
                                :label="field.label"
                                :value="field.value"
                                readonly
                                outlined
                            ></v-text-field>
                        </template>
                        <template v-else-if="field.type === 'BOOLEAN'">
                            <v-checkbox
                                :label="field.label"
                                :input-value="field.value"
                                readonly
                            ></v-checkbox>
                        </template>
                        <template v-else-if="field.type === 'IMAGE'">
                            <v-img
                                :src="field.value"
                                :alt="field.label"
                                contain
                                max-height="150"
                            ></v-img>
                            <div class="text-caption">{{ field.label }}</div>
                        </template>
                    </v-col>
                </v-row>
            </div>
        </v-card-text>
    </v-card>
    `,
});