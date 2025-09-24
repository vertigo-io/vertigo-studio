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