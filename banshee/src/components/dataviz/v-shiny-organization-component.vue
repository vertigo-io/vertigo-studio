<template>
  <div class="pa-4" style="height: 600px;">
    <div ref="chartContainer" class="chart-container"></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, onBeforeUnmount } from 'vue';
import { OrgChart } from 'd3-org-chart';
import type { ShinyOrganization } from '@/models/dataviz/organization/ShinyOrganization';

const props = defineProps<{
  data: ShinyOrganization
}>();

const chartContainer = ref<HTMLDivElement | null>(null);
let chart: OrgChart | null = null;

onMounted(() => {
  if (chartContainer.value && props.data.nodes) {
    chart = new OrgChart();
    chart
      .container(chartContainer.value)
      .data(props.data.nodes)
      .nodeId(d => d.id)
      .parentNodeId(d => d.parentId)
      .nodeContent(d => {
        return `
          <div class="node-container">
            <img src="${d.data.imageUrl}" class="node-image">
            <div class="node-info">
              <div class="node-name">${d.data.name}</div>
              <div class="node-position">${d.data.position}</div>
            </div>
          </div>
        `;
      })
      .render();
  }
});

watch(() => props.data.nodes, (newNodes) => {
  if (chart) {
    chart.data(newNodes).render();
  }
}, { deep: true });

onBeforeUnmount(() => {
  // Clean up chart if necessary, although d3-org-chart might not have a specific destroy method
  if (chart) {
    // chart.destroy(); // If a destroy method exists
  }
});

</script>

<style>
/* Add some basic styling for the org chart nodes */
.chart-container {
  width: 100%;
  height: 100%;
}

.node-container {
  display: flex;
  align-items: center;
  padding: 5px;
  border-radius: 5px;
  background-color: #f0f0f0;
  border: 1px solid #ccc;
}

.node-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
  object-fit: cover;
}

.node-info {
  display: flex;
  flex-direction: column;
}

.node-name {
  font-weight: bold;
}

.node-position {
  font-size: 0.9em;
  color: #555;
}
</style>
