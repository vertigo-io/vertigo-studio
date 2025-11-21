import { createApp } from 'vue';
import App from './App.vue';

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import * as labsComponents from 'vuetify/labs/components'
import '@mdi/font/css/materialdesignicons.css';

const app = createApp(App);

const vuetify = createVuetify({
  components: {
    ...components,
    ...labsComponents,
  },
  directives,
  theme: {
    defaultTheme: 'dark',
  },
})
app.use(vuetify);

// Import and register all Shiny components automatically
const shinyComponents = import.meta.glob<{ default: any }>('./views/**/*.vue', { eager: true });

for (const path in shinyComponents) {
  const file = path.split('/').pop();
  if (!file) continue; // ← évite l'erreur TS2532

  const componentName = file.replace(/\.\w+$/, '');

  const componentModule = shinyComponents[path];
  if (componentModule?.default) {
    app.component(componentName, componentModule.default);
  }
}

app.mount('#app');
