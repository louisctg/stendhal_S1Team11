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

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockClientUI;

/**
 * Test the DropAction class.
 *
 * @author Louis Thurston-Gibson
 */
public class HelpActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	  SlashActionRepository.register();
	}
	
	/**
	 * Tests for null parameter given the volume action.
	 */
	@Test
	public void testExecute() {
		final MockClientUI clientUI = new MockClientUI();
		final HelpAction action = (HelpAction) SlashActionRepository.get("help");
		
		assertTrue(action.execute(new String[]{null}, null));
		
		final String[] testLines = {
				"For a detailed reference, visit #https://stendhalgame.org/wiki/Stendhal_Manual",
				"Here are the most-used commands:",
				"* CHATTING:",
				"- /me <action> \tShow a message about what you are doing.",
				"- /tell <player> <message>",
				"\t\tSend a private message to #player.",
				"- /answer <message>",
				"\t\tSend a private message to the last player who sent a message to you.",
				"- // <message>\tSend a private message to the last player you sent a message to.",
				"- /storemessage <player> <message>",
				"\t\tStore a private message to deliver for an offline #player.",
				"- /who \tList all players currently online.",
				"- /where <player> \tShow the current location of #player.",
				"- /sentence <text> \tSet message on stendhalgame.org profile page and what players see when using #Look.",
				"* SUPPORT:",
				"- /support <message>",
				"\t\tAsk an administrator for help.",
				"- /faq \t\tOpen Stendhal FAQs wiki page in browser.",
				"* ITEM MANIPULATION:",
				"- /drop [quantity] <item>",
				"\t\tDrop a certain number of an #item.",
				"- /markscroll <text>",
				"\t\tMark your empty scroll and add a #text label.",
				"* BUDDIES AND ENEMIES:",
				"- /add <player> \tAdd #player to your buddy list.",
				"- /remove <player>",
				"\t\tRemove #player from your buddy list.",
				"- /ignore <player> [minutes|*|- [reason...]]",
				"\t\tAdd #player to your ignore list.",
				"- /ignore \tFind out who is on your ignore list.",
				"- /unignore <player>",
				"\t\tRemove #player from your ignore list.",
				"* STATUS:",
				"- /away <message>",
				"\t\tSet an away message.",
				"- /away \tRemove away status.",
				"- /grumpy <message>",
				"\t\tSet a message to ignore all non-buddies.",
				"- /grumpy \tRemove grumpy status.",
				"- /name <pet> <name>",
				"\t\tGive a name to your pet.",
				"- /profile [name] \tOpens a player profile page on stendhalgame.org.",
				"* PLAYER CONTROL:",
				"- /clickmode \tSwitches between single click mode and double click mode.",
				"- /walk \tToggles autowalk on/off.",
				"- /stopwalk \tTurns autowalk off.",
				"- /movecont \tToggle continuous movement (allows players to continue walking after map change or teleport without releasing direction key).",
				"* CLIENT SETTINGS:",
				"- /mute \tMute or unmute the sounds.",
				"- /volume \tLists or sets the volume for sound and music.",
				"* MISC:",
				"- /info \t\tFind out what the current server time is.",
				"- /clear \tClear chat log.",
				"- /help \tShow help information."
		};
		final String[] UILines = clientUI.getEventBuffer().toString().split("\\n");
		for(int i = 0; i < testLines.length; i++)
		  assertEquals(testLines[i], UILines[i]);
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final HelpAction action = (HelpAction) SlashActionRepository.get("help");
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final HelpAction action = (HelpAction) SlashActionRepository.get("help");
		assertThat(action.getMinimumParameters(), is(0));
	}

}
