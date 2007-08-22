@echo off

call jvm

call mvn package -U -cpu

call mvn clean

if errorlevel 1 pause


