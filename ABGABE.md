# Persönlicher Kommentar – Aufgabe 03

Formulierungsvorschlag: Die Aussagen zum eigenen Verständnis und zum künftigen
Einsatz vor der Abgabe an die persönliche Erfahrung anpassen.

## Wie weit konnte ich das Beispiel nachspielen und wie viel habe ich verstanden?

Mit Unterstützung konnte ich das Rechner-Beispiel als Maven-Projekt zum Laufen
bringen. Zunächst passten die Methodennamen nicht zu den vorbereiteten Tests;
ausserdem mussten Addition und Multiplikation korrigiert werden. Danach liefen
alle sechs JUnit-Tests mit `mvn test` erfolgreich durch, einschliesslich des Tests
für die Division durch null.

Auch GitHub Actions konnte erfolgreich eingerichtet und geprüft werden. Ein
erster Push führte zu einem grünen Build. Anschliessend wurde für `5 + 2`
absichtlich der falsche Erwartungswert `8` eingetragen und gepusht. GitHub
meldete genau diesen Test als fehlgeschlagen und markierte den Build rot.
Nach der Korrektur auf `7` war der nächste Lauf wieder grün. So wurde sichtbar,
dass ein Testfehler den automatischen Build tatsächlich scheitern lässt.

Für mich ist der grundlegende Ablauf nachvollziehbar: JUnit prüft das Verhalten
des Rechners, Maven führt die Build-Schritte und Tests aus, und GitHub Actions
startet diesen Ablauf nach einem Push automatisch auf einem Build-Server.
Die YAML-Datei legt den Auslöser, die Java-Umgebung und die einzelnen Schritte
fest. Dadurch erhalte ich auch ausserhalb meiner lokalen Entwicklungsumgebung
eine Rückmeldung zum Projektzustand.

Als Vertiefung wurde JaCoCo für die Test-Abdeckung ergänzt. Der Bericht zeigt,
welche Teile des Rechners beim Testen ausgeführt wurden. Eine hohe Abdeckung
bedeutet für mich jedoch nicht automatisch, dass alle möglichen Fehler
ausgeschlossen sind. Die Grundidee ist verständlich; komplexere Workflows und
statische Code-Analyse müsste ich noch weiter vertiefen.

## Wann werde ich Build- und Test-Automatisierung einsetzen?

Ich möchte Build- und Test-Automatisierung bei Projekten einsetzen, die ich
regelmässig erweitere oder gemeinsam mit anderen entwickle. Besonders bei
Änderungen an bestehender Logik hilft mir ein automatischer Testlauf, Fehler
früh zu erkennen. Bei Teamprojekten würde ich die Tests auch bei Pull Requests
ausführen lassen, damit Probleme vor dem Zusammenführen sichtbar werden.
Für kleine Übungsprojekte eignet sich ein einfacher Maven-Workflow ebenfalls,
um diese Arbeitsweise von Anfang an einzuüben.

Die ausgeführten Prüfungen und die drei GitHub-Läufe sind in
[NACHWEIS.md](NACHWEIS.md) verlinkt.
