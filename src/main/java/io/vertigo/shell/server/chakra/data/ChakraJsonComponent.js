class ChakraJsonComponent extends Component {
    constructor({ title, json }) {
        super();
        this.title = title || 'Chakra JSON';
        this.json = json || '{}';
        this.divId = `chakra-json-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        // Chakra-inspired styling for dark theme
        return `<div id="${this.divId}" class="chakra-json-container" style="background-color: #1A202C; padding: 15px; border-radius: 8px; font-family: monospace; font-size: 0.9em;">
			<h3 class="chakra-json-title" style="color: #CBD5E0; margin-bottom: 10px;">
				${this.title}
			</h3>
            <pre class="chakra-json-content" style="white-space: pre-wrap; word-wrap: break-word; color: #A0AEC0;">
			${this.buildContent()}		
			</pre>
		</div>`;
    }

    buildContent() {
 //       const container = document.getElementById(this.divId);
//        const preElement = container.querySelector('.chakra-json-content');
        try {
            const obj = JSON.parse(this.json);
            const formattedJson = JSON.stringify(obj, null, 2);

            // Apply syntax highlighting inspired by Chakra colors
            const highlightedJson = formattedJson
                .replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;') // HTML escape
                .replace(/"(\w+)":/g, '<span style="color: #90CDF4;">"$1"</span>:') // Keys (labelColor)
                .replace(/"([^"]*)"/g, '<span style="color: #F6AD55;">"$1"</span>') // Strings (stringColor)
                .replace(/\b(\d+(\.\d+)?)\b/g, '<span style="color: #68D391;">$1</span>') // Numbers (numberColor)
                .replace(/\b(true|false)\b/g, '<span style="color: #805AD5;">$1"</span>') // Booleans (booleanColor)
                .replace(/\b(null)\b/g, '<span style="color: #E53E3E;">$1"</span>'); // Null (nullColor)

            return highlightedJson;

        } catch (e) {
            return  `Error parsing JSON: ${e.message}\n${this.json}`;
//            preElement.style.color = 'red';
        }
    }
}