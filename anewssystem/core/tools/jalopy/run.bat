@echo off
setlocal
del *.bak
set REPO=%USERPROFILE%\.m2\repository\

set classpath=.
set classpath=%classpath%;%REPO%\jalopy\jalopy\1.5rc3\jalopy-1.5rc3.jar
javac JalopyUtil.java -encoding UTF-8
java JalopyUtil


endlocal
pause
