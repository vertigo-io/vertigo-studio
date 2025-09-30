new Vue({
    el: '#app',
    data: {
        statusClass: 'status-disconnected',
        statusText: 'Connecting',
        messages: [],
        prompt: '',
        ws: null,
        componentMap: {
            // ---data
            json: 'v-chakra-json-component',
            list: 'v-chakra-list-component',
            chakraTable: 'v-chakra-table-component',
            table: 'v-table-component',
            tree: 'v-chakra-tree-component',
            // ---dataviz
            barChart: 'v-chakra-bar-chart-component',
            gauge: 'v-gauge-component',
            progressBar: 'v-progress-bar-component',
            rating: 'v-rating-component',
            sparkLine: 'v-spark-line-component',
            chakraPieChart: 'v-chakra-pie-chart-component',
            chakraDonutChart: 'v-chakra-donut-chart-component',
            chakraAreaChart: 'v-chakra-area-chart-component',
            status: 'v-status-component',
            // ---text
            figlet: 'v-figlet-component',
            paragraph: 'v-chakra-paragraph-component',
            textPath: 'v-text-path-component',
            title: 'v-chakra-title-component',
            // ---media
            chakraCard: 'v-chakra-card-component',
            chakraVideo: 'v-chakra-video-component',
            geoMap: 'v-geo-map-component',
            photo: 'v-photo-component',
            rssFeed: 'v-rss-component',
            sankey: 'v-sankey-component',
            youtube: 'v-youtube-component',
        }
    },
    methods: {
        connectWebSocket() {
            this.ws = new WebSocket('ws://localhost:8080');

            this.ws.onopen = () => {
                this.statusClass = 'status-connected';
                this.statusText = 'Connected';
            };

            this.ws.onclose = () => {
                this.statusClass = 'status-disconnected';
                this.statusText = 'Disconnected';
            };

            this.ws.onerror = (error) => {
                this.statusClass = 'status-error';
                this.statusText = 'Error';
                console.error('WebSocket Error:', error);
            };

            this.ws.onmessage = this.handleIncomingMessage;
        },
        sendMessage() {
            if (this.prompt.trim()) {
                this.addMessage(this.prompt, 'user-message');
                this.ws.send(this.prompt);
                this.prompt = '';
            }
        },
        handleIncomingMessage(event) {
            try {
                const parsed = JSON.parse(event.data);
				if (parsed.action==="update") {
					const component = new componentMap[parsed.type](parsed.data);
					const div = document.getElementById(parsed.id);
					div.innerHTML = component.toHtml();
				}	
                if (this.componentMap[parsed.type]) {
                    const componentName = this.componentMap[parsed.type];
                    // We will need to implement the component loading and rendering
                    // For now, let's just display the component type
                    this.addMessage(componentName, 'response-message', true, parsed.data);
                } else {
                    this.addMessage(event.data, 'response-message');
                }
            } catch (error) {
                console.error('Error parsing message:', error);
                this.addMessage(event.data, 'response-message');
            }
        },
        addMessage(content, cssClass, isComponent = false, data = {}) {
            this.messages.push({ 
                content: content, 
                cssClass: `chat-message ${cssClass}`,
                isComponent: isComponent,
                data: data
            });
            this.$nextTick(() => {
                const chat = document.getElementById('chat');
                chat.scrollTop = chat.scrollHeight;
            });
        }
    },
    created() {
        this.connectWebSocket();
    }
});
