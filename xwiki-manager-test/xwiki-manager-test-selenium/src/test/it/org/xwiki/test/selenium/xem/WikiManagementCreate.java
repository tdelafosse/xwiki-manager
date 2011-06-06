/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.test.selenium.xem;

import junit.framework.Test;

import org.xwiki.test.selenium.framework.AbstractXWikiTestCase;
import org.xwiki.test.selenium.framework.ColibriSkinExecutor;
import org.xwiki.test.selenium.framework.XWikiTestSuite;

/**
 * Minimum test that just try to access wiki.
 * 
 * @version $Id: 71d766aecd799f6a31456b93ad0e1698269892bf $
 */
public class WikiManagementCreate extends AbstractXWikiTestCase
{
    public static Test suite()
    {
        XWikiTestSuite suite = new XWikiTestSuite("Check some wiki management use cases");
        suite.addTestSuite(WikiManagementCreate.class, ColibriSkinExecutor.class);
        return suite;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.xpn.xwiki.it.selenium.framework.AbstractXWikiTestCase#setUp()
     */
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        loginAsAdmin();

        open("WikiManager", "CreateNewWiki");
    }

    /**
     * Validate empty wiki creation and all automated actions around it.
     */
    // "@Ignored" because of http://jira.xwiki.org/jira/browse/XEM-189
    /*
    public void ignoredTestCreateEmptyWiki()
    {
        setWikiNameFieldValue();
        setFieldValue("XWiki.XWikiServerClass_0_description", "A new empty wiki");
        submit();

        // Validate creation finished with no error
        assertTextPresent("Your wiki \"newemptywiki\" has been created.");

        open("WikiManager", "WebHome");

        // Validate the new wiki is listed
        assertTextPresent("newemptywiki");
        // Validate the autogenerated pretty name
        assertTextPresent("Newemptywiki");
        // Validate the correct domain is printed in the list
        assertTextPresent("newemptywiki.localdomain.com");
    }
    */

    /**
     * Validate the ajax based wiki name validation.
     */
    private void setWikiNameFieldValue()
    {
        // Validate that an existing wiki name is invalid as wiki name
        setFieldValue("wikiname", "xwiki");
        getSelenium().keyUp("wikiname", "\\40");
        waitForCondition("selenium.page().bodyText().indexOf('A wiki with this identifier already exists') != -1;");

        // Validate that "" is invalid as wiki name
        setFieldValue("wikiname", "");
        getSelenium().keyUp("wikiname", "\\40");
        waitForCondition("selenium.page().bodyText().indexOf('Identifier can\\'t be empty') != -1;");

        // Validate that a not existing wiki name is valid as wiki name
        setFieldValue("wikiname", "newemptywiki");
        getSelenium().keyUp("wikiname", "\\40");
        waitForCondition("selenium.page().bodyText().indexOf('You can use this name as new wiki identifier') != -1;");
    }
}
