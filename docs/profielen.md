## Profielen

### Spring profielen

Voor de profielen gebruiken we profielgroepen en multi-document yaml bestanden. Er zijn drie typen profielen:

- _**Includes profielen:**_ deze, en de profielen die eronder gegroepeerd zijn, worden gebruikt om de verschillende yaml
  bestanden (per techniek) in te laden in Spring. Suffix: `-includes`.
- _**Convenience profielen:**_ profielen die gebruikt worden als synoniem voor de profielen die ze groeperen. Hebben
  verder geen bijzondere functie binnen de applicatie. Deze profielen hebben meestal een algemenere naam dan de
  profielen die ze groeperen.
- _**Run-profielen:**_ deze worden gebruikt om de applicatie of een test mee te draaien en bevatten combinaties van
  verschillende specifieke profielen. Deze staan gedefinieerd op het hoogste niveau in `spring.profiles.group`.
  Suffix: `-run` en `-test-run` voor run profielen gebruikt in testen.
- _**Specifieke profielen/Functionele profielen:**_ "fine-grained" profielen die worden gebruikt om per techniek en per
  omgeving configuratie te definiëren (met `spring.config.activate.on-profile`). Deze staan onder de voorgaande
  profielen, een niveau lager in `spring.profiles.group`. De property `functional-profiles.matchers` laat zien wat de
  namen zijn van deze profielen, dit wordt door de applicatie gebruikt om de functionele profielen bij het opstarten te
  loggen. Formaat: `<omgeving>-logging` of `<omgeving>-postgres`. Deze profielen weerspiegelen de functionaliteit waarmee de applicatie is opgestart.
- _**Components profielen:**_ deze worden gebruikt om applicatielagen/adapters mee te activeren via
  `application-components.yaml`. Suffix: `-components`.
- _**Activate profielen:**_ deze worden gebruikt om afzonderlijke componenten mee in en uit te schakelen, daar waar in
  en uit schakelen via @PluggableComponent ongewenst is (in klassen die voor de architectuur irrelevant zijn, of in 
  testen) of onmogelijk is (in testen). Deze profielen worden enkel gebruikt om deze componenten mee te activeren en de
  componenten zijn geannoteerd met `@Profile`. Formaat: `activate-<component-name>`.
- _**Enable profielen:**_ deze schakelen technische functionaliteit aan en uit. Prefix: `-enable`.

Er is hierdoor bijna geen sprake van overerving, ofwel overschrijven van properties tussen profielen. Daardoor kun je
bij het definiëren van een run-profiel deze volledig opbouwen met specifiekere profielen. Daardoor heb je een duidelijk
inzicht in de compositie van een profiel. Een aantal positieve aspecten van deze indeling zijn:

- _**"Composition over inheritance":**_ je kunt een bestaand profiel aanpassen of een nieuwe aanmaken door een nieuwe
  combinatie van reeds bestaande specifieke profielen te maken. Er is minder complexiteit en onduidelijkheid doordat er
  geen of minder properties overschreven worden.
- _**Cohesie:**_ per techniek staan de verschillende configuratieopties bij elkaar.
- _**Modulariteit:**_ de configuraties worden gedefinieerd in de resources folder van de module waar ze gebruikt worden.
  Net als de adapters in de adapter module, staat de configuratie per techniek gegroepeerd.

De `application.yaml` bevat nu hoofdzakelijk profieldefinities en wordt niet meer gebruikt om specifieke
omgevingsafhankelijke properties te specificeren die in de applicatie gebruikt worden. Dit doe je in de specifieke
profielen voor de relevante techniek en omgeving.

De application yaml bestanden zijn te vinden onder:

- Main resources van de main module (en tevens de hoofd `application.yaml`)
- Main resources van de adapter module (techniek specifieke yaml en secrets yaml bestanden)
- Main resources van de it module (gebruikt voor aanvullende configuratie in testen)

Voor debugging is het handig om het profiel `activate-properties-printer` te activeren (als dat niet al is gedaan). Dan zul je
bij het opstarten van Spring een printout zien met alle properties.

Voor verdere documentatie zie:

- https://docs.spring.io/spring-boot/reference/features/profiles.html
- https://docs.spring.io/spring-boot/reference/features/external-config.html

### Maven profielen

Voor het genereren en aggregeren van JaCoCo test coverage data in de coverage module (onder `/target/site`), kan de
applicatie gedraaid worden met het profiel `coverage`. Een maven clean install die ook coverage data genereert en
aggregeert kun je dus uitvoeren met `mvn clean install -Pcoverage`.