import { BansheeCommandLine } from './BansheeCommandLine';

export class BansheeManager {
    private ws: WebSocket | null = null;

    public setWebSocket(ws: WebSocket) {
        this.ws = ws;
    }

    public send(commandLine: BansheeCommandLine) {
        if (this.ws) {
            this.ws.send(JSON.stringify(commandLine));
        } else {
            console.error("WebSocket connection not available.");
        }
    }
}
