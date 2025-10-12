# Documentation des Modèles de Composants Banshee

Cette documentation technique liste les modèles de données utilisés par les composants Vue.js de Banshee. Pour chaque modèle, les propriétés attendues, leur type et leur caractère obligatoire ou facultatif sont détaillés.

---

## `ShinyComponent`

**Fichier source**: `ShinyComponent.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |

---

## `ShinyContainer`

**Fichier source**: `core\container\ShinyContainer.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `components` | `ShinyComponent[]` | Oui |

---

## `ShinyError`

**Fichier source**: `core\error\ShinyError.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `text` | `string` | Oui |

---

## `ShinyCalendar`

**Fichier source**: `data\calendar\ShinyCalendar.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `year` | `number` | Oui |
| `month` | `number` | Oui |

---

## `ShinyCardComponent`

**Fichier source**: `data\card\ShinyCardComponent.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `subtitle` | `string` | Non |
| `description` | `string` | Non |
| `imageUrl` | `string` | Non |
| `imageAlt` | `string` | Non |
| `link` | `string` | Non |
| `icon` | `string` | Non |
| `badgeLabel` | `string` | Non |
| `badgeColor` | `string` | Non |
| `format` | `ShinyCardFormat` | Oui |

---

## `ShinyCardFormat`

**Fichier source**: `data\card\ShinyCardFormat.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyChip`

**Fichier source**: `data\chip\ShinyChip.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `text` | `string` | Oui |
| `color` | `string` | Non |
| `variant` | `ShinyChipVariant` | Non |
| `closable` | `boolean` | Oui |
| `icon` | `string` | Non |

---

## `ShinyChipVariant`

**Fichier source**: `data\chip\ShinyChipVariant.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyForm`

**Fichier source**: `data\form\ShinyForm.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `sections` | `ShinyFormSection[]` | Oui |

---

## `ShinyFormField`

**Fichier source**: `data\form\ShinyFormField.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `name` | `string` | Oui |
| `label` | `string` | Oui |
| `type` | `ShinyFormFieldType` | Oui |
| `value` | `any` | Non |
| `required` | `boolean` | Oui |
| `placeholder` | `string` | Non |
| `helpText` | `string` | Non |
| `defaultValue` | `any` | Non |
| `options` | `ShinyFormOption[]` | Non |
| `validator` | `ShinyFormFieldValidator` | Non |
| `readOnly` | `boolean` | Oui |

---

## `ShinyFormFieldType`

**Fichier source**: `data\form\ShinyFormFieldType.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyFormFieldValidator`

**Fichier source**: `data\form\ShinyFormFieldValidator.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `pattern` | `string` | Non |
| `minLength` | `number` | Non |
| `maxLength` | `number` | Non |
| `minValue` | `any` | Non |
| `maxValue` | `any` | Non |

---

## `ShinyFormOption`

**Fichier source**: `data\form\ShinyFormOption.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `label` | `string` | Oui |
| `value` | `any` | Oui |

---

## `ShinyFormSection`

**Fichier source**: `data\form\ShinyFormSection.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `title` | `string` | Oui |
| `fields` | `ShinyFormField[]` | Oui |
| `collapsible` | `boolean` | Oui |
| `initiallyCollapsed` | `boolean` | Oui |

---

## `ShinyJson`

**Fichier source**: `data\json\ShinyJson.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `json` | `string` | Oui |

---

## `ShinyList`

**Fichier source**: `data\list\ShinyList.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `items` | `(string | ShinyList)[]` | Oui |
| `listType` | `ShinyListType` | Oui |

---

## `ShinyListType`

**Fichier source**: `data\list\ShinyListType.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyOrganization`

**Fichier source**: `data\organization\ShinyOrganization.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `nodes` | `ShinyOrganizationNode[]` | Oui |

---

## `ShinyOrganizationNode`

**Fichier source**: `data\organization\ShinyOrganizationNode.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `id` | `string` | Oui |
| `parentId` | `string | null` | Oui |
| `name` | `string` | Oui |
| `position` | `string` | Oui |
| `imageUrl` | `string` | Non |

---

## `ShinyTable`

**Fichier source**: `data\table\ShinyTable.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `id` | `string` | Oui |
| `title` | `string` | Oui |
| `noDataFound` | `string` | Oui |
| `header` | `string[]` | Oui |
| `rows` | `string[][]` | Oui |
| `sortable` | `boolean` | Oui |
| `sortColumn` | `number` | Oui |
| `sortDirection` | `string` | Oui |

---

## `ShinyTimeline`

**Fichier source**: `data\timeline\ShinyTimeline.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Non |
| `items` | `ShinyTimelineItem[]` | Oui |

---

## `ShinyTimelineItem`

**Fichier source**: `data\timeline\ShinyTimelineItem.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `title` | `string` | Oui |
| `content` | `string` | Non |
| `color` | `string` | Non |
| `icon` | `string` | Non |

---

## `ShinyTree`

**Fichier source**: `data\tree\ShinyTree.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `label` | `string` | Oui |
| `children` | `ShinyTree[]` | Oui |
| `type` | `string` | Oui |

---

## `ShinyChart`

**Fichier source**: `dataviz\chart\ShinyChart.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `chartType` | `ShinyChartType` | Oui |
| `labels` | `string[]` | Oui |
| `series` | `ShinyChartSerie[]` | Oui |

---

## `ShinyChartSerie`

**Fichier source**: `dataviz\chart\ShinyChartSerie.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `name` | `string` | Oui |
| `data` | `number[]` | Oui |

---

## `ShinyChartType`

**Fichier source**: `dataviz\chart\ShinyChartType.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyGauge`

**Fichier source**: `dataviz\gauge\ShinyGauge.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `value` | `number` | Oui |
| `maxValue` | `number` | Oui |

---

## `ShinyRating`

**Fichier source**: `dataviz\rating\ShinyRating.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `label` | `string` | Oui |
| `value` | `number` | Oui |
| `scale` | `ShinyRatingScale` | Oui |
| `customMaxValue` | `number` | Oui |
| `showValue` | `boolean` | Oui |
| `showPercentage` | `boolean` | Oui |
| `showBox` | `boolean` | Oui |
| `separator` | `string` | Oui |
| `allowHalfRating` | `boolean` | Oui |
| `type` | `string` | Oui |

---

## `ShinyRatingScale`

**Fichier source**: `dataviz\rating\ShinyRatingScale.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinySankey`

**Fichier source**: `dataviz\sankey\ShinySankey.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `data` | `ShinySankeyLink[]` | Oui |

---

## `ShinySankeyLink`

**Fichier source**: `dataviz\sankey\ShinySankeyLink.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `from` | `string` | Oui |
| `to` | `string` | Oui |
| `flow` | `number` | Oui |

---

## `ShinySparkline`

**Fichier source**: `dataviz\sparkline\ShinySparkline.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `values` | `number[]` | Oui |

---

## `ShinyStatus`

**Fichier source**: `dataviz\status\ShinyStatus.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `types` | `ShinyStatusType[]` | Oui |

---

## `ShinyStatusType`

**Fichier source**: `dataviz\status\ShinyStatusType.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyAlert`

**Fichier source**: `feedback\alert\ShinyAlert.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `alertType` | `ShinyAlertType` | Oui |
| `title` | `string` | Non |
| `content` | `string` | Oui |
| `closable` | `boolean` | Oui |
| `icon` | `string` | Non |
| `prominent` | `boolean` | Oui |

---

## `ShinyAlertType`

**Fichier source**: `feedback\alert\ShinyAlertType.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---

## `ShinyMultiSelection`

**Fichier source**: `input\multiselection\ShinyMultiSelection.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `title` | `string` | Oui |
| `options` | `string[]` | Oui |
| `selectedIndices` | `Set<number>` | Oui |
| `type` | `string` | Oui |

---

## `ShinySlider`

**Fichier source**: `input\slider\ShinySlider.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `label` | `string` | Oui |
| `min` | `number` | Oui |
| `max` | `number` | Oui |
| `step` | `number` | Oui |
| `value` | `number` | Non |
| `color` | `string` | Non |
| `thumbLabel` | `boolean` | Oui |

---

## `ShinyInputText`

**Fichier source**: `input\text\ShinyInputText.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `label` | `string` | Oui |
| `required` | `boolean` | Oui |
| `validationPattern` | `string` | Non |
| `suggestions` | `string[]` | Non |
| `defaultValue` | `string` | Non |
| `value` | `string` | Non |

---

## `ShinyGeoMap`

**Fichier source**: `media\geomap\ShinyGeoMap.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `geoPoints` | `ShinyGeoPoint[]` | Oui |

---

## `ShinyGeoPoint`

**Fichier source**: `media\geomap\ShinyGeoPoint.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `latitude` | `number` | Oui |
| `longitude` | `number` | Oui |
| `label` | `string` | Non |

---

## `ShinyPdfComponent`

**Fichier source**: `media\pdf\ShinyPdfComponent.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Non |
| `pdfPath` | `string` | Oui |
| `initialPage` | `number` | Oui |
| `height` | `string` | Oui |

---

## `ShinyPhoto`

**Fichier source**: `media\photo\ShinyPhoto.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Non |
| `url` | `string` | Oui |
| `alt` | `string` | Non |

---

## `ShinyRss`

**Fichier source**: `media\rss\ShinyRss.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `items` | `ShinyRssItem[]` | Oui |

---

## `ShinyRssItem`

**Fichier source**: `media\rss\ShinyRssItem.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `title` | `string` | Oui |
| `link` | `string` | Oui |
| `description` | `string` | Non |
| `image` | `string` | Non |
| `pubDate` | `string` | Non |
| `author` | `string` | Non |

---

## `ShinyYoutube`

**Fichier source**: `media\youtube\ShinyYoutube.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Non |
| `videoId` | `string` | Oui |

---

## `ShinyFiglet`

**Fichier source**: `text\figlet\ShinyFiglet.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `text` | `string` | Oui |

---

## `ShinyMarkDown`

**Fichier source**: `text\markdown\ShinyMarkDown.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `markdownText` | `string` | Oui |

---

## `ShinyParagraph`

**Fichier source**: `text\paragraph\ShinyParagraph.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `text` | `string` | Oui |

---

## `ShinyTextPath`

**Fichier source**: `text\textpath\ShinyTextPath.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `path` | `string` | Oui |
| `separator` | `string` | Non |

---

## `ShinyTitle`

**Fichier source**: `text\title\ShinyTitle.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `title` | `string` | Oui |
| `level` | `number` | Oui |

---

## `ShinyToggle`

**Fichier source**: `text\toggle\ShinyToggle.ts`

| Propriété | Type | Obligatoire |
|---|---|---|
| `type` | `string` | Oui |
| `label` | `string` | Oui |
| `value` | `boolean` | Oui |
| `toggleType` | `ShinyToggleType` | Oui |
| `onText` | `string` | Non |
| `offText` | `string` | Non |
| `showText` | `boolean` | Oui |

---

## `ShinyToggleType`

**Fichier source**: `text\toggle\ShinyToggleType.ts`

Ce modèle ne contient pas de propriétés directes ou elles n'ont pas pu être extraites.

---
