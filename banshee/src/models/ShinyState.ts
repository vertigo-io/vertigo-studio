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

    getIntValue(key: string): number | null {
        const value = this.getValue(key);
        if (value) {
            return parseInt(value, 10);
        }
        return null;
    }

    getBooleanValue(key: string): boolean | null {
        const value = this.getValue(key);
        if (value) {
            return value.toLowerCase() === 'true';
        }
        return null;
    }
}

