package nl.pancompany.hexagonal.architecture.it.support.annotations;

import nl.pancompany.hexagonal.architecture.it.support.fakeadapters.FakeAdapterConfiguration;
import nl.pancompany.hexagonal.architecture.it.support.testcontainers.TestcontainersConfiguration;
import nl.pancompany.hexagonal.architecture.main.DummyApp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(classes = DummyApp.class)
@ActiveProfiles
@Import({FakeAdapterConfiguration.class, TestcontainersConfiguration.class})
public @interface DummyIT {

    // This way, you can override profiles on a per-test basis
    @AliasFor(annotation = ActiveProfiles.class, attribute = "profiles")
    String[] value() default {"local-it-test-run", "warn-logging"};

}
