import { BansheeCommand } from './BansheeCommand';

export class BansheeManager {
    private ws: WebSocket | null = null;

    public setWebSocket(ws: WebSocket) {
        this.ws = ws;
    }

    public send(command: BansheeCommand) {
        if (this.ws) {
            this.ws.send(JSON.stringify(command));
        } else {
            console.error("WebSocket connection not available.");
        }
    }
}
