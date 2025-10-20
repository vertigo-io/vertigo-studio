<template>
  <v-container fluid class="board-container">
    <h2 class="board-title">{{ data.name }}</h2>
    <p class="board-description">{{ data.description }}</p>

    <div class="board-lists-wrapper">
      <draggable
        v-model="lists"
        group="lists"
        item-key="id"
        class="board-lists"
        handle=".list-handle"
      >
        <template #item="{ element: list }">
          <v-card class="board-list ma-2" :style="{ backgroundColor: list.color || '#424242' }">
            <v-card-title class="list-handle d-flex justify-space-between align-center">
              <span>{{ list.name }}</span>
              <v-icon size="small">mdi-drag</v-icon>
            </v-card-title>
            <v-card-text class="list-cards-container">
              <draggable
                v-model="list.cards"
                group="cards"
                item-key="id"
                class="list-cards"
              >
                <template #item="{ element: card }">
                  <v-card class="board-card pa-2 mb-2">
                    <v-card-title class="card-title">{{ card.title }}</v-card-title>
                    <v-card-text class="card-description">{{ card.description }}</v-card-text>
                  </v-card>
                </template>
              </draggable>
            </v-card-text>
          </v-card>
        </template>
      </draggable>
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { ref, watch, onMounted, onUnmounted } from 'vue';
import draggable from 'vuedraggable';
import { ShinyBoard } from '../../../models/data/board/ShinyBoard';
import { ShinyBoardList } from '../../../models/data/board/ShinyBoardList';

const props = defineProps<{
  data: ShinyBoard;
}>();

const lists = ref<ShinyBoardList[]>(props.data.lists);
const isFullscreen = ref(false);

watch(() => props.data.lists, (newLists) => {
  lists.value = newLists;
});

const handleFullscreenChange = () => {
  isFullscreen.value = !!document.fullscreenElement;
};

onMounted(() => {
  document.addEventListener('fullscreenchange', handleFullscreenChange);
});

onUnmounted(() => {
  document.removeEventListener('fullscreenchange', handleFullscreenChange);
});
</script>

<style scoped>
.board-container {
  background-color: #1a1a1a; /* Dark background */
  min-height: calc(100vh - 64px); /* Adjust based on your app's header/footer */
  padding: 16px;
  display: flex;
  flex-direction: column;
}

.board-title {
  color: white;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 8px;
}

.board-description {
  color: rgba(255, 255, 255, 0.8);
  font-size: 16px;
  margin-bottom: 16px;
}

.board-lists-wrapper {
  flex-grow: 1;
  overflow-x: auto; /* Enable horizontal scrolling */
  padding-bottom: 16px; /* Space for scrollbar */
}

.board-lists {
  display: flex;
  flex-direction: row; /* Default horizontal layout */
  align-items: flex-start;
  gap: 16px;
  height: 100%; /* Occupy full height of wrapper */
}

.board-list {
  flex: 0 0 300px; /* Fixed width for lists */
  max-height: 100%; /* Occupy full height of wrapper */
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  overflow: hidden;
  color: white; /* Text color for list title */
}

.list-handle {
  cursor: grab;
  padding: 12px;
  font-weight: bold;
}

.list-cards-container {
  flex-grow: 1;
  overflow-y: auto;
  padding: 8px;
}

.list-cards {
  min-height: 50px; /* Ensure draggable area is visible */
}

.board-card {
  background-color: #333333; /* Darker card background */
  border-radius: 4px;
  box-shadow: 0 1px 0 rgba(9, 30, 66, 0.25);
  cursor: pointer;
  color: white; /* Text color for card */
}

.card-title {
  font-size: 16px;
  font-weight: 500;
  padding-bottom: 4px;
}

.card-description {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7); /* Lighter text for description */
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .board-lists {
    flex-direction: column; /* Stack lists vertically on small screens */
    align-items: stretch; /* Stretch to full width */
    overflow-x: hidden; /* Disable horizontal scroll in vertical layout */
  }

  .board-list {
    flex: 0 0 auto; /* Allow lists to take natural height */
    width: 100%; /* Full width */
    max-height: none; /* Remove max-height constraint */
    margin-bottom: 16px; /* Add space between vertical lists */
  }
}

/* Fullscreen specific styles */
:fullscreen .board-lists-wrapper {
  overflow-x: auto; /* Ensure horizontal scroll in fullscreen */
}

:fullscreen .board-lists {
  flex-wrap: nowrap; /* Prevent wrapping in fullscreen */
}

:fullscreen .board-list {
  flex-shrink: 0; /* Prevent shrinking in fullscreen */
}
</style>