
import { ShinyState } from "../ShinyState";

export interface BansheeCommand {
    command: string;
    id?: string;
    state?: ShinyState;
}
