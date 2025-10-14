<template>
  <v-app>
    <div class="header-container">
      <h1>
        BAN<span class="yellow">SHEE</span>
        <span class="loading-dot" :class="{ 'active': isLoading }"></span>
      </h1>
      <VAppStatusComponent />
    </div>

    <main>
      <div class="chat-container" id="chat">
        <template v-for="message in story.messages" :key="message.id">
          <div :class="message.cssClass">
            <component v-if="message.component" :is="resolveComponent(message.component.type)" :data="message.component"></component>
            <div v-else-if="message.content">{{ message.content }}</div>
          </div>
        </template>
      </div>
    </main>

    <form class="prompt-container" @submit.prevent="sendMessage">
      <input type="text" v-model="prompt" placeholder="Écrivez un message...">
      <VSpeechToTextButton @transcript="handleTranscript" />
      <button type="submit">Envoyer</button>
    </form>
  </v-app>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue';
import VAppStatusComponent from './components/core/VAppStatusComponent.vue';
import VSpeechToTextButton from './components/input/VSpeechToTextButton.vue';
import { BansheeRole } from './models/core/BansheeRole';
import { BansheeMessage } from './models/core/BansheeMessage';
import { BansheeStory } from './models/core/BansheeStory';
import { ShinyComponent } from './models/ShinyComponent';

// Import all Shiny components for dynamic rendering
// Core
import VShinyContainerComponent from './components/core/v-shiny-container-component.vue';
import VShinyErrorComponent from './components/core/v-shiny-error-component.vue';
// Data
import VShinyCardComponent from './components/data/v-shiny-card-component.vue';
import VShinyChipComponent from './components/data/v-shiny-chip-component.vue';
import VShinyFormComponent from './components/data/v-shiny-form-component.vue';
import VShinyJsonComponent from './components/data/v-shiny-json-component.vue';
import VShinyListComponent from './components/data/v-shiny-list-component.vue';
import VShinyOrganizationComponent from './components/data/v-shiny-organization-component.vue';
import VShinyTableComponent from './components/data/v-shiny-table-component.vue';
import VShinyTimelineComponent from './components/data/v-shiny-timeline-component.vue';
import VShinyTreeComponent from './components/data/v-shiny-tree-component.vue';
// Dataviz
import VShinyAreaChartComponent from './components/dataviz/v-shiny-area-chart-component.vue';
import VShinyBarChartComponent from './components/dataviz/v-shiny-bar-chart-component.vue';
import VShinyDonutChartComponent from './components/dataviz/v-shiny-donut-chart-component.vue';
import VShinyGeoMapComponent from './components/dataviz/v-shiny-geo-map-component.vue';
import VShinyLineChartComponent from './components/dataviz/v-shiny-line-chart-component.vue';
import VShinyPieChartComponent from './components/dataviz/v-shiny-pie-chart-component.vue';
import VShinyRadarChartComponent from './components/dataviz/v-shiny-radar-chart-component.vue';
import VShinyRadar2Component from './components/dataviz/v-shiny-radar2-component.vue';
import VShinyRatingComponent from './components/dataviz/v-shiny-rating-component.vue';
import VShinySankeyComponent from './components/dataviz/v-shiny-sankey-component.vue';
import VShinySparkLineComponent from './components/dataviz/v-shiny-spark-line-component.vue';
import VShinyStatusComponent from './components/dataviz/v-shiny-status-component.vue';
import VShinyFlowComponent from './components/dataviz/v-shiny-flow-component.vue';
// Feedback
import VShinyAlertComponent from './components/feedback/v-shiny-alert-component.vue';
// Input
import VShinySliderComponent from './components/input/v-shiny-slider-component.vue';
// Live
import VShinyProgressBarComponent from './components/live/v-shiny-progress-bar-component.vue';
// Media
import VShinyPdfComponent from './components/media/v-shiny-pdf-component.vue';
import VShinyPhotoComponent from './components/media/v-shiny-photo-component.vue';
import VShinyRssComponent from './components/media/v-shiny-rss-component.vue';
import VShinyVideoComponent from './components/media/v-shiny-video-component.vue';
import VShinyYoutubeComponent from './components/media/v-shiny-youtube-component.vue';
// Text
import VShinyFigletComponent from './components/text/v-shiny-figlet-component.vue';
import VShinyParagraphComponent from './components/text/v-shiny-paragraph-component.vue';
import VShinyTextPathComponent from './components/text/v-shiny-text-path-component.vue';
import VShinyTitleComponent from './components/text/v-shiny-title-component.vue';

// Declare global types if not already defined
declare global {
  interface Window {
    webkitSpeechRecognition: any;
    ws: WebSocket | null; // Declare global ws
  }
}

const story = reactive(new BansheeStory());
const prompt = ref('');
const isLoading = ref(false);

const componentMap: Record<string, any> = {
  // ---core
  ShinyContainer: VShinyContainerComponent,
  ShinyError: VShinyErrorComponent,
  ShinyForm: VShinyFormComponent,
  // ---data
  ShinyJson: VShinyJsonComponent,
  ShinyList: VShinyListComponent,
  ShinyTable: VShinyTableComponent,
  ShinyTree: VShinyTreeComponent,
  ShinyTimeline: VShinyTimelineComponent,
  ShinyChip: VShinyChipComponent,
  ShinyOrganization: VShinyOrganizationComponent,
  // ---dataviz
  ShinyBarChart: VShinyBarChartComponent,
  ShinyRadarChart: VShinyRadarChartComponent,
  ShinyPieChart: VShinyPieChartComponent,
  ShinyDonutChart: VShinyDonutChartComponent,
  ShinyAreaChart: VShinyAreaChartComponent,
  ShinyLineChart: VShinyLineChartComponent,

  ShinyProgressBar: VShinyProgressBarComponent,
  ShinyRating: VShinyRatingComponent,
  ShinySparkline: VShinySparkLineComponent,
  ShinyStatus: VShinyStatusComponent,
  ShinyFlow: VShinyFlowComponent,
  // ---feedback
  ShinyAlert: VShinyAlertComponent,
  // ---input
  ShinySlider: VShinySliderComponent,
  // ---media
  ShinyPdfComponent: VShinyPdfComponent,
  ShinyPhoto: VShinyPhotoComponent,
  ShinyRss: VShinyRssComponent,
  ShinyVideo: VShinyVideoComponent,
  ShinyYoutube: VShinyYoutubeComponent,
  ShinyGeoMap: VShinyGeoMapComponent,
  ShinyCard: VShinyCardComponent,
  // ---text
  ShinyFiglet: VShinyFigletComponent,
  ShinyParagraph: VShinyParagraphComponent,
  ShinyTextPath: VShinyTextPathComponent,
  ShinyTitle: VShinyTitleComponent,
};

const resolveComponent = (componentType: string) => {
  const component = componentMap[componentType];
  if (!component) {
    console.warn(`Component not found for type: ${componentType}`);
  }
  return component || 'div'; // Fallback to a div or a generic error component
};

const handleTranscript = (transcript: string) => {
  prompt.value = transcript;
};

const sendMessage = () => {
  if (prompt.value.trim()) {
    if ("clear" === prompt.value) {
      story.clear();
    } else {
      addMessage(BansheeRole.USER, prompt.value);
      window.ws?.send(prompt.value);
      isLoading.value = true; // Start loading animation
    }
    prompt.value = '';
  }
};

const handleIncomingMessage = (event: MessageEvent) => {
  isLoading.value = false; // Stop loading animation
  try {
    const parsed = JSON.parse(event.data);
    if (parsed.type && componentMap[parsed.type]) {
      // Assuming parsed.data contains the ShinyComponent data directly
      addMessage(BansheeRole.ASSISTANT, undefined, { type: parsed.type, ...parsed.data });
    } else {
      addMessage(BansheeRole.ASSISTANT, event.data);
    }
  } catch (error) {
    console.error('Error parsing message:', error);
    addMessage(BansheeRole.ASSISTANT, event.data);
  }
};

const addMessage = (role: BansheeRole, content?: string, component?: ShinyComponent) => {
  const cssClass = `chat-message ${role}-message`;
  story.pushMessage({
    id: `msg-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`,
    role,
    content,
    component,
    cssClass,
  });
  nextTick(() => {
    const chat = document.getElementById('chat');
    if (chat) {
      chat.scrollTop = chat.scrollHeight;
    }
  });
};

onMounted(() => {
  // Assign handleIncomingMessage to the global ws.onmessage once ws is available
  // This assumes VAppStatusComponent will set window.ws
  const checkWsInterval = setInterval(() => {
    if (window.ws) {
      window.ws.onmessage = handleIncomingMessage;
      clearInterval(checkWsInterval);
    }
  }, 100);
});
</script>

<style scoped>
.chat-message {
  margin-bottom: 10px;
  padding: 8px 12px;
  border-radius: 8px;
  max-width: 70%;
  word-wrap: break-word;
}

.user-message {
  background-color: var(--user-message-bg);
  color: var(--user-message-text);
  align-self: flex-end;
  margin-left: auto;
}

.assistant-message {
  background-color: var(--assistant-message-bg);
  color: var(--assistant-message-text);
  align-self: flex-start;
  margin-right: auto;
}

.system-message {
  background-color: var(--system-message-bg);
  color: var(--system-message-text);
  align-self: center;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  font-style: italic;
}
</style>