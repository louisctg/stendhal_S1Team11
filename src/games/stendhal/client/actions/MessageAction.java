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
 * Send a message to a player.
 */
public class MessageAction extends XMLSlashAction {

	private String lastPlayerTell;

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
	public MessageAction() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		lastPlayerTell = params[0];

		if (!remainder.isEmpty()) {
			RPAction tell = new RPAction();

			tell.put("type", "tell");
			tell.put("target", lastPlayerTell);
			tell.put("text", remainder);

			ClientSingletonRepository.getClientFramework().send(tell);
			return true;
		}

		return false;
	}
	/**
	 * Gets the last player we have sent something using /tell.
	 *
	 * @return player name
	 */
	String getLastPlayerTell() {
		return lastPlayerTell;
	}
}
