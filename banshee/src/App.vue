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
          <div :class="`chat-message ${message.role}-message`"
               @mouseover="hoveredMessageId = message.id"
               @mouseleave="hoveredMessageId = null">
            <div class="message-content-wrapper" :ref="el => message.id && (messageRefs[message.id] = el)">
              <component :is="shinyRegistry.resolve(message.component.shinyType)" 
              :data="message.component"></component>
              <button v-if="hoveredMessageId === message.id" 
                      @click="toggleFullscreen(message.id)" 
                      class="fullscreen-button">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-maximize">
                  <path d="M8 3H5a2 2 0 0 0-2 2v3m18 0V5a2 2 0 0 0-2-2h-3m0 18h3a2 2 0 0 0 2-2v-3m-18 0v3a2 2 0 0 0 2 2h3"></path>
                </svg>
              </button>
            </div>
          </div>
        </template>
      </div>
    </main>

    <form class="prompt-container" @submit.prevent="submitPrompt">
      <input type="text" v-model="prompt" placeholder="Écrivez un message...">
      <VSpeechToTextButton @transcript="handleTranscript" />
      <button type="submit">Envoyer</button>
    </form>
  </v-app>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick, provide } from 'vue';
import VAppStatusComponent from './components/core/VAppStatusComponent.vue';
import { BansheeRole } from './models/core/BansheeRole';
import { BansheeMessage } from './models/core/BansheeMessage';
import { BansheeStory } from './models/core/BansheeStory';
import { ShinyParagraph } from './models/text/paragraph/ShinyParagraph';
import { ShinyModel } from './models/ShinyModel';

import { ShinyRegistry } from './models/core/ShinyRegistry';

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

const shinyRegistry = new ShinyRegistry();
provide('shinyRegistry', shinyRegistry);

const handleTranscript = (transcript: string) => {
  prompt.value = transcript;
};

const submitPrompt = () => {
  if (prompt.value.trim()) {
    if ("clear" === prompt.value) {
      story.clear();
    } else {
      const component: ShinyParagraph = { type:'ShinyParagraph', text : prompt.value };
      addMessage(BansheeRole.USER, component);
      window.ws?.send(prompt.value);
      isLoading.value = true; // Start loading animation
    }
    prompt.value = '';
  }
};

const handleIncomingEvent = (event: MessageEvent) => {
  isLoading.value = false; // Stop loading animation
  try {
    const parsed = JSON.parse(event.data);
  } catch (error) {
    console.error('Error parsing message:', error);
    //il faut absolument mettre le type après sinon on récupère le type préesnt de dtaa s'il existe
    const component: ShinyModel  ={  ...event.data, type: 'ShinyError' }
    addMessage(BansheeRole.SYSTEM, component);
  }
};

const addMessage = (role: BansheeRole, model: ShinyModel ) => {
  const message = new BansheeMessage(role, model);
  story.pushMessage(message);
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
      window.ws.onmessage = handleIncomingEvent;
      clearInterval(checkWsInterval);
    }
  }, 100);
});

const messageRefs = ref({}); // To store references to message content wrappers
const hoveredMessageId = ref<string | null>(null);

const toggleFullscreen = (messageId: string) => {
  const element = messageRefs.value[messageId];
  if (element) {
    if (document.fullscreenElement) {
      document.exitFullscreen();
      // Dispatch event when exiting fullscreen
      const event = new CustomEvent('fullscreen-toggled', { detail: { messageId, isFullscreen: false } });
      window.dispatchEvent(event);
    } else {
      element.requestFullscreen().then(() => {
        // Dispatch event when entering fullscreen
        const event = new CustomEvent('fullscreen-toggled', { detail: { messageId, isFullscreen: true } });
        window.dispatchEvent(event);
      }).catch((err: Error) => {
        console.error(`Error attempting to enable full-screen mode: ${err.message} (${err.name})`);
      });
    }
  }
};
</script>

<style scoped>

/* Animation of dot when waiting response */
.loading-dot {
    width: 12px;
    aspect-ratio: 1;
    border-radius: 50%;
    background: var(--yellow-accent);
    clip-path: inset(-220%);
    animation: none; /* Default to no animation */
    display: inline-block;
    margin-left: 10px;
    vertical-align: middle;
}

.loading-dot.active {
    animation: l28 2s infinite linear;
}

@keyframes l28 {
    0%  {box-shadow:0 0 0 0   , 40px 0,-40px 0,0 40px,0 -40px}
    10% {box-shadow:0 0 0 0   , 12px 0,-40px 0,0 40px,0 -40px}
    20% {box-shadow:0 0 0 4px , 0px  0,-40px 0,0 40px,0 -40px}
    30% {box-shadow:0 0 0 4px , 0px  0,-12px 0,0 40px,0 -40px}
    40% {box-shadow:0 0 0 8px , 0px  0,  0px 0,0 40px,0 -40px}
    50% {box-shadow:0 0 0 8px , 0px  0,  0px 0,0 12px,0 -40px}
    60% {box-shadow:0 0 0 12px, 0px  0,  0px 0,0  0px,0 -40px}
    70% {box-shadow:0 0 0 12px, 0px  0,  0px 0,0  0px,0 -12px}
    80% {box-shadow:0 0 0 16px, 0px  0,  0px 0,0  0px,0  0px }
    90%,
    100%{box-shadow:0 0 0 0   , 40px 0,-40px 0,0 40px,0 -40px}
}

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

.message-content-wrapper {
  position: relative;
  /* Ensure content takes full width/height of wrapper */
  width: 100%;
  height: 100%;
}

.fullscreen-button {
  position: absolute;
  top: 8px;
  right: 8px;
  background-color: rgba(0, 0, 0, 0.6);
  border: none;
  border-radius: 4px;
  padding: 4px;
  cursor: pointer;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0; /* Hidden by default */
  transition: opacity 0.2s ease-in-out;
  z-index: 10; /* Ensure it's above content */
}

.chat-message:hover .fullscreen-button {
  opacity: 1; /* Show on hover */
}

.chat-container::-webkit-scrollbar {
	width: 10px;
}

.chat-container::-webkit-scrollbar-track {
	background: var(--general-bg);
	border-radius: 10px;
}

.chat-container::-webkit-scrollbar-thumb {
	background: var(--assistant-bg);
	border-radius: 10px;
	border: 2px solid var(--general-bg);
}

.chat-container::-webkit-scrollbar-thumb:hover {
	background: var(--assistant-accent);
}

.fullscreen-button svg {
  width: 18px;
  height: 18px;
}

.prompt-container {
	display: flex;
	gap: 10px;
	margin-top: 15px;
	max-width: 900px; /* Match chat-container width */
	margin: 15px auto 0 auto; /* Center it and add top margin */
}

.prompt-container input {
	flex: 1;
	padding: 12px 15px; /* Increased padding */
	border-radius: 8px;
	border: 1px solid var(--assistant-bg);
	background: var(--general-surface);
	color: var(--general-text);
	font-size: 18px; /* Increased font size */
}

.prompt-container button {
	padding: 10px 18px;
	border: none;
	border-radius: 8px;
	background: var(--assistant-bg);
	color: var(--general-text);
	cursor: pointer;
	font-weight: bold;
	transition: background 0.2s ease;
}

.prompt-container button:hover {
	background: var(--yellow-accent);
}
</style>