class ParagraphComponent extends Component {
    constructor({ title, text }) {
        super();
        this.title = title || 'Paragraph';
        this.text = text || '';
    }

    toHtml() {
        return `<p class="paragraph-text">${this.text}</p>`;
    }
}

class TitleComponent extends Component {
    constructor({ title, level, text }) {
        super();
        this.title = title || 'Title';
        this.level = level || 2; // Default to h2
        this.text = text || '';
    }

    toHtml() {
        const tag = `h${Math.min(Math.max(this.level, 1), 6)}`; // Ensure h1 to h6
        return `<${tag} class="title-text">${this.text}</${tag}>`;
    }
}

class FigletComponent extends Component {
    constructor({ text, style }) {
        super();
        this.text = text || '';
        this.style = style;
        this.svgId = `figlet-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<svg id="${this.svgId}" class="figlet-svg"></svg>`;
    }

    activate() {
        const svg = document.getElementById(this.svgId);
        if (!svg) {
            throw new Error(`SVG element not found for ID: ${this.svgId}`);
        }

        const charSet = {
            'A': [[1, 1, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1], [1, 0, 1]],
            'B': [[1, 1, 0], [1, 0, 1], [1, 1, 0], [1, 0, 1], [1, 1, 0]],
            'C': [[1, 1, 1], [1, 0, 0], [1, 0, 0], [1, 0, 0], [1, 1, 1]],
            'D': [[1, 1, 0], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 0]],
            'E': [[1, 1, 1], [1, 0, 0], [1, 1, 0], [1, 0, 0], [1, 1, 1]],
            'F': [[1, 1, 1], [1, 0, 0], [1, 1, 0], [1, 0, 0], [1, 0, 0]],
            'G': [[1, 1, 1], [1, 0, 0], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
            'H': [[1, 0, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1], [1, 0, 1]],
            'I': [[1, 1, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0], [1, 1, 1]],
            'J': [[0, 1, 1], [0, 0, 1], [0, 0, 1], [1, 0, 1], [1, 1, 0]],
            'K': [[1, 0, 1], [1, 0, 0], [1, 1, 0], [1, 0, 0], [1, 0, 1]],
            'L': [[1, 0, 0], [1, 0, 0], [1, 0, 0], [1, 0, 0], [1, 1, 1]],
            'M': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1]],
            'N': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1]],
            'O': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
            'P': [[1, 1, 0], [1, 0, 1], [1, 1, 0], [1, 0, 0], [1, 0, 0]],
            'Q': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1], [0, 0, 1]],
            'R': [[1, 1, 0], [1, 0, 1], [1, 1, 0], [1, 0, 1], [1, 0, 1]],
            'S': [[1, 1, 1], [1, 0, 0], [1, 1, 1], [0, 0, 1], [1, 1, 1]],
            'T': [[1, 1, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0], [0, 1, 0]],
            'U': [[1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
            'V': [[1, 0, 1], [1, 0, 1], [1, 0, 1], [0, 1, 0], [0, 1, 0]],
            'W': [[1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1]],
            'X': [[1, 0, 1], [1, 0, 1], [0, 1, 0], [1, 0, 1], [1, 0, 1]],
            'Y': [[1, 0, 1], [1, 0, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0]],
            'Z': [[1, 1, 1], [0, 0, 1], [0, 1, 0], [1, 0, 0], [1, 1, 1]],
            ' ': [[0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0]],
            '0': [[1, 1, 1], [1, 0, 1], [1, 0, 1], [1, 0, 1], [1, 1, 1]],
            '1': [[0, 1, 0], [1, 1, 0], [0, 1, 0], [0, 1, 0], [1, 1, 1]],
            '2': [[1, 1, 0], [0, 0, 1], [0, 1, 0], [1, 0, 0], [1, 1, 1]],
            '3': [[1, 1, 1], [0, 0, 1], [0, 1, 1], [0, 0, 1], [1, 1, 1]],
            '4': [[1, 0, 1], [1, 0, 1], [1, 1, 1], [0, 0, 1], [0, 0, 1]],
            '5': [[1, 1, 1], [1, 0, 0], [1, 1, 0], [0, 0, 1], [1, 1, 0]],
            '6': [[1, 1, 1], [1, 0, 0], [1, 1, 1], [1, 0, 1], [1, 1, 1]],
            '7': [[1, 1, 1], [0, 0, 1], [0, 1, 0], [0, 1, 0], [0, 1, 0]],
            '8': [[1, 1, 1], [1, 0, 1], [1, 1, 1], [1, 0, 1], [1, 1, 1]],
            '9': [[1, 1, 1], [1, 0, 1], [1, 1, 1], [0, 0, 1], [1, 1, 1]]
        };

        const charWidth = 3;
        const charHeight = 5;
        const pixelSize = 4;
        const charSpacing = 1;
        const text = this.text.toUpperCase();

        let svgWidth = 0;
        if (text.length > 0) {
            svgWidth = (text.length * (charWidth + charSpacing) - charSpacing) * pixelSize;
        }
        const svgHeight = charHeight * pixelSize;

        svg.setAttribute('width', svgWidth);
        svg.setAttribute('height', svgHeight);
        svg.setAttribute('viewBox', `0 0 ${svgWidth} ${svgHeight}`);

        let currentX = 0;
        for (let i = 0; i < text.length; i++) {
            const char = text[i];
            const charMatrix = charSet[char] || charSet[' '];

            for (let y = 0; y < charMatrix.length; y++) {
                for (let x = 0; x < charMatrix[y].length; x++) {
                    if (charMatrix[y][x] === 1) {
                        const rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
                        rect.setAttribute('x', currentX + x * pixelSize);
                        rect.setAttribute('y', y * pixelSize);
                        rect.setAttribute('width', pixelSize);
                        rect.setAttribute('height', pixelSize);
                        rect.setAttribute('fill', 'currentColor');
                        svg.appendChild(rect);
                    }
                }
            }
            currentX += (charWidth + charSpacing) * pixelSize;
        }
    }
}

class TextPathComponent extends Component {
    constructor({ path, separator }) {
        super();
        this.path = path || '';
        this.separator = separator || '/';
        this.divId = `textpath-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.divId}" class="text-path"></div>`;
    }

    activate() {
        const container = document.getElementById(this.divId);
        if (!container) {
            throw new Error(`Container not found for ID: ${this.divId}`);
        }

        const rootColor = 'green';
        const nodeColor = 'yellow';
        const leafColor = '#87CEEB'; // skyblue
        const separatorColor = 'red';

        const parts = this.path.split(this.separator);
        parts.forEach((part, index) => {
            const partSpan = document.createElement('span');
            partSpan.textContent = part;

            if (index === 0) {
                partSpan.style.color = rootColor;
            } else if (index === parts.length - 1) {
                partSpan.style.color = leafColor;
            } else {
                partSpan.style.color = nodeColor;
            }
            container.appendChild(partSpan);

            if (index < parts.length - 1) {
                const separatorSpan = document.createElement('span');
                separatorSpan.textContent = this.separator;
                separatorSpan.style.color = separatorColor;
                container.appendChild(separatorSpan);
            }
        });
    }
}