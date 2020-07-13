package io.vertigo.studio.notebook;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**Each sketch has a prefix
 * @author pchretien
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ ElementType.TYPE })
public @interface SkecthPrefix {
	/**
	 * Sketch type prefix.
	 */
	String value();
}
