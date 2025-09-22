class ProgressBarComponent extends LiveComponent {
	constructor({ id, title, value, total }) {
		super({ id });
		this.title = title || 'Progression';
		this.value = value || 0;
		this.total = total || 100;
		if (!this.id) {
			throw new Error("ProgressBar data must have an id.");
		}
	}

	toHtml() {
		const percentage = Math.min((this.value / this.total) * 100, 100);
		return `<div class="progress-container" id="${this.id}"><div class="progress-bar" style="width: ${percentage}%"></div><div class="progress-text">${Math.round(percentage)}%</div></div>`;
	}

	update(value) {
		this.value = value;
		const container = document.getElementById(this.id);
		if (!container) return;
		const newPercentage = Math.min((this.value / this.total) * 100, 100);
		const bar = container.querySelector('.progress-bar');
		const text = container.querySelector('.progress-text');
		if (bar && text) {
			bar.style.width = `${newPercentage}%`;
			text.textContent = `${Math.round(newPercentage)}%`;
			bar.classList.toggle('completed', newPercentage >= 100);
		}
	}

	complete() {
		this.update(this.total);
	}
}
