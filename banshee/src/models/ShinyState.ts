import { ShinyProp } from "./ShinyProp";

export class ShinyState {
    props: ShinyProp[];

    constructor(props: ShinyProp[]) {
        this.props = props;
    }

    getValue(key: string): string | null {
        const prop = this.props.find(p => p.key === key);
        if (prop) {
            return prop.value;
        }
        return null;
    }
}

