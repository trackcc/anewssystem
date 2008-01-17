@echo off

call jvm

call mvn package site -U -cpu

call mvn clean

del checks.paf
del checks.ppf

if errorlevel 1 pause


