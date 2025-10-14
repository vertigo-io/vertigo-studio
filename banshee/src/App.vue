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
            <component v-if="message.component" :is="registry.resolveComponent(message.component.type)" :data="message.component"></component>
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
const registry= new ShinyRegistry();

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
    if (parsed.type) {
      // Assuming parsed.data contains the ShinyComponent data directly
      story.pushMessage(BansheeMessage.fromComponent(BansheeRole.ASSISTANT, { type: parsed.type, ...parsed.data }));
    } else {
      story.pushMessage(BansheeMessage.fromContent(BansheeRole.ASSISTANT, event.data));
    }
  } catch (error) {
    console.error('Error parsing message:', error);
    story.pushMessage(BansheeMessage.fromContent(BansheeRole.SYSTEM, event.data));
  }
};

const addMessage = (role: BansheeRole, content?: string, component?: ShinyComponent) => {
  if (content) {
    story.pushMessage(BansheeMessage.fromContent(role, content));
  } else if (component) {
    story.pushMessage(BansheeMessage.fromComponent(role, component));
  }
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