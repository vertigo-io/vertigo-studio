import { ShinyBlock } from '../../ShinyBlock';
import type { ShinyTimelineItem } from "./ShinyTimelineItem";

export interface ShinyTimeline extends ShinyBlock{
    title?: string;
    items: ShinyTimelineItem[];
}
