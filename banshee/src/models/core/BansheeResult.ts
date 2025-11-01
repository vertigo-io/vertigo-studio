import { ShinyElement } from "../ShinyElement";
import { ShinyBlock } from "../ShinyBlock";
import { ShinyLayout } from "../ShinyLayout";

export interface BansheeResult {
    action: string;
    model: ShinyElement | ShinyBlock | ShinyLayout;
}
