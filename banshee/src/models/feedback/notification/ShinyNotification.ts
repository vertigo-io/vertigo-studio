import { ShinyBlock } from '../../ShinyBlock';
import { ShinyNotificationType } from './ShinyNotificationType';

export interface ShinyNotification extends ShinyBlock {
    message: string;
    type: ShinyNotificationType;
    timeout: number;
}
