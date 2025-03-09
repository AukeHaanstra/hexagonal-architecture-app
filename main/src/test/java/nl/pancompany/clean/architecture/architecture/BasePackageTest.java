package nl.pancompany.clean.architecture.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import nl.pancompany.clean.architecture.common.annotation.architecture.Application;
import nl.pancompany.clean.architecture.common.annotation.architecture.Main;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BasePackageTest {

    static final String ROOT_PACKAGE = "nl.pancompany.clean.architecture";
    static final String MAIN_PACKAGE = ROOT_PACKAGE + ".main";
    static final String APPLICATION_PACKAGE = ROOT_PACKAGE + ".application";

    static final Map<Class<? extends Annotation>, String> packages = Map.of(
            Main.class, MAIN_PACKAGE,
            Application.class, APPLICATION_PACKAGE
    );
    static final String SUBPACKAGES = "..";

    final JavaClasses javaClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(ROOT_PACKAGE + SUBPACKAGES);

    @ParameterizedTest
    @ValueSource(classes = {Main.class, Application.class})
    void testBasePackagesUnchanged(final Class<? extends Annotation> annotationClass) {
        final var expectedBasePackage = packages.get(annotationClass);
        final var actualBasePackage = getBasePackage(annotationClass);
        assertThat(actualBasePackage).isEqualTo(expectedBasePackage);
    }

    private String getBasePackage(final Class<? extends Annotation> annotationClass) {
        final var basePackages =
                javaClasses.stream()
                        .filter(javaClass -> javaClass.isAnnotatedWith(annotationClass))
                        .map(JavaClass::getPackageName)
                        .toList();
        if (basePackages.size() != 1) {
            throw new IllegalArgumentException("Should provide an annotation used in at least, and no more than, one package.");
        }
        return basePackages.getFirst();
    }

}