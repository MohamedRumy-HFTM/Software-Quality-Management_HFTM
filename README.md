# Aufgabe 03: Build-Server mit Maven und GitHub Actions

[![Build and test](https://github.com/MohamedRumy-HFTM/Software-Quality-Management_HFTM/actions/workflows/build.yml/badge.svg)](https://github.com/MohamedRumy-HFTM/Software-Quality-Management_HFTM/actions/workflows/build.yml)

Das Rechner-Beispiel aus dem HFTM-Kurs verwendet Java 17, Maven und JUnit 5.
Es unterstützt Addition, Subtraktion, Multiplikation und Division. Bei Division
durch null wird eine `ArithmeticException` ausgelöst.

## Lokal ausführen

Voraussetzungen: JDK 17 oder neuer und Maven 3.9.x. Die Befehle im Ordner mit der
`pom.xml` ausführen:

```sh
mvn test
mvn clean verify
```

`mvn test` kompiliert das Projekt und führt die sechs Unit-Tests aus.
`mvn clean verify` entfernt vorherige Build-Dateien, führt die Tests aus,
erstellt die JAR-Datei und erzeugt den Bericht zur Test-Abdeckung.
Dieses Beispiel enthält keine Integrationstests; dafür wären zusätzliche Tests
und eine passende Plugin-Konfiguration, zum Beispiel Maven Failsafe, nötig.

Falls nach einem Wechsel der Quelldateien noch alte kompilierte Tests im Ordner
`target` liegen, einmal `mvn clean test` ausführen. `target/` wird nicht versioniert.

## GitHub Actions

Der Workflow liegt in [`.github/workflows/build.yml`](.github/workflows/build.yml).
Bei jedem Push, bei Pull Requests und beim manuellen Start unter **Actions**
führt GitHub folgende Schritte aus:

1. Quellcode aus dem Repository laden (`actions/checkout`).
2. Temurin JDK 17 bereitstellen und Maven-Abhängigkeiten cachen (`actions/setup-java`).
3. Auf einem Ubuntu-Runner `mvn --batch-mode --no-transfer-progress clean verify` ausführen.
4. Vorhandene Test- und Coverage-Berichte als Artefakt hochladen, auch bei einem Testfehler.

Maven ist auf dem GitHub-gehosteten Runner bereits vorhanden. Der Job läuft
direkt auf dem Runner; es ist kein eigener Docker-Container konfiguriert.
`permissions: contents: read` beschränkt die Berechtigungen des Workflow-Tokens.

Ein fehlgeschlagener Test lässt Maven mit einem Fehlercode enden. Damit wird auch
der Build-Schritt und der gesamte Actions-Lauf rot. Ein erfolgreiches Hochladen
der Fehlerberichte ändert diesen Status nicht. Benachrichtigungen hängen von den
persönlichen GitHub-Einstellungen ab.

## Absichtlich fehlschlagenden Test nachvollziehen

1. Zuerst einen erfolgreichen Build unter **Actions → Build and test** ansehen.
2. In `src/test/java/ch/hftm/rechner/CalculatorTest.java` bei `addPositiveIntegers`
   den Erwartungswert `7` auf `8` ändern. Die Rechnung bleibt `add(5, 2)`.
3. `mvn test` ausführen: Ein Test muss mit „expected: 8.0, but was: 7.0“ fehlschlagen.
4. Diese Änderung committen und pushen. Den zugehörigen roten Lauf öffnen und
   im Schritt **Build and test with Maven** den Assertion-Fehler suchen.
5. Den Erwartungswert auf `7` zurücksetzen, erneut testen, committen und pushen.
   Der nächste Lauf muss wieder erfolgreich sein.

Zwischen den Pushes den jeweiligen Lauf abwarten, damit sich alle Ergebnisse
eindeutig einem Commit zuordnen lassen. Der Fehler ist eine falsche Erwartung
im Test, kein Syntax- oder Kompilierungsfehler.

## Vertiefung: Test-Abdeckung

JaCoCo misst beim Testlauf die ausgeführten Codepfade und erstellt bei `verify`
einen Bericht unter `target/site/jacoco/index.html`. Nach einem erfolgreichen
GitHub-Lauf lässt sich das Artefakt **test-and-coverage-reports** herunterladen,
entpacken und die darin enthaltene `index.html` öffnen. Die Berichte werden
14 Tage aufbewahrt.

Bei einem fehlgeschlagenen Unit-Test bricht Maven vor `verify` ab. In diesem Fall
enthält das Artefakt die Surefire-Testberichte, aber keinen neuen Coverage-Bericht.
Es ist keine Mindestabdeckung eingerichtet. Hohe Coverage zeigt ausgeführte
Codepfade; sie beweist nicht, dass alle fachlichen Fälle korrekt getestet sind.
SonarCloud und Super-Linter sind nicht Teil dieser Umsetzung.

## Quellen

- [GitHub Actions mit Maven](https://docs.github.com/en/actions/tutorials/build-and-test-code/java-with-maven)
- [Maven Build Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/)
- [JaCoCo Maven Plugin](https://www.jacoco.org/jacoco/trunk/doc/maven.html)
