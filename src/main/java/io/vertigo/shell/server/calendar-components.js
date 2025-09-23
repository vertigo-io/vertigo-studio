class CalendarComponent extends Component {
    constructor({ year, month }) {
        super();
        this.year = year;
        this.month = month;
        this.divId = `calendar-${Math.random().toString(36).substr(2, 9)}`;
    }

    toHtml() {
        return `<div id="${this.divId}"></div>`;
    }

    activate() {
        const container = document.getElementById(this.divId);
        if (!container) {
            throw new Error(`Container not found for ID: ${this.divId}`);
        }

        new Vue({
            el: `#${this.divId}`,
            template: `<q-date 
                          :default-year-month="defaultYearMonth"
                          readonly
                        />`,
            data: {
                year: this.year,
                month: this.month
            },
            computed: {
                defaultYearMonth() {
                    return `${this.year}/${String(this.month).padStart(2, '0')}`;
                }
            }
        });
    }
}
