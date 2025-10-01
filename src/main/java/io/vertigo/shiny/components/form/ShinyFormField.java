package io.vertigo.shiny.components.form;

import io.vertigo.core.lang.Assertion;

import java.util.List;

public record ShinyFormField(
        String name,
        String label,
        ShinyFormFieldType type,
        Object value,
        boolean required,
        String placeholder,
        String helpText,
        Object defaultValue,
        List<ShinyFormOption> options,
        ShinyFormFieldValidator validator,
        boolean readOnly) {

    public ShinyFormField {
        Assertion.check()
                .isNotBlank(name)
                .isNotBlank(label)
                .isNotNull(type);
        // value can be null
        // options can be null if not a SELECT, RADIO, or CHECKBOX_GROUP
    }

    public boolean validate() {
        return validator.validate(value);
    }
}
