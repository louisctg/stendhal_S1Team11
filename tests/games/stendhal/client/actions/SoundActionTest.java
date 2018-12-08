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
 * Test the DropAction class.
 *
 * @author Louis Thurston-Gibson
 */
public class SoundActionTest {

	/**
	 * Test for execute.
	 */
	@Test
	public void testSwitchingMute() {
		final MockClientUI clientUI = new MockClientUI();
		final SoundAction action = new SoundAction();
		
		assertTrue(action.execute(null, null));

		assertEquals(clientUI.getEventBuffer(), "This command is outdated. Please use \"/volume\" for changing the volume and \"/mute\" for muting all audio");
		
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SoundAction action = new SoundAction();
		assertThat(action.getMaximumParameters(), is(5));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SoundAction action = new SoundAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}