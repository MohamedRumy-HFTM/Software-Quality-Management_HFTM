# Nachweis zur Aufgabe 03

Durchgeführt am 11. September 2026.

## Ausgangslage und Korrektur

Das bereitgestellte Projekt liess sich zunächst nicht testen: Die Tests riefen
`add`, `subtract`, `multiply` und `divide` auf, während die Klasse andere
Methodennamen enthielt. Addition und Multiplikation gaben nur den ersten
Operanden zurück. Die Methoden wurden passend zu den Tests benannt und die
Rechenoperationen vervollständigt. Division durch null löst weiterhin eine
`ArithmeticException` aus.

Im mitgelieferten `target`-Ordner lag ausserdem eine alte kompilierte Testklasse
`CaclulatorTest`, deren Quelldatei nicht mehr vorhanden war. Einmaliges
`mvn clean test` entfernte diese veralteten Build-Dateien. Danach funktionierte
auch `mvn test` ohne `clean`.

## Lokale Prüfung

Umgebung: macOS auf ARM64, Maven 3.9.9, Oracle JDK 23.0.2.
Die Kompilierung verwendet `release 17`; GitHub Actions verwendet Temurin JDK 17.

| Prüfung | Beobachtetes Ergebnis |
| --- | --- |
| `mvn test` nach der Korrektur | 6 Tests, 0 Failures, 0 Errors, 0 Skipped; BUILD SUCCESS |
| `mvn clean verify` | 6 erfolgreiche Tests; JAR und JaCoCo-Bericht erstellt |
| Absichtlich `8` statt `7` für `add(5, 2)` erwartet | 6 Tests, 1 Failure, 0 Errors, 0 Skipped; BUILD FAILURE |
| Nach Rücksetzen auf `7`: `mvn clean verify` | Wieder 6 erfolgreiche Tests; BUILD SUCCESS |

Der absichtliche Fehler wurde in `CalculatorTest.addPositiveIntegers` erkannt:

```text
org.opentest4j.AssertionFailedError: expected: <8.0> but was: <7.0>
```

JaCoCo meldete im erfolgreichen Build für `Calculator`:

- Zeilen: 7 von 7 ausgeführt (100 %).
- Zweige: 2 von 2 ausgeführt (100 %).
- Anweisungen: 28 von 28 ausgeführt (100 %).

Das sind Ausführungsabdeckungen des kleinen Rechners, kein Nachweis vollständiger
fachlicher Fehlerfreiheit. Der Bericht entsteht in `target/site/jacoco/`.

## GitHub Actions: grün → rot → grün

Repository: [MohamedRumy-HFTM/Software-Quality-Management_HFTM](https://github.com/MohamedRumy-HFTM/Software-Quality-Management_HFTM).
Die drei Änderungen wurden einzeln über den konfigurierten School-SSH-Zugang
auf `main` gepusht. Alle aufgeführten Läufe sind abgeschlossen.

| Stand | Commit | Bestätigter Actions-Lauf |
| --- | --- | --- |
| Funktionierender Rechner und CI eingerichtet | `85bba0a` | [Erfolgreich](https://github.com/MohamedRumy-HFTM/Software-Quality-Management_HFTM/actions/runs/34597981785) |
| Absichtlich falsche Erwartung: 8 statt 7 | `fd6afd3` | [Fehlgeschlagen](https://github.com/MohamedRumy-HFTM/Software-Quality-Management_HFTM/actions/runs/34598238080) |
| Erwartungswert wieder auf 7 korrigiert | `4c3740e` | [Erfolgreich](https://github.com/MohamedRumy-HFTM/Software-Quality-Management_HFTM/actions/runs/34598307357) |

Im GitHub-Protokoll des roten Laufs steht im Schritt **Build and test with Maven**:

```text
CalculatorTest.addPositiveIntegers:19 expected: <8.0> but was: <7.0>
Tests run: 6, Failures: 1, Errors: 0, Skipped: 0
BUILD FAILURE
Process completed with exit code 1.
```

Checkout und Java-Setup waren erfolgreich; der Fehler entstand bei der
Testausführung. Der Schritt **Upload test and coverage reports** blieb dank
`if: always()` erfolgreich und sicherte die Testberichte. Der Gesamtstatus blieb
**failure**. Nach der Korrektur meldete GitHub wieder **success**.

Das Artefakt **test-and-coverage-reports** des korrigierten Laufs wurde ebenfalls
heruntergeladen und geprüft: 6 Tests ohne Fehler sowie 7/7 abgedeckte Zeilen,
2/2 Zweige und 28/28 Anweisungen. Die Ergebnisse stimmen mit der lokalen Prüfung
überein.

## Erfüllte Teile der Aufgabe

- Maven-Projekt mit vorhandenen JUnit-Tests korrigiert und lokal geprüft.
- GitHub Actions für Pushes, Pull Requests und manuellen Start eingerichtet.
- Erfolgreichen, absichtlich fehlerhaften und wieder erfolgreichen Push geprüft.
- Vertiefung zur Test-Abdeckung mit JaCoCo und hochgeladenen Berichten umgesetzt.
- Persönlichen Kommentar als anpassbaren Entwurf in [ABGABE.md](ABGABE.md) erstellt.

Die optionale statische Code-Analyse mit SonarCloud oder Super-Linter wurde
nicht eingerichtet. Die Moodle-Abgabe selbst ist noch durchzuführen.
