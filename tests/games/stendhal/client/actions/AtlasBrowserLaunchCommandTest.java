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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Constants;
import games.stendhal.client.MockClientUI;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.entity.User;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPObject.ID;



public class AtlasBrowserLaunchCommandTest {
	
	private static final String ZONE_NAME = "Testzone";
	private static final int USER_ID = 1001;
	
	private static RPObject createPlayer() {
		final RPObject rpo = new RPObject();

		rpo.put("type", "player");
		rpo.put("name", "player");
		rpo.setID(new ID(USER_ID, ZONE_NAME));

		final User pl = new User();
		pl.initialize(rpo);

		for (final String slotName : Constants.CARRYING_SLOTS) {
			rpo.addSlot(slotName);
		}
		
		return rpo;
	}
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
	}
	

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	
	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecuteWithUser() {
		final MockClientUI clientUI = new MockClientUI();
		final AtlasBrowserLaunchCommand action = (AtlasBrowserLaunchCommand) SlashActionRepository.get("atlas");
		
		createPlayer();
		
		assertTrue(action.execute(new String[]{null}, null));

		assertEquals(clientUI.getEventBuffer(), "Trying to open #https://stendhalgame.org/world/atlas.html?me=Testzone.0.0 in your browser.");
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final AtlasBrowserLaunchCommand action = (AtlasBrowserLaunchCommand) SlashActionRepository.get("atlas");
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final AtlasBrowserLaunchCommand action = (AtlasBrowserLaunchCommand) SlashActionRepository.get("atlas");
		assertThat(action.getMinimumParameters(), is(0));
	}
}
