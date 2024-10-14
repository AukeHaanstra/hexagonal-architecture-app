package nl.pancompany.clean.architecture.common.annotation.architecture;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * This annotation marks a class as an optional component.
 * Check application-components.yaml to see for which profile the annotated bean is wired.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PluggableComponent {

    /**
     * The value sets the name of the optional component,
     * to be turned into a Spring bean name.
     * Alias for {@link Component#value}.
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

}