rem @echo off
setlocal enabledelayedexpansion
rmdir /s /q out 2>nul
mkdir out
set PKG=src/com/example/dungeon
javac -encoding UTF-8 -d out %PKG%/core/*.java %PKG%/model/*.java %PKG%/*.java
echo Build OK. Run run.bat
