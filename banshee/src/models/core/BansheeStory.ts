import { BansheeMessage } from './BansheeMessage';
import { ShinyModel } from '../ShinyModel';

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

  updateMessage(model: ShinyModel): void {
  //on cherhe un modèle avec le même id et on le remplace   
  const message: BansheeMessage | undefined = this._messages.find(msg => msg.component.id === model.id);
  if (message) {
    message.component = model;
  } else {
      console.warn(`Model with id ${model.id} not found`);
    }
  } 
}
