/**
 * ShinyState is a class that represents the current state of a Shiny model in the Banshee frontend.
 * It manages a collection of `ShinyProp` instances, allowing for dynamic state management
 * of Shiny components.
 */
import { ShinyProp } from "./ShinyProp";

export class ShinyState {
    /**
     * The list of properties that make up the state.
     */
    props: ShinyProp[];

    /**
     * Creates an instance of ShinyState.
     * @param props The initial list of ShinyProp instances.
     */
    constructor(props: ShinyProp[]) {
        this.props = props;
    }

    /**
     * Retrieves the string value of a specific property from the state.
     * @param key The key of the property to retrieve.
     * @returns The string value of the property if found, otherwise null.
     */
    getValue(key: string): string | null {
        const prop = this.props.find(p => p.key === key);
        if (prop) {
            return prop.value;
        }
        return null;
    }

    /**
     * Sets the string value of a specific property in the state.
     * If the property does not exist, it will be added.
     * @param key The key of the property to set.
     * @param newValue The new string value for the property.
     */
    setValue(key: string, newValue: string): void {
        const prop = this.props.find(p => p.key === key);
        if (prop) {
            prop.value = newValue;
        } else {
            this.props.push({ key: key, value: newValue });
        }
    }

    /**
     * Retrieves the integer value of a specific property from the state.
     * @param key The key of the property to retrieve.
     * @returns The integer value of the property if found and valid, otherwise null.
     */
    getIntValue(key: string): number | null {
        const value = this.getValue(key);
        if (value) {
            return parseInt(value, 10);
        }
        return null;
    }

    /**
     * Sets the integer value of a specific property in the state.
     * @param key The key of the property to set.
     * @param newValue The new integer value for the property.
     */
    setIntValue(key: string, newValue: number): void {
        this.setValue(key, newValue.toString());
    }

    /**
     * Retrieves the boolean value of a specific property from the state.
     * @param key The key of the property to retrieve.
     * @returns The boolean value of the property if found and valid, otherwise null.
     */
    getBooleanValue(key: string): boolean | null {
        const value = this.getValue(key);
        if (value) {
            return value.toLowerCase() === 'true';
        }
        return null;
    }

    /**
     * Sets the boolean value of a specific property in the state.
     * @param key The key of the property to set.
     * @param newValue The new boolean value for the property.
     */
    setBooleanValue(key: string, newValue: boolean): void {
        this.setValue(key, newValue.toString());
    }
}

