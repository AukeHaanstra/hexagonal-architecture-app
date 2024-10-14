package nl.pancompany.clean.architecture.common.annotation.architecture;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * This annotation marks a class as an optional component at the root of an architectural element.
 * Check application.yaml to see for which profile this bean is wired.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PluggableComponent
public @interface PluggableRootComponent {

    /**
     * The value sets the name of the optional component,
     * to be turned into a Spring bean name.
     * Alias for {@link PluggableComponent#value}.
     */
    @AliasFor(annotation = PluggableComponent.class, attribute = "value")
    String value() default "";

}