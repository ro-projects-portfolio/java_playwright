package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark fields as Playwright page objects.
 * Fields annotated with @PlaywrightPage will be automatically initialized with Playwright pages
 * when using the PlaywrightRunner class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PlaywrightPage {
}
