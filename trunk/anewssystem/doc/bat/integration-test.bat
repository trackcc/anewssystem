@echo off
cd ..\..

rem call mvn -Dmaven.test.skip=false integration-test
call mvn -Dselenium=true integration-test

pause
