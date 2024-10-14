package nl.pancompany.clean.architecture.architecture;

import nl.pancompany.clean.architecture.common.annotation.architecture.*;
import org.junit.jupiter.api.Test;

/**
 * <p>
 *  <ul>
 *      <li>
 *          <p>
 *              The {@code domain} package is the core of the application. It consists of two parts.
 *          </p>
 *          <ol>
 *              <li>
 *                  <p>
 *                      The {@code domainModels} packages contain the domain entities.
 *                  </p>
 *              </li>
 *              <li>
 *                  <p>
 *                      The packages in {@code domainServices} contains services that use the entities in the {@code domainModel} packages.
 *                  </p>
 *              </li>
 *          </ol>
 *      </li>
 *      <li>
 *          <p>
 *              The {@code usecase} packages (a.k.a. {@code applicationServices}) contain services and configuration to run the application and use cases. It
 *              can use the items of the {@code domain} package but there must not be any dependency from the {@code domain} to the {@code usecase} or
 *              {@code application} packages.
 *          </p>
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
    static final String APPLICATION_TEST_PACKAGE = ROOT_PACKAGE + ".test";
    static final String LIBRARY_PACKAGE = ROOT_PACKAGE + ".common";
    static final String APPLICATION_TEST_LIBRARY_PACKAGE = ROOT_PACKAGE + ".test.common";

    static final String MAIN = "Main";
    static final String ADAPTERS = "Adapters";
    static final String USECASE = "Usecase";
    static final String DOMAIN_MODEL = "DomainModel";
    static final String DOMAIN_SERVICE = "DomainService";
    static final String DOMAIN_PORT = "DomainPort";

    @Test
    void architectureDoesNotViolateCleanArchitectureRules() {
        CleanArchitecture.forRoot(ROOT_PACKAGE)
                .layer(MAIN).definedBy(Main.class)
                .layers(ADAPTERS).definedBy(Adapter.class)
                .layer(USECASE).definedBy(Usecase.class)
                .layer(DOMAIN_SERVICE).definedBy(DomainService.class)
                .layer(DOMAIN_MODEL).definedBy(DomainModel.class)
                .layer(DOMAIN_PORT).definedBy(DomainPort.class)
                .whereLayer(MAIN).mayNotBeAccessedByAnyLayer()
                .whereLayers(ADAPTERS).mayOnlyBeAccessedByLayers(MAIN)
                .whereLayer(USECASE).mayOnlyBeAccessedByLayers(MAIN, ADAPTERS)
                .whereLayer(DOMAIN_SERVICE).mayOnlyBeAccessedByLayers(MAIN, ADAPTERS, USECASE)
                // DOMAIN_MODEL and DOMAIN_PORT constitute one actual application layer
                .whereLayer(DOMAIN_PORT).mayOnlyBeAccessedByLayers(MAIN, ADAPTERS, USECASE, DOMAIN_SERVICE, DOMAIN_MODEL)
                .whereLayer(DOMAIN_MODEL).mayOnlyBeAccessedByLayers(MAIN, ADAPTERS, USECASE, DOMAIN_SERVICE, DOMAIN_PORT)
                .ignoreDependency(APPLICATION_TEST_PACKAGE)
                .ignoreDependency(APPLICATION_TEST_LIBRARY_PACKAGE)
                .ensureAllClassesAreContainedInArchitectureIgnoring(LIBRARY_PACKAGE, APPLICATION_TEST_LIBRARY_PACKAGE)
                .check();
        }

}