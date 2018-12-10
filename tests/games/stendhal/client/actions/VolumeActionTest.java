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

import games.stendhal.client.MockClientUI;

/**
 * Test the DropAction class.
 *
 * @author Louis Thurston-Gibson
 */
public class VolumeActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
	}
	
	/**
	 * Reset all values to original.
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		new MockClientUI();
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		
		action.execute(new String[]{"master","100"}, null);
		action.execute(new String[]{"gui","100"}, null);
		action.execute(new String[]{"sfx","100"}, null);
		action.execute(new String[]{"creature","95"}, null);
		action.execute(new String[]{"ambient","80"}, null);
		action.execute(new String[]{"music","60"}, null);
	}
	
	/**
	 * Tests for null parameter given the volume action.
	 */
	@Test
	public void testNullParamZero() {
		final MockClientUI clientUI = new MockClientUI();
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		
		assertTrue(action.execute(new String[]{null}, null));

		String[] lines = clientUI.getEventBuffer().toString().split("\\n");
		
		assertEquals("Please use /volume <name> <value> to adjust the volume.", lines[0]);
		assertEquals("<name> is an item from the following list. \"master\" refers to the global volume setting.", lines[1]);
		assertEquals("master -> 100", lines[3]);
		assertEquals("gui -> 100", lines[4]);
		assertEquals("sfx -> 100", lines[5]);
		assertEquals("creature -> 95", lines[6]);
		assertEquals("ambient -> 80", lines[7]);
		assertEquals("music -> 60", lines[8]);
	}
	
	/**
	 * Test for not providing number.
	 */
	@Test
	public void testNoNumber() {
		final MockClientUI clientUI = new MockClientUI();
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		
		assertTrue(action.execute(new String[]{"master",null}, null));
		String[] lines = clientUI.getEventBuffer().toString().split("\\n");
		
		assertEquals("Please use /volume for help.", lines[0]);
	}
	
	/**
	 * Tests for changing all groups given the volume action.
	 */
	@Test
	public void testChangeMultipleParamZero() {
		final MockClientUI clientUI = new MockClientUI();
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		
		assertTrue(action.execute(new String[]{"master","50"}, null));
		assertTrue(action.execute(new String[]{"gui","73"}, null));
		assertTrue(action.execute(new String[]{"sfx","21"}, null));
		assertTrue(action.execute(new String[]{"creature","45"}, null));
		assertTrue(action.execute(new String[]{"ambient","91"}, null));
		assertTrue(action.execute(new String[]{"music","82"}, null));

		
		assertTrue(action.execute(new String[]{null}, null));
		String[] lines = clientUI.getEventBuffer().toString().split("\\n");
		
		assertEquals("Please use /volume <name> <value> to adjust the volume.", lines[0]);
		assertEquals("<name> is an item from the following list. \"master\" refers to the global volume setting.", lines[1]);
		assertEquals("master -> 50", lines[3]);
		assertEquals("gui -> 73", lines[4]);
		assertEquals("sfx -> 21", lines[5]);
		assertEquals("creature -> 45", lines[6]);
		assertEquals("ambient -> 91", lines[7]);
		assertEquals("music -> 82", lines[8]);
	}
	
	/**
	 * Tests for changing all groups given the volume action.
	 */
	@Test
	public void testNotANumber() {
		final MockClientUI clientUI = new MockClientUI();
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		
		assertTrue(action.execute(new String[]{"master","5..0"}, null));
		String[] lines = clientUI.getEventBuffer().toString().split("\\n");
		
		assertEquals("5..0 is not a valid number", lines[0]);
	}
	
	/**
	 * Tests for changing all groups given the volume action.
	 */
	@Test
	public void testWrongGroup() {
		final MockClientUI clientUI = new MockClientUI();
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		
		assertTrue(action.execute(new String[]{"wrong_groupname","50"}, null));
		String[] lines = clientUI.getEventBuffer().toString().split("\\n");
		
		assertEquals("No sound group \"wrong_groupname\" does exist", lines[0]);
		assertEquals("Please type \"/volume show\" for a valid list of groups", lines[1]);
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final VolumeAction action = (VolumeAction) SlashActionRepository.get("volume");
		assertThat(action.getMinimumParameters(), is(0));
	}

}
