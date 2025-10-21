package io.vertigo.shiny.models.text.chip;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShinyChipVariant {
    @JsonProperty("elevated")
    ELEVATED,
    @JsonProperty("flat")
    FLAT,
    @JsonProperty("outlined")
    OUTLINED,
    @JsonProperty("text")
    TEXT,
    @JsonProperty("plain")
    PLAIN;
}
