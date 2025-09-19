class WebSocketManager {
	constructor(url, statusElement, statusTextElement) {
		this.url = url;
		this.statusElement = statusElement;
		this.statusTextElement = statusTextElement;
		this.socket = null;
		this.onMessageHandler = () => { };

		document.addEventListener('visibilitychange', () => {
			if (document.visibilityState === 'visible') {
				this.tryReconnect();
			}
		});
	}

	connect() {
		console.log("Attempting to connect to WebSocket...");
		this.socket = new WebSocket(this.url);
		this.socket.onopen = () => this.onOpen();
		this.socket.onclose = () => this.onClose();
		this.socket.onerror = (error) => this.onError(error);
		this.socket.onmessage = (event) => this.onMessageHandler(event);
	}

	onOpen() {
		this.statusElement.className = 'status-connected';
		this.statusTextElement.textContent = 'Connected';
	}

	onClose() {
		console.log("WebSocket connection closed.");
		this.statusElement.className = 'status-disconnected';
		this.statusTextElement.textContent = 'Disconnected';
	}

	onError(error) {
		console.error("WebSocket error:", error);
		this.statusElement.className = 'status-error';
		this.statusTextElement.textContent = 'Error';
	}

	sendMessage(message) {
		if (this.socket && this.socket.readyState === WebSocket.OPEN) {
			this.socket.send(message);
		} else {
			throw new Error("Cannot send message, WebSocket is not open.");
		}
	}
}