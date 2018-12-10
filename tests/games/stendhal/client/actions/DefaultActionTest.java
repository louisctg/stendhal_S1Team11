/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.client.StendhalClient;

public class DefaultActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Test for toXML() method of DefaultAction.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	@Test
	public void testExecute() throws URISyntaxException, SAXException, IOException {
		// This will need to be changed if first action changes in action/actions.xml
		final ActionGroupsXMLLoader loader = new ActionGroupsXMLLoader(new URI("/data/conf/actions.xml"));
		List<DefaultAction> loadedDefaultActions = loader.load();
		String addBuddyAction = "  <action name=\"AcceptChallengeAction\">\n" + 
								"    <implementation class-name=\"games.stendhal.client.actions.AcceptChallengeAction\"/>\n" + 
								"    <MaximumParameters>1</MaximumParameters>\n" + 
								"    <MinimumParameters>1</MinimumParameters>\n" + 
								"    <type>challenge</type>\n" + 
								"    	<putAction name=\"action\"/>\n" + 
								"    	<putAction name=\"target\"/>\n" + 
								"  </action>\n";
		assertEquals(addBuddyAction,loadedDefaultActions.get(0).toXML());
	}
}
