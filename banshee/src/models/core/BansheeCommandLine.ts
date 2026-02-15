
import { ShinyState } from "../ShinyState";

export class BansheeCommandLine {
    commandLine: string;
    id?: string;
    state?: ShinyState;

    constructor(commandLine: string, id?: string, state?: ShinyState) {
        this.commandLine = commandLine;
        this.id = id;
        this.state = state;
    }
}
