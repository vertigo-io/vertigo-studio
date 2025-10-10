import { ShinyComponent } from '../../ShinyComponent';
import type { ShinyTimelineItem } from "./ShinyTimelineItem";

export interface ShinyTimeline extends ShinyComponent{
    title?: string;
    items: ShinyTimelineItem[];
}
