/* All components must inherit Component */
class Component {
	title = "";
	toHtml() {
		throw new Error(`Component.toHtml() must be implemented by subclasses.`);
	}
	activate() {
		// By default, nothing is done
	}
}

class LiveComponent extends Component {
	constructor({ id }) {
		super();
		if (!id) {
			throw new Error("LiveComponent requires an id");
		}
		this.id = id;
	}

	update(data) {
		throw new Error(`LiveComponent.update() must be implemented by subclasses. Component ID: ${this.id}`);
	}

	complete() {
		throw new Error(`LiveComponent.complete() must be implemented by subclasses. Component ID: ${this.id}`);
	}
}

class ContainerComponent extends Component {
	//Attention on le construit de l'extérieur et non à partir de ses données !!
    constructor(title, subComponents) {
        super();
        this.title = title || 'Container';
        this.subComponents = subComponents; //[] arrays of Components
    }

    toHtml() {
        // Generate HTML for all child components
        const subComponentsHtml = this.subComponents.map(comp => comp.toHtml()).join('');
        return `<div class="container-content">${subComponentsHtml}</div>`;
    }
}
