new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    data: {
        statusClass: 'status-disconnected',
        statusText: 'Connecting',
        messages: [],
        prompt: '',
        ws: null,
        isListening: false, // New data property
        recognition: null, // SpeechRecognition object
        isLoading: false, // New loading indicator
        componentMap: {
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
            gauge: 'v-shiny-gauge-component',
            progressBar: 'v-shiny-progress-bar-component',
            rating: 'v-shiny-rating-component',
            sparkLine: 'v-shiny-spark-line-component',
            status: 'v-shiny-status-component',
            // ---text
            figlet: 'v-shiny-figlet-component',
            paragraph: 'v-shiny-shiny-paragraph-component',
            textPath: 'v-shiny-text-path-component',
            title: 'v-shiny-title-component',
            // ---media
            chakraVideo: 'v-shiny-video-component',
            geoMap: 'v-shiny-geo-map-component',
            photo: 'v-shiny-photo-component',
            rss: 'v-shiny-rss-component',
            sankey: 'v-shiny-sankey-component',
            youtube: 'v-shiny-youtube-component',
			pdf: 'v-shiny-pdf-component',
			card: 'v-shiny-card-component',
        }
    },
    methods: {
        toggleSpeechToText() {
            if (!('webkitSpeechRecognition' in window)) {
                alert('Speech recognition not supported in this browser. Please use Chrome.');
                return;
            }

            if (!this.recognition) {
                this.recognition = new webkitSpeechRecognition();
                this.recognition.continuous = false;
                this.recognition.interimResults = false;
                this.recognition.lang = 'fr-FR'; // Set language to French

                this.recognition.onstart = () => {
                    this.isListening = true;
                    console.log('Speech recognition started');
                };

                this.recognition.onresult = (event) => {
                    const transcript = Array.from(event.results)
                        .map(result => result[0].transcript)
                        .join('');
                    this.prompt = transcript;
                    console.log('Speech recognition result:', transcript);
                };

                this.recognition.onerror = (event) => {
                    console.error('Speech recognition error:', event.error);
                    this.isListening = false;
                };

                this.recognition.onend = () => {
                    this.isListening = false;
                    console.log('Speech recognition ended');
                };
            }

            if (this.isListening) {
                this.recognition.stop();
            } else {
                this.recognition.start();
            }
        },
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
				if ("clear"=== this.prompt){
					this.clearMessages();
				} else {
                		this.addMessage(this.prompt, 'user-message');
                		this.ws.send(this.prompt);
                        this.isLoading = true; // Start loading animation
				}
                this.prompt = '';
            }
        },
        handleIncomingMessage(event) {
            this.isLoading = false; // Stop loading animation
            try {
                const parsed = JSON.parse(event.data);
				if (this.componentMap[parsed.type]) {
                    const componentName = this.componentMap[parsed.type];
                    // We will need to implement the component loading and rendering
                    // For now, let's just display the component type
                    this.addMessage(componentName, 'response-message', parsed.id, true, parsed.data);
                } else {
                    this.addMessage(event.data, 'response-message');
                }
            } catch (error) {
                console.error('Error parsing message:', error);
                this.addMessage(event.data, 'response-message');
            }
        },
		clearMessages() {
		    this.messages = [];
		},
		updateMessage(id, data) {
		    const message = this.messages.find(msg => msg.id === id);
		    if (message) {
		        message.data = { ...message.data, ...data };
		    } else {
		        console.warn(`Message with id ${id} not found`);
		    }
		},
		addMessage(content, cssClass, id, isComponent = false, data = {}) {
            this.messages.push({ 
                content: content, 
                cssClass: `chat-message ${cssClass}`,
				id : id, 
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
