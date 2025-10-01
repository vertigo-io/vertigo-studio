class RatingComponent extends Component {
    constructor({ label, value, scale, customMaxValue, showValue, showPercentage, showBox, separator, allowHalfRating }) {
        super();
        this.label = label || '';
        this.value = value || 0;
        this.scale = scale || 'SCALE_5';
        this.customMaxValue = customMaxValue || -1;
        this.showValue = showValue || false;
        this.showPercentage = showPercentage || false;
        this.showBox = showBox || false;
        this.separator = separator || '/';
        this.allowHalfRating = allowHalfRating || false;
        this.divId = `rating-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        let containerClasses = "rating-container";
        if (this.showBox) {
            containerClasses += " rating-box";
        }
        return `<div id="${this.divId}" class="${containerClasses}">
                    <span class="rating-label">${this.label}</span>
                    <div class="rating-stars"></div>
                    <span class="rating-value"></span>
                </div>`;
    }

    activate() {
        const container = document.getElementById(this.divId);
        const starsContainer = container.querySelector('.rating-stars');
        const valueSpan = container.querySelector('.rating-value');

        const filledColor = 'gold';
        const emptyColor = 'lightgray';
        const filledIcon = '★';
        const emptyIcon = '☆';
        const halfIcon = '½'; // This is not in the original enum, but useful for half ratings

        let maxValue;
        if (this.customMaxValue !== -1) {
            maxValue = this.customMaxValue;
        } else {
            switch (this.scale) {
                case 'SCALE_10':
                    maxValue = 10;
                    break;
                case 'SCALE_100':
                    maxValue = 100;
                    break;
                default:
                    maxValue = 5;
            }
        }

        starsContainer.innerHTML = '';
        for (let i = 1; i <= maxValue; i++) {
            const star = document.createElement('span');
            if (i <= this.value) {
                star.textContent = filledIcon;
                star.style.color = filledColor;
            } else if (this.allowHalfRating && i - 0.5 === this.value) {
                // This is a simplification. A real half-star implementation is more complex.
                // For now, we just show a character.
                star.textContent = halfIcon;
                star.style.color = filledColor;
            } else {
                star.textContent = emptyIcon;
                star.style.color = emptyColor;
            }
            starsContainer.appendChild(star);
        }

        if (this.showValue) {
            let valueText = this.value;
            if (this.showPercentage) {
                valueText += '%';
            }
            valueSpan.textContent = ` ${this.separator} ${valueText}`;
        }
    }
}