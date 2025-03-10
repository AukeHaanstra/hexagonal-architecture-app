package nl.pancompany.hexagonal.architecture.common.annotation.architecture;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * This annotation marks a class as the adapter base class,
 * marking the root package of the adapter.
 * It also functions as a stereotype annotation in clean architecture,
 * and marks its annotated class as a bean for a component scan of the adapter module.
 * Similar to Spring's default stereotype annotations such as {@code @Service}.
 *
 * Check application-components.yaml to see for which profile the annotated bean is wired.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PluggableRootComponent
public @interface Adapter {

    /**
     * The value sets the adapter name,
     * to be turned into a Spring bean name.
     * Alias for {@link PluggableRootComponent#value}.
     */
    @AliasFor(annotation = PluggableRootComponent.class, attribute = "value")
    String value() default "";

}