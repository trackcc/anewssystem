@echo off
setlocal
set REPO=%USERPROFILE%\.m2\repository\
del *.bak

set classpath=.
set classpath=%classpath%;%REPO%\hsqldb\hsqldb\1.8.0.7\hsqldb-1.8.0.7.jar
set classpath=%classpath%;%REPO%\net\sourceforge\jtds\jtds\1.2\jtds-1.2.jar
set classpath=%classpath%;%REPO%\net\sourceforge\jexcelapi\jxl\2.6\jxl-2.6.jar
set classpath=%classpath%;%REPO%\mysql\mysql-connector-java\5.0.5\mysql-connector-java-5.0.5.jar

javac Export.java
java Export

pause

