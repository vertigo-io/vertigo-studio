// ========================================================================
// Application Logic
// ========================================================================

const CONFIG = {
	WEBSOCKET_URL: 'ws://localhost:8080',
}

// Initialize WebSocket Manager
const wsManager = new WebSocketManager(
	CONFIG.WEBSOCKET_URL,
	document.getElementById("status"),
	document.getElementById("status-text")
);

const chat = document.getElementById("chat");
const liveMap = new Map();

// Set up message handler
wsManager.onMessageHandler = (event) => {
	try {
		const parsed = JSON.parse(event.data);
		print(parsed);
	} catch (error) {
		alert(`Erreur : ${error.message}`);
		addMessage(event.data, "response");
	}
};

//parsed = {type , data}
function print(parsed) {
	console.log("print>>" + JSON.stringify(parsed));
	const componentMap = {
		//---core
		//			container: ContainerComponent,
		error: ErrorComponent,
		//---data
		json: ChakraJsonComponent,
		list: ListComponent,
		table: ChakraTableComponent,
		tree: ChakraTreeComponent,
		//---dataviz
		barChart: ChakraBarChartComponent,
		gauge: GaugeComponent,
		progressBar: ProgressBarComponent,
		rating: RatingComponent,
		sparkLine: SparkLineComponent,
		chakraPieChart: ChakraPieChartComponent,
		chakraDonutChart: ChakraDonutChartComponent,
		chakraAreaChart: ChakraAreaChartComponent,
		status: StatusComponent,
		//---text
		figlet: FigletComponent,
		paragraph: ChakraParagraphComponent,
		textPath: TextPathComponent,
		title: ChakraTitleComponent,
		
		//---media
		chakraCard: ChakraCardComponent,
		chakraVideo: ChakraVideoComponent,
		geoMap: GeoMapComponent,
		photo: PhotoComponent,
		rssFeed: RssComponent, // Ajout du nouveau composant
		sankey: SankeyComponent,
		/*spotify: SpotifyComponent,*/
		youtube: YouTubeComponent,
	};

	if (componentMap[parsed.type]) {
		const component = new componentMap[parsed.type](parsed.data);
		
		addCollapsible(parsed.type, component.title, component.toHtml());
		setTimeout(() => component.activate(), 0);
		if (component instanceof LiveComponent) {
			liveMap.set(component.id, component);
		}
	} else if (parsed.type === 'live') {
		//Update a component that inherits LiveComponent
		const liveData = parsed.data;
		const liveComponent = liveMap.get(liveData.id);
		if (liveComponent) {
			if (liveData.action === "update") {
				liveComponent.update(liveData.value);
			} else if (liveData.action === "complete") {
				liveComponent.complete();
				liveMap.delete(liveData.id);
			}
		}
	} else if (parsed.type === 'container') {
		subComponents = [];
		for (const componentParsed of parsed.data.children) {
			if (componentMap[componentParsed.type]) {
				const component = new componentMap[componentParsed.type](componentParsed.data);
				subComponents.push(component);
			}
		}
		const container = new ContainerComponent(parsed.data.title, subComponents);

		addCollapsible(parsed.type, container.title, container.toHtml());
		//On active les composants du container
		this.subComponents.forEach(subComponent => {
			setTimeout(() => subComponent.activate(), 0);
			if (subComponent instanceof LiveComponent) {
				liveMap.set(subComponent.id, subComponent);
			}
		});
	} else {
		addMessage(JSON.stringify(parsed), 'response');
	}
}

function sendMessage() {
	const input = document.getElementById("prompt");
	const message = input.value.trim();
	if (message) {
		addMessage(message, "user");
		try {
			wsManager.sendMessage(message);
			input.value = "";
		} catch (error) {
			alert(`Erreur d'envoi : ${error.message}`);
		}
	}
}

function addMessage(text, type) {
	const div = document.createElement("div");
	div.className = `chat-message ${type === "user" ? "user-message" : "response-message"}`;
	div.textContent = text;
	chat.appendChild(div);
	chat.scrollTop = chat.scrollHeight;
}

function toggleCollapse(element) {
	const content = element.nextElementSibling;
	const container = element.parentElement;

	if (content.classList.contains('collapsed')) {
		content.classList.remove('collapsed');
		container.classList.remove('collapsed');
	} else {
		content.classList.add('collapsed');
		container.classList.add('collapsed');
	}
}

function getDataTypeIcon(type) {
	const iconMap = {
		//---core
		'error': 'alert-triangle',
		//---data
		'json': 'code-2',
		'list': 'list',
		'table': 'table-2',
		'tree': 'folder-tree',
		//---dataviz
		'barChart': 'chart-bar',
		'chakraSparkLine': 'trending-up',
		'chakraPieChart': 'pie-chart',
		'chakraDonutChart': 'pie-chart',
		'chakraAreaChart': 'area-chart',
		'gauge': 'gauge',
		'sparkLine': 'trending-up',
		//---live
		'progressBar': 'rabbit',
		//---text
		//---media
		'chakraCard': 'credit-card',
		'geoMap': 'map-pin',
		'rssFeed': 'rss',
		'sankey': 'waves',
	};
	return iconMap[type] || 'traffic-cone';
}

function addCollapsible(type, title, content) {
	const iconName = getDataTypeIcon(type);

	const html = `<div class="table-title" onclick="toggleCollapse(this)">
          	<div class="table-title-content">
            		<i data-lucide="${iconName}" class="table-icon"></i>
           	 	${title}
          	</div>
          	<i data-lucide="chevron-down" class="collapse-icon"></i>
        		</div>
			<div class="collapsible-content">${content}</div>`;

	const div = document.createElement("div");
	div.className = "chat-message response-message";
	div.innerHTML = html;
	chat.appendChild(div);

	lucide.createIcons({
		nameAttr: 'data-lucide'
	});

	chat.scrollTop = chat.scrollHeight;
}

document.addEventListener('DOMContentLoaded', function() {
	lucide.createIcons();
	const promptForm = document.getElementById('prompt-form');
	promptForm.addEventListener('submit', (event) => {
		event.preventDefault();
		sendMessage();
	});

	// Connect WebSocket when DOM is loaded
	wsManager.connect();
});
