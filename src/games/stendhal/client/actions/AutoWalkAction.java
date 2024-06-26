/***************************************************************************
 *                   (C) Copyright 2003-2015 - Arianne                     *
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

import static games.stendhal.common.constants.Actions.TARGET;
import static games.stendhal.common.constants.Actions.TYPE;
import static games.stendhal.common.constants.Actions.WALK;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.StringHelper;
import marauroa.common.game.RPAction;

/**
 * Causes the entity to begin walking in the direction facing.
 *
 * @author
 * 		AntumDeluge
 */
public class AutoWalkAction extends XMLSlashAction {

	/**
	 * Execute a chat command.
	 *
	 * @param params
	 * 		The formal parameters.
	 * @param remainder
	 * 		Line content after parameters.
	 * @return
	 * 		<code>true</code> if command was handled.
	 */
	public AutoWalkAction() {
		super();
	}
	@Override
	public boolean execute(String[] params, String remainder) {
		final RPAction walk = new RPAction();

		walk.put(TYPE, WALK);
		walk.put(TARGET, StringHelper.unquote(remainder));

		ClientSingletonRepository.getClientFramework().send(walk);

		return true;
	}
}
