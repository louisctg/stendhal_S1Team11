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

import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

/**
 * Send a message to the player who has last contacted us privately.
 */
public class AnswerAction extends XMLSlashAction {

	/**
	 * Execute a chat command.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if command was handled.
	 */
	public AnswerAction() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		if (!remainder.isEmpty()) {
			RPAction answer = new RPAction();

			answer.put("type", "answer");
			answer.put("text", remainder);

			ClientSingletonRepository.getClientFramework().send(answer);
			return true;
		}

		return false;
	}
}
