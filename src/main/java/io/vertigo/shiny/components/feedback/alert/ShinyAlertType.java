package io.vertigo.shiny.components.feedback.alert;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ShinyAlertType {
    @JsonProperty("success")
    SUCCESS,
    @JsonProperty("info")
    INFO,
    @JsonProperty("warning")
    WARNING,
    @JsonProperty("error")
    ERROR;
}
