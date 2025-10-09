export class ShinySankeyLink {
  from: string;
  to: string;
  flow: number;

  constructor(from: string, to: string, flow: number) {
    this.from = from;
    this.to = to;
    this.flow = flow;
  }
}
