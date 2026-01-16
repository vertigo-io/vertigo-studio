/**
 * ShinyProp is an interface that represents a key-value pair used to define properties
 * for various Shiny models in the Banshee frontend. These properties allow for dynamic
 * configuration and customization of components.
 */
export interface ShinyProp {
  /**
   * The key of the property.
   */
  key: string;
  /**
   * The string value of the property.
   */
  value: string;
}