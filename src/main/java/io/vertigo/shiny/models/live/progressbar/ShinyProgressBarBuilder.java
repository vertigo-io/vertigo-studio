package io.vertigo.shiny.models.live.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyProgressBarBuilder implements Builder<ShinyProgressBar> {
	    private int _total = 0; // Valeur totale correspondant à 100%

	public ShinyProgressBarBuilder withTotal(final int value) {
		Assertion.check().isTrue(value > 0, "total must be > 0");
		//---
		        this._total = value;		return this;
	}

    public ShinyProgressBar build() {
        return new ShinyProgressBar(_total);
    }
}
