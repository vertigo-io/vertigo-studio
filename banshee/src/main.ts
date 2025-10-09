import { createApp } from 'vue';
import App from './App.vue';

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import '@mdi/font/css/materialdesignicons.css';

const app = createApp(App);

const vuetify = createVuetify({
  components,
  directives,
})
app.use(vuetify);

// Import and register all Shiny components automatically
const shinyComponents = import.meta.glob('./components/**/*.vue', { eager: true });

for (const path in shinyComponents) {
    const componentName = path.split('/').pop().replace(/\.\w+$/, '');
    const componentModule = shinyComponents[path];
    if (componentModule && typeof componentModule === 'object' && 'default' in componentModule) {
        app.component(componentName, componentModule.default);
    }
}

app.mount('#app');
