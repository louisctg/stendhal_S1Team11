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
package games.stendhal.server.maps.semos.townhall;

import games.stendhal.common.Direction;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SayTextWithPlayerNameAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.QuestActiveCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.parser.Sentence;
import games.stendhal.server.entity.player.Player;

import java.util.Map;

public class BoyNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildSemosTownhallArea(zone, attributes);
	}

	private void buildSemosTownhallArea(final StendhalRPZone zone, final Map<String, String> attributes) {
		final SpeakerNPC npc = new SpeakerNPC("Tad") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				add(ConversationStates.IDLE,
						ConversationPhrases.GREETING_MESSAGES,
						new AndCondition(
								new QuestNotStartedCondition("introduce_players"),
								new ChatCondition() {
									public boolean fire(final Player player, final Sentence sentence, final Entity entity) {
										return !player.isGhost();
									}
								}),
				        ConversationStates.ATTENDING,
				        null,
				        new SayTextWithPlayerNameAction("Ssshh! Come here, [name]! I have a #task for you."));
				
				add(ConversationStates.IDLE,
						ConversationPhrases.GREETING_MESSAGES,
						new QuestActiveCondition("introduce_players"),
				        ConversationStates.ATTENDING,
				        "*sniff* *sniff* I still feel ill, please hurry with that #favour for me.",
				        null);
				
				add(ConversationStates.IDLE,
						ConversationPhrases.GREETING_MESSAGES,
						new QuestCompletedCondition("introduce_players"),
				        ConversationStates.ATTENDING,
				        null,
				        new SayTextWithPlayerNameAction("Hi again, [name]! Thanks again, I'm feeling much better now."));
				
				addGoodbye();
			}

			/* (non-Javadoc)
			 * @see games.stendhal.server.entity.npc.SpeakerNPC#onGoodbye(games.stendhal.server.entity.player.Player)
			 */
			@Override
			protected void onGoodbye(Player player) {
				setDirection(Direction.RIGHT);
			}
			
			
			
		};

		npc.addInitChatMessage(null, new ChatAction() {
			public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
				if (!player.hasQuest("TadFirstChat")) {
					player.setQuest("TadFirstChat", "done");
					((SpeakerNPC) raiser.getEntity()).listenTo(player, "hi");
				}
			}
		});

		npc.setEntityClass("childnpc");
		npc.setDescription("The young boy you see is Tad. He looks ill and his face is pale.");
		npc.setPosition(13, 38);
		npc.setDirection(Direction.RIGHT);
		npc.initHP(100);
		zone.add(npc);
	}
}
