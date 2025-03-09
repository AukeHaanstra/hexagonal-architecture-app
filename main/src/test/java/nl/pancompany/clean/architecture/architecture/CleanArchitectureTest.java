package nl.pancompany.clean.architecture.architecture;

import nl.pancompany.clean.architecture.common.annotation.architecture.*;
import org.junit.jupiter.api.Test;

/**
 * <p>
 *  <ul>
 *      <li>
 *          <p>
 *              The {@code application} package is the core of the application. It consists of two parts.
 *          </p>
 *          <ol>
 *              <li>
 *                  <p>
 *                      The {@code domain} packages contain the domain entities.
 *                  </p>
 *              </li>
 *              <li>
 *                  <p>
 *                      The {@code usecase} packages contain services and configuration to run the application and use cases.
 *                  </p>
 *              </li>
 *          </ol>
 *      </li>
 *      <li>
 *          <p>
 *              The {@code adapter} package contains logic to connect to external systems and/or infrastructure. No adapter may depend on another adapter.
 *              Adapters can use the items of the {@code domain} and {@code usecase} packages. Vice versa, neither the {@code domain} nor
 *              the {@code usescase} packages must contain dependencies on any {@code adapter} package.
 *          </p>
 *      </li>
 *  </ul>
 * </p>
 *
 * @see <a href="https://www.archunit.org/userguide/html/000_Index.html#_onion_architecture">Constraints on an onion architecture - the same constraints apply
 * to a clean architecture</a>
 * @see <a href="https://github.com/TNG/ArchUnit-Examples/blob/main/example-plain/src/test/java/com/tngtech/archunit/exampletest/OnionArchitectureTest.java">
 * ArchUnit example repository - Test example for onion architecture</a>
 * @see <a href="https://github.com/TNG/ArchUnit/issues/318">How to exclude packages</a>
 * @see <a href="https://github.com/TNG/ArchUnit/issues/841">How to exclude libraries and external classes from violations</a>
 */
class CleanArchitectureTest {

    static final String ROOT_PACKAGE = "nl.pancompany.clean.architecture";
    static final String LIBRARY_PACKAGE = ROOT_PACKAGE + ".common";
    static final String APPLICATION_TEST_LIBRARY_PACKAGE = ROOT_PACKAGE + ".test.common";

    static final String MAIN = "Main";
    static final String ADAPTERS = "Adapters";
    static final String APPLICATION = "Application";

    @Test
    void architectureDoesNotViolateCleanArchitectureRules() {
        CleanArchitecture.forRoot(ROOT_PACKAGE)
                .layer(MAIN).definedBy(Main.class)
                .layers(ADAPTERS).definedBy(Adapter.class)
                .layer(APPLICATION).definedBy(Application.class)
                .whereLayer(MAIN).mayNotBeAccessedByAnyLayer()
                .whereLayers(ADAPTERS).mayOnlyBeAccessedByLayers(MAIN)
                .whereLayer(APPLICATION).mayOnlyBeAccessedByLayers(MAIN, ADAPTERS)
                .ignoreDependency(APPLICATION_TEST_LIBRARY_PACKAGE)
                .ensureAllClassesAreContainedInArchitectureIgnoring(LIBRARY_PACKAGE, APPLICATION_TEST_LIBRARY_PACKAGE)
                .check();
        }

}