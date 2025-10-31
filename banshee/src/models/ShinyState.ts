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

    setValue(key: string, newValue: string): void {
        const prop = this.props.find(p => p.key === key);
        if (prop) {
            prop.value = newValue;
        } else {
            this.props.push({ key: key, value: newValue });
        }
    }

    getIntValue(key: string): number | null {
        const value = this.getValue(key);
        if (value) {
            return parseInt(value, 10);
        }
        return null;
    }

    setIntValue(key: string, newValue: number): void {
        this.setValue(key, newValue.toString());
    }

    getBooleanValue(key: string): boolean | null {
        const value = this.getValue(key);
        if (value) {
            return value.toLowerCase() === 'true';
        }
        return null;
    }

    setBooleanValue(key: string, newValue: boolean): void {
        this.setValue(key, newValue.toString());
    }
}

