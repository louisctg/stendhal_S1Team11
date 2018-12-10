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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockClientUI;
import games.stendhal.common.messages.SupportMessageTemplatesFactory;


/**
 * Test the GMHelpActionXML class.
 *
 * @author Louis Thurston-Gibson
 */
public class GMHelpActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	  SlashActionRepository.register();
	}
	
	/**
	 * Tests for null parameter given the volume action.
	 */
	@Test
	public void testNullExecute() {
		final MockClientUI clientUI = new MockClientUI();
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		
		assertTrue(action.execute(new String[]{null}, null));
		
		final String[] testLines = {
				"For a detailed reference, visit #https://stendhalgame.org/wiki/Stendhal:Administration",
				"Here are the most-used GM commands:",
				"* GENERAL:",
				"- /gmhelp [alter|script|support]",
				"\t\tFor more info about alter, script or the supportanswer shortcuts.",
				"- /adminnote <player> <note>",
				"\t\tLogs a note about #player.",
				"- /inspect <player>",
				"\t\tShow complete details of #player.",
				"- /script <scriptname>",
				"\t\tLoad (or reload) a script on the server. See #/gmhelp #script for details.",
				"* CHATTING:",
				"- /supportanswer <player> <message>",
				"\t\tReplies to a support question. Replace #message with $faq, $faqsocial, $ignore, $faqpvp, $wiki, $knownbug, $bugstracker, $rules, $notsupport or $spam shortcuts if desired.",
				"- /tellall <message>",
				"\t\tSend a private message to all logged-in players.",
				"* PLAYER CONTROL:",
				"- /teleportto <name>",
				"\t\tTeleport yourself near the specified player or NPC.",
				"- /teleclickmode \tMakes you teleport to the location you double click.",
				"- /ghostmode \tMakes yourself invisible and intangible.",
				"- /invisible \tToggles whether or not you are invisible to creatures.",
				"* ENTITY MANIPULATION:",
				"- /adminlevel <player> [<newlevel>]",
				"\t\tDisplay or set the adminlevel of the specified #player.",
				"- /jail <player> <minutes> <reason>",
				"\t\tImprisons #player for a given length of time.",
				"- /gag <player> <minutes> <reason>",
				"\t\tGags #player for a given length of time (player is unable to send messages to anyone).",
				"- /ban <character> <hours> <reason>",
				"\t\tBans the account of the character from logging onto the game server or website for the specified amount of hours (-1 till end of time).",
				"- /teleport <player> <zone> <x> <y>",
				"\t\tTeleport #player to the given location.",
				"- /alter <player> <attrib> <mode> <value>",
				"\t\tAlter stat #attrib of #player by the given amount; #mode can be ADD, SUB, SET or UNSET. See #/gmhelp #alter for details.",
				"- /altercreature <id> name;atk;def;hp;xp",
				"\t\tChange values of the creature. Use #- as a placeholder to keep default value. Useful in raids.",
				"- /alterquest <player> <questslot> <value>",
				"\t\tUpdate the #questslot for #player to be #value.",
				"- /summon <creature|item> [x] [y]",
				"\t\tSummon the specified item or creature at co-ordinates #x, #y in the current zone.",
				"- /summonat <player> <slot> [amount] <item>",
				"\t\tSummon the specified item into the specified slot of <player>; <amount> defaults to 1 if not specified.",
				"- /destroy <entity> \tDestroy an entity completely.",
				"* MISC:",
				"- /jailreport [<player>]",
				"\t\tList the jailed players and their sentences."
		};
		final String[] UILines = clientUI.getEventBuffer().toString().split("\\n");
		for(int i = 0; i < testLines.length; i++)
		  assertEquals(testLines[i], UILines[i]);
	}
	
	/**
	 * Tests for "alter" parameter given the volume action.
	 */
	@Test
	public void testAlterExecute() {
		final MockClientUI clientUI = new MockClientUI();
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		
		assertTrue(action.execute(new String[]{"alter"}, null));
		
		final String[] testLines = {
				"/alter <player> <attrib> <mode> <value>",
				"\t\tAlter stat <attrib> of <player> by the given amount; <mode> can be ADD, SUB, SET or UNSET.",
				"\t\t- Examples of <attrib>: atk, def, base_hp, hp, atk_xp, def_xp, xp, outfit",
				"\t\t- When modifying 'outfit', you should use SET mode and provide an 8-digit number; the first 2 digits are the 'hair' setting, then 'head', 'outfit', then 'body'",
				"\t\t  For example: #'/alter testplayer outfit set 12109901'",
				"\t\t  This will make <testplayer> look like danter"
		};
		final String[] UILines = clientUI.getEventBuffer().toString().split("\\n");
		for(int i = 0; i < testLines.length; i++)
		  assertEquals(testLines[i], UILines[i]);
	}
	
	/**
	 * Tests for "script" parameter given the volume action.
	 */
	@Test
	public void testScriptExecute() {
		final MockClientUI clientUI = new MockClientUI();
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		
		assertTrue(action.execute(new String[]{"script"}, null));
		
		final String[] testLines = {
				"usage: /script [-list|-load|-unload|-execute] [params]",
				"\t-list : shows available scripts. In this mode can be given one optional parameter for filenames filtering, with using well-known wildcards for filenames ('*' and '?', for example \"*.class\" for java-only scripts).",
				"\t-load : load script with first parameter's filename.",
				"\t-unload : unload script with first parameter's filename from server",
				"\t-execute : run selected script.",
				"",
				"All scripts are ran using: /script scriptname [params]. After running a script you can remove any traces of it with /script -unload scriptname, this would remove any summoned creatures, for example. It's good practise to do this after summoning creatures for a raid using scripts.",
				"#/script #AdminMaker.class : For test servers only, summons an adminmaker to aid testing.",
				"#/script #AdminSign.class #zone #x #y #text : Makes an AdminSign in zone at (x,y) with text. To put it next to you do /script AdminSign.class - - - text.",
				"#/script #AlterQuest.class #player #questname #state : Update the quest for a player to be in a certain state. Omit #state to remove the quest.",
				"#/script #DeepInspect.class #player : Deep inspects a player and all his/her items.",
				"#/script #DropPlayerItems.class #player #[amount] #item : Drop the specified amount of items from the player if they are equipped in the bag or body.",
				"#/script #EntitySearch.class #nonrespawn : Shows the locations of all creatures that don't respawn, for example creatures that were summoned by a GM, deathmatch creatures, etc.",
				"#/script #FixDM.class #player : sets a player's DeathMatch slot to victory status.",
				"#/script #ListNPCs.class : lists all npcs and their position.",
				"#/script #LogoutPlayer.class #player : kicks a player from the game.",
				"#/script #NPCShout.class #npc #text : NPC shouts text.",
				"#/script #NPCShoutZone.class #npc #zone #text : NPC shouts text to players in given zone. Use - in place of zone to make it your current zone.",
				"#/script #Plague.class #1 #creature : summon a plague of raid creatures around you.",
				"#/script #WhereWho.class : Lists where all the online players are",
				"#/script #Maria.class : Summons Maria, who sells food&drinks. Don't forget to -unload her after you're done.",
				"#/script #ServerReset.class : use only in a real emergency to shut down server. If possible please warn the players to logout and give them some time. It kills the server the hard way.",
				"#/script #ResetSlot.class #player #slot : Resets the named slot such as !kills or !quests. Useful for debugging."
		};
		final String[] UILines = clientUI.getEventBuffer().toString().split("\\n");
		for(int i = 0; i < testLines.length; i++)
		  assertEquals(testLines[i], UILines[i]);
	}
	
	/**
	 * Tests for "script" parameter given the volume action.
	 */
	@Test
	public void testFalseExecute() {
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		assertFalse(action.execute(new String[]{"alter","second_param"}, null));
		assertFalse(action.execute(new String[]{"wrong_param"}, null));
	}
	
	/**
	 * Tests for "support" parameter given the volume action.
	 */
	@Test
	public void testSuportExecute() {
		final MockClientUI clientUI = new MockClientUI();
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		
		assertTrue(action.execute(new String[]{"support"}, null));
		List<String> testLines = new LinkedList<String>();
		Map<String, String> templates = new SupportMessageTemplatesFactory().getTemplates();
		for (Entry<String, String> template : templates.entrySet()) {
			testLines.add(template.getKey() + " - " + template.getValue());
		}
		
		final String[] UILines = clientUI.getEventBuffer().toString().split("\\n");
		for(int i = 0; i < testLines.size(); i++)
		  assertEquals(testLines.get(i), UILines[i]);
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GMHelpAction action = (GMHelpAction) SlashActionRepository.get("gmhelp");
		assertThat(action.getMinimumParameters(), is(0));
	}

}
