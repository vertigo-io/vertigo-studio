package io.vertigo.shiny.components.form;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyType;
import io.vertigo.shiny.components.ShinyComponent;

@ShinyType("form")
public record ShinyForm(String title, List<ShinyFormSection> sections) implements ShinyComponent {
	public ShinyForm {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(sections);
	}
	//
	//    @Override
	//    public String getType() {
	//        return "form";
	//    }
}
