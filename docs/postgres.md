## Postgres database runnen met docker

Voor het runnen van de clean architecture demo applicatie met demo profiel, is een lokale postgres nodig.
In de repository root staan een drietal sql scripts, die helpen met het lokaal runnen van postgres:

* `postgres-run.sh`: Zal de laatste postgres image ophalen en starten.
* `postgres-stop-remove.sh`: Hiermee kun je de postgres instantie stoppen en verwijderen.
* `postgres-psql-login.sh`: Hiermee ga je in de postgres container en log je binnen de container in met psql. Je kunt
  dan psql commando's uitvoeren binnen je container. Het is echter ook mogelijk om vanuit Intellij een connectie met de
  postgres database te maken op poort 5432.