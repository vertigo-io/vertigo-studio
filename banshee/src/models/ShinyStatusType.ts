export enum ShinyStatusType {
  SUCCESS = 'SUCCESS',
  ERROR = 'ERROR',
  WARNING = 'WARNING',
  INFO = 'INFO',
  NEUTRAL = 'NEUTRAL',
}

export function getShinyStatusColor(type: ShinyStatusType): string {
  switch (type) {
    case ShinyStatusType.SUCCESS:
      return 'var(--status-connected)';
    case ShinyStatusType.ERROR:
      return 'var(--status-disconnected)';
    case ShinyStatusType.WARNING:
      return 'var(--yellow-accent)';
    case ShinyStatusType.INFO:
      return 'var(--x-neon-blue)';
    case ShinyStatusType.NEUTRAL:
      return 'var(--general-text)';
    default:
      return 'gray';
  }
}
