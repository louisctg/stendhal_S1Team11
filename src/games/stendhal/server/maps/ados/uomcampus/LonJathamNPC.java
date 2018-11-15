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
package games.stendhal.server.maps.ados.uomcampus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;


/**
 * The interior for the Ados UoM campus was copied from that of the library.
 * Credits for the library interior are as follows:
 * Ados Library (Inside / Level 0).
 *
 * @author hendrik
 */
public class LonJathamNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildCampus(zone);
	}

	private void buildCampus(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Lon Jatham") {

			@Override
			protected void createPath() {
				final List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(10, 9));
				nodes.add(new Node(10, 27));
				nodes.add(new Node(20, 27));
				nodes.add(new Node(20, 10));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("GOOD MORNING! Would you like #help deciding if our university is a good fit for you? Alternatively, I can give #offer you a tip to be a better programmer.");
				addJob("I am the one and only Lon Jatham.");
				addHelp("We offer course units related to Computer Architecture, Information Systems, Mobile Computing and Networks, Hardware, Algorithms, Software Engineering and many more!");
				addOffer("Make sure to give your variables meaningful, appropriate names. If you do it, you'll make it far as a programmer.");

				
				addGoodbye();
			}
		};

		npc.setEntityClass("lonjathamnpc");
		npc.setPosition(10, 9);
		npc.initHP(100);
		npc.setDescription("You see a lecturer with matching glasses and shirt.");
		zone.add(npc);
	}
}
