Vue.component('v-shiny-rating-component', {
    props: ['data'],
    template: `
    <div :class="containerClasses">
        <span class="rating-label">{{ data.label }}</span>
        <div class="rating-stars" v-html="stars"></div>
        <span class="rating-value">{{ valueText }}</span>
    </div>
    `,
    computed: {
        containerClasses() {
            let classes = "rating-container";
            if (this.data.showBox) {
                classes += " rating-box";
            }
            return classes;
        },
        maxValue() {
            if (this.data.customMaxValue !== -1) {
                return this.data.customMaxValue;
            }
            switch (this.data.scale) {
                case 'SCALE_10':
                    return 10;
                case 'SCALE_100':
                    return 100;
                default:
                    return 5;
            }
        },
        stars() {
            const filledColor = 'gold';
            const emptyColor = 'lightgray';
            const filledIcon = '★';
            const emptyIcon = '☆';
            const halfIcon = '½';
            let starsHtml = '';
            for (let i = 1; i <= this.maxValue; i++) {
                if (i <= this.data.value) {
                    starsHtml += `<span style="color: ${filledColor}">${filledIcon}</span>`;
                } else if (this.data.allowHalfRating && i - 0.5 === this.data.value) {
                    starsHtml += `<span style="color: ${filledColor}">${halfIcon}</span>`;
                } else {
                    starsHtml += `<span style="color: ${emptyColor}">${emptyIcon}</span>`;
                }
            }
            return starsHtml;
        },
        valueText() {
            if (this.data.showValue) {
                let text = this.data.value;
                if (this.data.showPercentage) {
                    text += '%';
                }
                return ` ${this.data.separator || '/'} ${text}`;
            }
            return '';
        }
    }
});