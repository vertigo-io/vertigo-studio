
import { ShinyState } from "../ShinyState";

export class BansheeCommand {
    command: string;
    id?: string;
    state?: ShinyState;

    constructor(command: string, id?: string, state?: ShinyState) {
        this.command = command;
        this.id = id;
        this.state = state;
    }
}
