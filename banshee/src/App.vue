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
  // ---dataviz
  barChart: 'v-shiny-bar-chart-component',
  radarChart: 'v-shiny-radar-chart-component',
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

const updateMessage = (id: string, data: any) => {
  const message = messages.find(msg => msg.id === id);
  if (message) {
    message.data = { ...message.data, ...data };
  } else {
    console.warn(`Message with id ${id} not found`);
  }
};

const addMessage = (content: string, cssClass: string, id: string, isComponent = false, data = {}) => {
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

<style>
/* Global styles from style.css will go here */
body {
  font-family: 'Orbitron', sans-serif;
  background-color: #1A202C; /* Dark background */
  color: #E2E8F0; /* Light text */
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

#app {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  max-width: 900px;
  margin: 0 auto;
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #4A5568;
  margin-bottom: 20px;
}

h1 {
  font-size: 2.5em;
  color: #E2E8F0;
  margin: 0;
  display: flex;
  align-items: center;
}

h1 .yellow {
  color: #ECC94B; /* Yellow accent */
}

.loading-dot {
  width: 10px;
  height: 10px;
  background-color: #ECC94B;
  border-radius: 50%;
  margin-left: 10px;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
  animation: pulse 1.5s infinite;
}

.loading-dot.active {
  opacity: 1;
}

@keyframes pulse {
  0% {
    transform: scale(0.8);
    opacity: 0.5;
  }
  50% {
    transform: scale(1.2);
    opacity: 1;
  }
  100% {
    transform: scale(0.8);
    opacity: 0.5;
  }
}

#status {
  display: flex;
  align-items: center;
  font-size: 0.9em;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-right: 5px;
}

.status-disconnected .status-dot {
  background-color: #E53E3E; /* Red */
}

.status-connected .status-dot {
  background-color: #38A169; /* Green */
}

.status-error .status-dot {
  background-color: #DD6B20; /* Orange */
}

#status-text {
  color: #CBD5E0;
}

main {
  flex-grow: 1;
  overflow-y: auto;
  padding-bottom: 20px;
}

.chat-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  background-color: #2D3748; /* Darker background for chat */
  border-radius: 8px;
  min-height: 300px;
  max-height: 60vh; /* Limit chat height */
  overflow-y: auto;
  scroll-behavior: smooth;
}

.chat-message {
  padding: 8px 12px;
  border-radius: 5px;
  max-width: 70%;
  word-wrap: break-word;
}

.user-message {
  align-self: flex-end;
  background-color: #4A5568; /* User message background */
  color: #E2E8F0;
}

.response-message {
  align-self: flex-start;
  background-color: #1A202C; /* Response message background */
  color: #CBD5E0;
}

.prompt-container {
  display: flex;
  margin-top: 20px;
  gap: 10px;
}

.prompt-container input {
  flex-grow: 1;
  padding: 10px 15px;
  border-radius: 5px;
  border: 1px solid #4A5568;
  background-color: #2D3748;
  color: #E2E8F0;
  font-size: 1em;
  outline: none;
}

.prompt-container input::placeholder {
  color: #A0AEC0;
}

.prompt-container button {
  background-color: #3182CE; /* Blue button */
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1em;
  transition: background-color 0.2s ease;
}

.prompt-container button:hover {
  background-color: #2B6CB0; /* Darker blue on hover */
}

.microphone-button {
  background-color: #ECC94B !important; /* Yellow for microphone */
  color: #1A202C !important;
  display: flex;
  align-items: center;
  justify-content: center;
}

.microphone-button:hover {
  background-color: #D69E2E !important; /* Darker yellow on hover */
}

.microphone-button svg {
  width: 20px;
  height: 20px;
}
</style>