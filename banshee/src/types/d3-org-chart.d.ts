declare module 'd3-org-chart' {
  export default class OrgChart<T = any> {
    constructor();
    container(el: HTMLElement): this;
    data(nodes: T[]): this;
    nodeId(fn: (d: T) => string): this;
    parentNodeId(fn: (d: T) => string | null): this;
    nodeContent(fn: (d: any) => string): this;
    render(): this;
    // tu peux ajouter d'autres méthodes si nécessaire
  }
}
