package io.vertigo.shiny.components.input.slider;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinySlider(
        String label,
        double min,
        double max,
        double step,
        double value,
        String color,
        boolean thumbLabel) implements ShinyComponent {

    @Override
    	public String type() {
    		return "ShinySlider";    }

    public ShinySlider {
        Assertion.check()
                .isNotBlank(label, "Label cannot be blank")
                .isTrue(max > min, "Max must be greater than min")
                .isTrue(step > 0, "Step must be positive")
                .isTrue(value >= min && value <= max, "Value must be between min and max");
    }
}
