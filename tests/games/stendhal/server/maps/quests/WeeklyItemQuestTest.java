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
package games.stendhal.server.maps.quests;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Rand;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.kirdneh.museum.CuratorNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class WeeklyItemQuestTest {


	private static String questSlot = "weekly_item";

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		MockStendlRPWorld.get();

		final StendhalRPZone zone = new StendhalRPZone("admin_test");

		new CuratorNPC().configureZone(zone, null);

		final AbstractQuest quest = new WeeklyItemQuest();
		quest.addToWorld();

	}
	@Before
	public void setUp() {
		player = PlayerTestHelper.createPlayer("player");
	}

	/**
	 * Tests for quest.
	 */
	@Test
	public void testQuest() {

		npc = SingletonRepository.getNPCList().get("Hazel");
		en = npc.getEngine();

		en.step(player, "hi");
		assertEquals("Welcome to Kirdneh Museum.", getReply(npc));
		en.step(player, "task");
		assertTrue(getReply(npc).startsWith("I want Kirdneh's museum to be the greatest in the land! Please fetch "));
		en.step(player, "complete");
		assertTrue(getReply(npc).startsWith("You don't seem to have "));
		en.step(player, "bye");
		assertEquals("Good bye, it was pleasant talking with you.", getReply(npc));

		player.setQuest(questSlot, "obsidian;100");
		Item item = ItemTestHelper.createItem("obsidian");
		player.getSlot("bag").add(item);
		final int xp = player.getXP();

		en.step(player, "hi");
		assertEquals("Welcome to Kirdneh Museum.", getReply(npc));
		en.step(player, "complete");
		assertFalse(player.isEquipped("obsidian"));
		assertTrue(player.isEquipped("money"));
		assertThat(player.getXP(), greaterThan(xp));
		assertTrue(player.isQuestCompleted(questSlot));
		assertTrue(getReply(npc).startsWith("Wonderful! Here is "));
		en.step(player, "bye");
		assertEquals("Good bye, it was pleasant talking with you.", getReply(npc));

		en.step(player, "hi");
		assertEquals("Welcome to Kirdneh Museum.", getReply(npc));
		en.step(player, "task");
		assertThat(getReply(npc), is(oneOf("The museum can only afford to send you to fetch an item once a week. Please check back in 7 days.",
				                            "The museum can only afford to send you to fetch an item once a week. Please check back in 1 week.")));
		en.step(player, "bye");
		assertEquals("Good bye, it was pleasant talking with you.", getReply(npc));

		// -----------------------------------------------
		player.setQuest(questSlot, "done;0");

		en.step(player, "hi");
		assertEquals("Welcome to Kirdneh Museum.", getReply(npc));
		en.step(player, "task");
		assertTrue(getReply(npc).startsWith("I want Kirdneh's museum to be the greatest in the land! Please fetch "));
		en.step(player, "bye");
		assertEquals("Good bye, it was pleasant talking with you.", getReply(npc));

		// -----------------------------------------------

		player.setQuest(questSlot, "dark dagger;0");
		en.step(player, "hi");
		assertEquals("Welcome to Kirdneh Museum.", getReply(npc));
		en.step(player, "task");
		assertEquals("You're already on a quest to bring the museum a dark dagger. Please say #complete if you have it with you. But, perhaps that is now too rare an item. I can give you #another task, or you can return with what I first asked you.", getReply(npc));
		en.step(player, "another");
		assertTrue(getReply(npc).startsWith("I want Kirdneh's museum to be the greatest in the land! Please fetch "));
		en.step(player, "bye");
		assertEquals("Good bye, it was pleasant talking with you.", getReply(npc));

	}
	
	/**
	 * Tests to check that quest not giving the same item twice in a row. 
	 * This includes not getting the same item you did in the previous 
	 * quest or the same item you have not been able to find for 6 weeks.
	 */
	@Test
	public void QuestItemNotRepeatedTest() {
		setUp();
		npc = SingletonRepository.getNPCList().get("Hazel");
		en = npc.getEngine();

        // Set testing variable to true so that the item produced by the quest is always the same.
		Rand.isTest(true);
		
		String currentItem = null;
		String lastItem = null;
		
		// Test case for completed quests.
		// We set the value of the testValue class variable in Rand to 0 so we always
		// pick the first item in the map of objects. Therefore, we will see if the
		// code is indeed removing the last item requested from the map before choosing.
		Rand.setTestValue(0);
			
		lastItem = player.lastItemRequest;
		en.step(player, "hi");
		en.step(player, "task");
		en.step(player, "bye");
		currentItem = player.getRequiredItemName(questSlot, 0);    
		assertFalse(currentItem.equals(lastItem)); // previousItem starts at null so this should always pass.
		lastItem = player.lastItemRequest;

		Item item = ItemTestHelper.createItem(currentItem, player.getRequiredItemQuantity(questSlot, 0));
		player.getSlot("bag").add(item);
			
		en.step(player, "hi");
		en.step(player, "complete");
		en.step(player, "bye");
		
		player.setQuest(questSlot, "done;0");
		en.step(player, "hi");
		en.step(player,  "task"); 
		currentItem = player.getRequiredItemName(questSlot, 0);    
		assertFalse(currentItem.equals(lastItem)); // previousItem is now set to a proper value.
		en.step(player, "bye");
		lastItem = player.lastItemRequest;
			
		
		// Test case for 'another' situation.
		player.setQuest(questSlot, currentItem + ";0");
		en.step(player, "hi");
		en.step(player, "another");
		currentItem = player.getRequiredItemName(questSlot, 0);    
		assertFalse(currentItem.equals(lastItem));
		en.step(player, "bye");
	}
}
