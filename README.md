# Score Board

Live Football World Cup Scoreboard library that shows all the ongoing matches and their scores

## App description

Functionality:

* start a new match (initial score 0 - 0)
* update score
* finish match
* summary of matches in progress

For more details look into `doc/CodingExercisev1.11.pdf`

## Technical doc

Here `doc/TechnicalDoc.odt`. Content

* requirements
* architecture
* software deign

## Add Google code formatter (intellij)

* File -> Settings -> Editor -> Code Style -> Java
* Scheme -> Setting button -> Import Scheme ->  intellij idea code style xml
* select file from `settings/intellij-java-google-style.xml`
* click on apply and close
* to run code formatter on save: File -> Settings -> Tools -> Actions on Save -> select Reformat
  code

## SpotBugs

SpotBugs is an open source tool used to perform static analysis on Java code.

* `mvn spotbugs:check` make the build failed if it found any bugs
* `mvn spotbugs:gui` goal launches SpotBugs GUI to check analysis result.

## Run tests

* `mvn test`

## Build lib from source

* `mvn package"`
* lib is in `target/ScoreBoard-1.0.jar`