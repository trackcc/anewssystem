
setlocal
set REPO=%USERPROFILE%\.m2\repository\
del *.bak

set classpath=.
set classpath=%classpath%;%REPO%\hsqldb\hsqldb\1.8.0.7\hsqldb-1.8.0.7.jar

javac EntityRelationship.java

java EntityRelationship

pause




