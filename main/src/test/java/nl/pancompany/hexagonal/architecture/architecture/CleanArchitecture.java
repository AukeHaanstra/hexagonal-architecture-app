package nl.pancompany.hexagonal.architecture.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.*;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static java.util.stream.Collectors.toMap;

public class CleanArchitecture {

    private static final String SUBPACKAGES = "..";
    private final JavaClasses javaClasses;
    private final Map<String, Set<String>> multiLayerDefinitions = new HashMap<>();
    private Architectures.LayeredArchitecture layeredArchitecture;

    private CleanArchitecture(final String rootPackageName) {
        this.javaClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages(rootPackageName + SUBPACKAGES);
        this.layeredArchitecture = layeredArchitecture()
                .consideringAllDependencies();
    }

    public static CleanArchitecture forRoot(final String rootPackageName) {
        return new CleanArchitecture(rootPackageName);
    }

    public LayerDefinition layer(final String layerName) {
        return new LayerDefinition(layerName);
    }

    public LayersDefinition layers(final String multiLayerName) {
        return new LayersDefinition(multiLayerName);
    }

    public LayerDependencySpecification whereLayer(final String layerName) {
        return new LayerDependencySpecification(layerName);
    }

    public LayersDependencySpecification whereLayers(final String multiLayerName) {
        return new LayersDependencySpecification(multiLayerName);
    }

    public CleanArchitecture ignoreDependency(final String packageName) {
        layeredArchitecture = layeredArchitecture.ignoreDependency(resideInAPackage(packageName + SUBPACKAGES), DescribedPredicate.alwaysTrue());
        return this;
    }


    public CleanArchitecture ensureAllClassesAreContainedInArchitectureIgnoring(final String... packageNames) {
        layeredArchitecture =
                layeredArchitecture.ensureAllClassesAreContainedInArchitectureIgnoring(Arrays.stream(packageNames)
                        .map(packageName -> packageName + SUBPACKAGES)
                        .toArray(String[]::new));
        return this;
    }

    public void check() {
        layeredArchitecture.check(javaClasses);
    }

    @RequiredArgsConstructor
    public class LayerDefinition {

        private final String layerName;

        public CleanArchitecture definedBy(final Class<? extends Annotation> annotationClass) {
            final List<String> basePackages =
                    javaClasses.stream()
                            .filter(javaClass -> javaClass.isAnnotatedWith(annotationClass))
                            .map(JavaClass::getPackageName)
                            .toList();
            if (basePackages.size() != 1) {
                throw new IllegalArgumentException("Should provide an annotation used in at least, and no more than, one package for annotation: "
                        + annotationClass.getSimpleName());
            }
            layeredArchitecture = layeredArchitecture.layer(layerName)
                    .definedBy(basePackages.getFirst() + SUBPACKAGES);
            return CleanArchitecture.this;
        }

    }

    @RequiredArgsConstructor
    public class LayersDefinition {

        private final String multiLayerName;

        public CleanArchitecture definedBy(final Class<? extends Annotation> annotationClass) {
            final Map<String, String> basePackages =
                    javaClasses.stream()
                            .filter(javaClass -> javaClass.isAnnotatedWith(annotationClass))
                            .collect(toMap(JavaClass::getSimpleName, JavaClass::getPackageName));
            for (final String layerName : basePackages.keySet()) {
                layeredArchitecture = layeredArchitecture.layer(layerName)
                        .definedBy(basePackages.get(layerName) + SUBPACKAGES);
            }
            multiLayerDefinitions.put(multiLayerName, basePackages.keySet());
            return CleanArchitecture.this;
        }
    }

    @RequiredArgsConstructor
    public class LayerDependencySpecification {

        private final String layerName;

        CleanArchitecture mayNotBeAccessedByAnyLayer() {
            layeredArchitecture = layeredArchitecture.whereLayer(layerName)
                    .mayNotBeAccessedByAnyLayer();
            return CleanArchitecture.this;
        }

        CleanArchitecture mayOnlyBeAccessedByLayers(final String... layerNamesWithMulti) {
            final List<String> layerNames =
                    new ArrayList<>(Arrays.stream(layerNamesWithMulti)
                            .filter(this::isSingleLayerName)
                            .toList());
            final List<String> multiLayerNamesExpanded =
                    Arrays.stream(layerNamesWithMulti)
                            .filter(this::isMultiLayerName)
                            .map(multiLayerDefinitions::get)
                            .flatMap(Set::stream)
                            .toList();
            layerNames.addAll(multiLayerNamesExpanded);
            layeredArchitecture = layeredArchitecture.whereLayer(layerName)
                    .mayOnlyBeAccessedByLayers(layerNames.toArray(String[]::new));
            return CleanArchitecture.this;
        }

        private boolean isSingleLayerName(final String layerName) {
            return !multiLayerDefinitions.containsKey(layerName);
        }

        private boolean isMultiLayerName(final String layerName) {
            return multiLayerDefinitions.containsKey(layerName);
        }

    }

    @RequiredArgsConstructor
    public class LayersDependencySpecification {

        private final String multiLayerName;

        CleanArchitecture mayOnlyBeAccessedByLayers(final String... layerNames) {
            for (final String layerName : multiLayerDefinitions.get(multiLayerName)) {
                new LayerDependencySpecification(layerName).mayOnlyBeAccessedByLayers(layerNames);
            }
            return CleanArchitecture.this;
        }

    }

}