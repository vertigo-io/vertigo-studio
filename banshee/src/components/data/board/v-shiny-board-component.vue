<template>
  <v-container fluid class="board-container">
    <h2 class="board-title">{{ data.name }}</h2>
    <p class="board-description">{{ data.description }}</p>

    <draggable
      v-model="lists"
      group="lists"
      item-key="id"
      class="d-flex flex-row board-lists"
      handle=".list-handle"
    >
      <template #item="{ element: list }">
        <v-card class="board-list ma-2" :style="{ backgroundColor: list.color || '#424242' }">
          <v-card-title class="list-handle d-flex justify-space-between align-center">
            <span>{{ list.name }}</span>
            <v-icon small>mdi-drag</v-icon>
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
  </v-container>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import draggable from 'vuedraggable';
import { ShinyBoard } from '../../../models/data/board/ShinyBoard';
import { ShinyBoardList } from '../../../models/data/board/ShinyBoardList';

const props = defineProps<{
  data: ShinyBoard;
}>();

const lists = ref<ShinyBoardList[]>(props.data.lists);

watch(() => props.data.lists, (newLists) => {
  lists.value = newLists;
});
</script>

<style scoped>
.board-container {
  background-color: #1a1a1a; /* Dark background */
  min-height: calc(100vh - 64px); /* Adjust based on your app's header/footer */
  padding: 16px;
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

.board-lists {
  align-items: flex-start;
  gap: 16px;
}

.board-list {
  flex: 0 0 300px; /* Fixed width for lists */
  max-height: calc(100vh - 120px); /* Adjust to fit screen */
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
  /* background-color: rgba(0, 0, 0, 0.05); */ /* Removed to let list color show */
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
</style>
