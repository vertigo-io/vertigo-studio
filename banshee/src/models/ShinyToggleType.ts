export enum ShinyToggleType {
  TOGGLE = 'TOGGLE',
  CHECK = 'CHECK',
  SWITCH = 'SWITCH',
  LIGHT = 'LIGHT',
  BATTERY = 'BATTERY',
  STATUS = 'STATUS',
  THUMBS = 'THUMBS',
  ARROW = 'ARROW',
  STAR = 'STAR',
  CLASSIC = 'CLASSIC',
}

export function getShinyToggleIcons(type: ShinyToggleType): { onIcon: string; offIcon: string } {
  switch (type) {
    case ShinyToggleType.TOGGLE: return { onIcon: '◉', offIcon: '○' };
    case ShinyToggleType.CHECK: return { onIcon: '☑', offIcon: '☐' };
    case ShinyToggleType.SWITCH: return { onIcon: '🔛', offIcon: '🔴' };
    case ShinyToggleType.LIGHT: return { onIcon: '💡', offIcon: '🌑' };
    case ShinyToggleType.BATTERY: return { onIcon: '🔋', offIcon: '🪫' };
    case ShinyToggleType.STATUS: return { onIcon: '🟢', offIcon: '🔴' };
    case ShinyToggleType.THUMBS: return { onIcon: '👍', offIcon: '👎' };
    case ShinyToggleType.ARROW: return { onIcon: '▶', offIcon: '⏸' };
    case ShinyToggleType.STAR: return { onIcon: '★', offIcon: '☆' };
    case ShinyToggleType.CLASSIC: return { onIcon: '✅', offIcon: '❌' };
    default: return { onIcon: '', offIcon: '' };
  }
}
