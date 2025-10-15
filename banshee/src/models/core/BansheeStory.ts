import { BansheeMessage } from './BansheeMessage';

export class BansheeStory {
  private _messages: BansheeMessage[] = [];

  get messages(): BansheeMessage[] {
    return this._messages;
  }

  pushMessage(message: BansheeMessage) : void {
    this._messages.push(message);
  }

  clear() :void {
    this._messages.splice(0);
  }
}
