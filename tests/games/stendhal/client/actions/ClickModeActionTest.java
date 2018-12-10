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
 * Test the Click class.
 *
 * @author Louis Thurston-Gibson
 */
public class ClickModeActionTest {

	/**
	 * Tests for null parameter given the volume action.
	 */
	@Test
	public void testSwitchingClick() {
		final MockClientUI clientUI = new MockClientUI();
		final ClickModeAction action = new ClickModeAction();
		
		assertTrue(action.execute(new String[]{null}, null));

		assertEquals(clientUI.getEventBuffer(), "Click mode is now set to double click.");
		
		assertTrue(action.execute(new String[]{null}, null));
		
		// EventBuffer accumulates and doesn't flush so responses are concatenated
		assertEquals(clientUI.getEventBuffer(), "Click mode is now set to double click.\nClick mode is now set to single click.");
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final ClickModeAction action = new ClickModeAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final ClickModeAction action = new ClickModeAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
