package org.codehaus.mojo.cobertura.stubs;

/*
 * Copyright 2001-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import org.apache.maven.artifact.Artifact;
import org.apache.maven.model.Build;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Edwin Punzalan
 */
public class CheckMavenProjectStub
    extends MavenProjectStub
{
    private Build build;

    public CheckMavenProjectStub()
        throws IOException
    {
        File targetFile = new File( PlexusTestCase.getBasedir() + "/target/test-harness/check/cobertura.ser" );

        File serFile = new File( PlexusTestCase.getBasedir() + "/src/test/sources/check.ser" );

        FileUtils.copyFile( serFile, targetFile );
   }

    public MavenProject getExecutionProject()
    {
        return this;
    }

    public Set getDependencyArtifacts()
    {
        return Collections.EMPTY_SET;
    }

    public Build getBuild()
    {
        if ( build == null )
        {
            build = new Build();

            build.setDirectory( PlexusTestCase.getBasedir() + "/target/test-harness/check" );
            build.setOutputDirectory( PlexusTestCase.getBasedir() + "/target/test-harness/check/classes" );
        }

        return build;
    }

    public Artifact getArtifact()
    {
        return new ArtifactStub();
    }

    public List getCompileSourceRoots()
    {
        return Collections.singletonList( PlexusTestCase.getBasedir() + "/src/test/sources" );
    }
}
