## Component configuratie

Om centraal te configureren welke adapters (en pluggable applicatielagen) we in onze clean architecture gebruiken,
maken we gebruik van een `application-components.yaml`. Hierin staan profielen en adapters die bij dat profiel
geactiveerd zijn.

Er zijn drie typen componenten (met bijbehorende annotaties) die verschillend behandeld worden in deze configuratie:

- _**PluggableRootComponent**_ (`@PluggableRootComponent`): een pluggable component dat zich in de root van een adapter
  package of applicatielaag
  bevindt.
  Het activeren van zo'n component betekent het activeren van alle andere Component-en die zich in die package of
  subpackages bevinden. Iedere adapter en pluggable applicatielaag dient een component te hebben dat als zodanig
  geannoteerd is. Een PluggableRootComponent dient altijd een naam te hebben om te identificeren. Voorbeelden zijn de
  adapters (annotatie `@Adapter("<beannaam>")`).
- _**PluggableComponent**_ (`@PluggableComponent`): een pluggable component in een adapter.
  Kan net als PluggableRootComponent worden opgegeven in de `application-components.yaml`. Het activeren van zo'n
  component heeft echter niet het activeren van alle andere Componenten in die package en subpackages tot gevolg. Een
  PluggableComponent dient altijd een naam te hebben om te identificeren. Let op: wanneer zich in de root van een
  package geen geactiveerde `PluggableRootComponent` bevindt, kan een `PluggableComponent` ook niet in- en uit worden
  geschakeld, in zo'n geval kun je beter een Activate-profiel gebruiken (zie [Profielen](profielen.md)). Dit is
  intentioneel, met een `PluggableRootComponent` kun je component-scanning in een adapter of applicatielaag in en uit
  schakelen.
- _**Component**_ (`@Component`): een regulier Spring Component. In de adapters worden deze
  Componenten automatisch ingeschakeld als het PluggableRootComponent van die adapter of applicatielaag ingeschakeld
  wordt, en wordt niet ingewired in de applicatiecontext indien deze niet wordt ingeschakeld. Zijn niet afzonderlijk in
  en uit te schakelen via `application-components.yaml`.

Een `PluggableRootComponent` is een `PluggableComponent`, en een `PluggableComponent` is een Spring `Component`.
`Adapter` is een voorbeelden van een `PluggableRootComponent`.

Hoewel bovenstaande de belangrijkste applicatieconfiguratie weergeeft, zijn er meer plekken
waar componenten worden geconfigureerd in de applicatie. De uiteindelijke component configuratie wordt bepaald via:

- Annotaties in de main module.
- `application-components.yaml` en onze `BeanDefinitionRegistryPostProcessor` voor de adapter module.
- `UsecaseConfig` (main module), `UsecaseContext` (usecase module) en `UsecaseTestContext` (usecase-test module)
  voor de usecase en domain modules.
- Voor integratietesten via alle bovenstaande manieren en de test annotaties. Daarbij stelt de FakeAdapterConfiguration
  enkele fake adapters in die de adapters, gedefinieerd in `application-components.yaml`, in integratietesten overriden
  (eigenlijk "preferent wiren" via `@Primary`).

### Voordelen:

De gebruikte component configuratie heeft de volgende voordelen:

- Component configuratie op _**één plek**_ (geen `@Profile` overal).
- _**Duidelijk inzicht**_ in de context configuratie: welke componenten voor welk profiel.
- _**Separation of concerns**_: adapters en componenten hoeven niet te weten hoe ze gewired worden: de
  `application-components.yaml` koppelt nu de profielen aan de actieve adapters/componenten, in tegenstelling tot de
  componenten zelf (die kennen nu enkel hun eigen bean naam en hebben geen `@Profile` meer).

  _N.B. Spring doet dit zelf niet omdat Spring niet uit kan gaan van onze clean architecture met bijbehorende adapter
  structuur: als je **alle** componenten op deze manier aan profielen zou willen koppelen wordt
  het al heel snel onoverzichtelijk. Wij koppelen enkel adapters en enkele pluggable beans op deze
  manier aan profielen._
- _**Cohesie**_: profiel configuratie en component configuratie (afhankelijk van de actieve profielen) staan nu vlak bij
  elkaar (`application.yaml` en `application-components.yaml`).
- _**Helderheid door "compositie over overerving"**_ wanneer we de componenten definiëren onder _fine-grained_ profielen
  als `local-postgres`.