<template>
  <v-app>
    <div class="header-container">
      <h1>
        BAN<span class="yellow">SHEE</span>
        <span class="loading-dot" :class="{ 'active': isLoading }"></span>
      </h1>
      <div id="status" :class="statusClass">
        <div class="status-dot"></div>
        <span id="status-text">{{ statusText }}</span>
      </div>
    </div>

    <main>
      <div class="chat-container" id="chat">
        <div v-for="message in messages" :key="message.id" :class="message.cssClass">
          <component v-if="message.isComponent" :is="message.content" :data="message.data"></component>
          <div v-else>{{ message.content }}</div>
        </div>
      </div>
    </main>

    <form class="prompt-container" @submit.prevent="sendMessage">
      <input type="text" v-model="prompt" placeholder="Écrivez un message...">
      <button type="button" class="microphone-button" @click="toggleSpeechToText">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-mic"><path d="M12 2a3 3 0 0 0-3 3v7a3 3 0 0 0 6 0V5a3 3 0 0 0-3-3Z"/><path d="M19 10v2a7 7 0 0 1-14 0v-2"/><line x1="12" x2="12" y1="19" y2="22"/></svg>
      </button>
      <button type="submit">Envoyer</button>
    </form>
  </v-app>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue';

// Declare global types if not already defined
declare global {
  interface Window {
    webkitSpeechRecognition: any;
  }
}

interface Message {
  content: string;
  cssClass: string;
  id: string;
  isComponent: boolean;
  data: any;
}

const statusClass = ref('status-disconnected');
const statusText = ref('Connecting');
const messages = reactive<Message[]>([]);
const prompt = ref('');
let ws: WebSocket | null = null;
const isListening = ref(false);
let recognition: any = null;
const isLoading = ref(false);

const componentMap: { [key: string]: string } = {
  // ---core
  container: 'v-shiny-container-component',
  error: 'v-shiny-error-component',
  form: 'v-shiny-form-component',
  // ---data
  json: 'v-shiny-json-component',
  list: 'v-shiny-list-component',
  table: 'v-shiny-table-component',
  tree: 'v-shiny-tree-component',
  timeline: 'v-shiny-timeline-component',
  chip: 'v-shiny-chip-component',
  organization: 'v-shiny-organization-component',
  // ---dataviz
  barChart: 'v-shiny-bar-chart-component',
  radar: 'v-shiny-radar-chart-component',
  pieChart: 'v-shiny-pie-chart-component',
  donutChart: 'v-shiny-donut-chart-component',
  areaChart: 'v-shiny-area-chart-component',
  lineChart: 'v-shiny-line-chart-component',

  progressBar: 'v-shiny-progress-bar-component',
  rating: 'v-shiny-rating-component',
  sparkLine: 'v-shiny-spark-line-component',
  status: 'v-shiny-status-component',
  // ---text
  figlet: 'v-shiny-figlet-component',
  paragraph: 'v-shiny-paragraph-component',
  textPath: 'v-shiny-text-path-component',
  title: 'v-shiny-title-component',
  // ---media
  chakraVideo: 'v-shiny-video-component', // Renamed from chakraVideo
  geoMap: 'v-shiny-geo-map-component',
  photo: 'v-shiny-photo-component',
  rss: 'v-shiny-rss-component',
  sankey: 'v-shiny-sankey-component',
  youtube: 'v-shiny-youtube-component',
  pdf: 'v-shiny-pdf-component',
  card: 'v-shiny-card-component',
  // ---input
  slider: 'v-shiny-slider-component',
  // ---feedback
  alert: 'v-shiny-alert-component',


};

const toggleSpeechToText = () => {
  if (!('webkitSpeechRecognition' in window)) {
    alert('Speech recognition not supported in this browser. Please use Chrome.');
    return;
  }

  if (!recognition) {
    recognition = new window.webkitSpeechRecognition();
    recognition.continuous = false;
    recognition.interimResults = false;
    recognition.lang = 'fr-FR'; // Set language to French

    recognition.onstart = () => {
      isListening.value = true;
      console.log('Speech recognition started');
    };

    recognition.onresult = (event: any) => {
      const transcript = Array.from(event.results)
        .map((result: any) => result[0].transcript)
        .join('');
      prompt.value = transcript;
      console.log('Speech recognition result:', transcript);
    };

    recognition.onerror = (event: any) => {
      console.error('Speech recognition error:', event.error);
      isListening.value = false;
    };

    recognition.onend = () => {
      isListening.value = false;
      console.log('Speech recognition ended');
    };
  }

  if (isListening.value) {
    recognition.stop();
  } else {
    recognition.start();
  }
};

const connectWebSocket = () => {
  ws = new WebSocket('ws://localhost:8080');

  ws.onopen = () => {
    statusClass.value = 'status-connected';
    statusText.value = 'Connected';
  };

  ws.onclose = () => {
    statusClass.value = 'status-disconnected';
    statusText.value = 'Disconnected';
  };

  ws.onerror = (error: Event) => {
    statusClass.value = 'status-error';
    statusText.value = 'Error';
    console.error('WebSocket Error:', error);
  };

  ws.onmessage = handleIncomingMessage;
};

const sendMessage = () => {
  if (prompt.value.trim()) {
    if ("clear" === prompt.value) {
      clearMessages();
    } else {
      addMessage(prompt.value, 'user-message');
      ws?.send(prompt.value);
      isLoading.value = true; // Start loading animation
    }
    prompt.value = '';
  }
};

const handleIncomingMessage = (event: MessageEvent) => {
  isLoading.value = false; // Stop loading animation
  try {
    const parsed = JSON.parse(event.data);
    if (componentMap[parsed.type]) {
      const componentName = componentMap[parsed.type];
      addMessage(componentName, 'response-message', parsed.id, true, parsed.data);
    } else {
      addMessage(event.data, 'response-message');
    }
  } catch (error) {
    console.error('Error parsing message:', error);
    addMessage(event.data, 'response-message');
  }
};

const clearMessages = () => {
  messages.splice(0); // Clear reactive array
};

/*const updateMessage = (id: string, data: any) => {
  const message = messages.find(msg => msg.id === id);
  if (message) {
    message.data = { ...message.data, ...data };
  } else {
    console.warn(`Message with id ${id} not found`);
  }
};*/

const addMessage = (content: string, cssClass: string, id: string = undefined, isComponent = false, data = {}) => {
  messages.push({
    content: content,
    cssClass: `chat-message ${cssClass}`,
    id: id,
    isComponent: isComponent,
    data: data,
  });
  nextTick(() => {
    const chat = document.getElementById('chat');
    if (chat) {
      chat.scrollTop = chat.scrollHeight;
    }
  });
};

onMounted(() => {
  connectWebSocket();
});
</script>

<style scoped>

</style>