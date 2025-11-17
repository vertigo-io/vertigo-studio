<template>
  <button type="button" class="microphone-button" @click="toggleSpeechToText">
    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-mic"><path d="M12 2a3 3 0 0 0-3 3v7a3 3 0 0 0 6 0V5a3 3 0 0 0-3-3Z"/><path d="M19 10v2a7 7 0 0 1-14 0v-2"/><line x1="12" x2="12" y1="19" y2="22"/></svg>
  </button>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const emit = defineEmits<{ (e: 'transcript', transcript: string): void }>();

const isListening = ref(false);
let recognition: any = null;

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
      emit('transcript', transcript);
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
</script>

<style scoped>
.microphone-button {
  background-color: #ECC94B !important; /* Yellow for microphone */
  color: #1A202C !important;
  display: flex;
  align-items: center;
  justify-content: center;
}

.microphone-button:hover {
  background-color: #D69E2E !important; 
}

.microphone-button svg {
  width: 20px;
  height: 20px;
}

</style>