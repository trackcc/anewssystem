
rem jar
::call mvn archetype:create -DgroupId=anni -DartifactId=docs

rem war
::call mvn archetype:create -DgroupId=anni -DartifactId=docs -DarchetypeArtifactId=maven-archetype-webapp

rem doc
call mvn archetype:create -DgroupId=anni -DartifactId=docs -DarchetypeArtifactId=maven-archetype-site-simple

rem pom
::call mvn archetype:create -DgroupId=anni -DartifactId=modules -DarchetypeArtifactId=maven-archetype-quickstart

pause


