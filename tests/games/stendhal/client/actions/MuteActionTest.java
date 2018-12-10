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

import org.junit.Test;

import games.stendhal.client.MockClientUI;

/**
 * Test the MuteAction class.
 *
 * @author Louis Thurston-Gibson
 */
public class MuteActionTest {

	/**
	 * Tests for null parameter given the volume action.
	 */
	@Test
	public void testSwitchingMute() {
		final MockClientUI clientUI = new MockClientUI();
		final MuteAction action = new MuteAction();
		
		assertTrue(action.execute(new String[]{null}, null));

		boolean possibility1 = clientUI.getEventBuffer() == "Sounds are now off.";
		boolean possibility2 = clientUI.getEventBuffer() == "Sounds are now on.";
		
		assertTrue(possibility1 || possibility2);
		
		assertTrue(action.execute(new String[]{null}, null));
		
		// EventBuffer accumulates and doesn't flush so responses are concatenated
		if (possibility1)
		  assertEquals(clientUI.getEventBuffer(), "Sounds are now off.\nSounds are now on.");
		else
		  assertEquals(clientUI.getEventBuffer(), "Sounds are now on.\nSounds are now off.");
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final MuteAction action = new MuteAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final MuteAction action = new MuteAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
