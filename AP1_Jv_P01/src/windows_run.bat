call gradlew.bat clean shadowJar
copy /y libs\libjcurses.dll %TEMP%
call java -jar build\libs\RogueS21-1.0-SNAPSHOT-all.jar