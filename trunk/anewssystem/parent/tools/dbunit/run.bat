@echo off
setlocal
set REPO=%USERPROFILE%\.m2\repository\
del *.bak
del *.class

set classpath=.
set classpath=%classpath%;%REPO%\hsqldb\hsqldb\1.8.0.7\hsqldb-1.8.0.7.jar
set classpath=%classpath%;%REPO%\net\sourceforge\jtds\jtds\1.2\jtds-1.2.jar
set classpath=%classpath%;%REPO%\org\dbunit\dbunit\2.2\dbunit-2.2.jar
set classpath=%classpath%;%REPO%\poi\poi\2.5.1-final-20040804\poi-2.5.1-final-20040804.jar
::set classpath=%classpath%;%REPO%\org\apache\poi\poi\3.0.1-FINAL\poi-3.0.1-FINAL.jar
set classpath=%classpath%;%REPO%\mysql\mysql-connector-java\5.0.5\mysql-connector-java-5.0.5.jar
javac Test.java
java Test


endlocal
pause
