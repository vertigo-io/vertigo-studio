import { ShinyModel } from '../../ShinyModel';
import type { ShinyTimelineItem } from "./ShinyTimelineItem";

export interface ShinyTimeline extends ShinyModel{
    title?: string;
    items: ShinyTimelineItem[];
}
