package org.codehaus.mojo.javascript;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.maven.reporting.MavenReportException;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Generate JsDoc report
 *
 * @goal jsdoc
 * @author <a href="mailto:nicolas.deloof@gmail.com">nicolas De Loof</a>
 */
public class JsDocReport
    extends AbstractJavascriptReport
{

    /**
     * Location of the source files.
     *
     * @parameter default-value="${basedir}/src/main/javascript"
     */
    protected File sourceDirectory;

    /**
     * Include symbols tagged as private ?
     *
     * @parameter
     */
    private boolean includePrivate;

    /**
     * Include all functions, even undocumented ones ?
     *
     * @parameter
     */
    private boolean includeUndocumented;

    /**
     * Include all functions, even undocumented, underscored ones ?
     *
     * @parameter
     */
    private boolean includeUnderscore;

    /**
     * JsDoc template to be used
     *
     * @parameter default-value="utf8"
     */
    private String template;

    /**
     * JsDoc custom template to be used (overrides template)
     *
     * @parameter
     */
    private File customTemplate;

    /**
     * {@inheritDoc}
     *
     * @see org.apache.maven.reporting.AbstractMavenReport#executeReport(java.util.Locale)
     * @see http://code.google.com/p/jsdoc-toolkit/wiki/CmdlineOptions
     */
    protected void executeReport( Locale locale )
        throws MavenReportException
    {
        File workDirectory = getWorkDirectory();
        unpackJavascriptDependency( "org.jsdoctoolkit:jsdoc", workDirectory );

        File script = new File( workDirectory.getAbsolutePath(), "/app/run.js" );
        List args = new ArrayList();
        if ( customTemplate == null )
        {
            args.add( "-t=" + workDirectory.getAbsolutePath() + "/templates/" + template );
        }
        else
        {
            args.add( "-t=" + customTemplate.getAbsolutePath() );
        }
        args.add( "-d=" + getOutputDirectory() );
        if ( includeUndocumented )
        {
            args.add( "-a" );
        }
        if ( includeUnderscore )
        {
            args.add( "-A" );
        }
        if ( includePrivate )
        {
            args.add( "-p" );
        }
        args.add( "-o=" + workDirectory.getAbsolutePath() + "/jsdoc.log" );
        args.add( sourceDirectory.getAbsolutePath() );

        System.setProperty( "jsdoc.dir", workDirectory.getAbsolutePath() );
        evalScript( script, (String[]) args.toArray( new String[0] ), null );
    }

    /**
     * {@inheritDoc}
     *
     * @see org.apache.maven.reporting.AbstractMavenReport#canGenerateReport()
     */
    public boolean canGenerateReport()
    {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.codehaus.mojo.javascript.AbstractJavascriptReport#getName()
     */
    protected String getName()
    {
        return "jsdoc";
    }
}
