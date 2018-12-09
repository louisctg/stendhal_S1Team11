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
 * Send a message to the last player messaged.
 */
public class RemessageActionXML extends XMLSlashAction {
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
	public RemessageActionXML() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		final MessageAction messageCommand = (MessageAction) SlashActionRepository.get("msg");

		if ((messageCommand == null)
				|| (messageCommand.getLastPlayerTell() == null)
				|| remainder.isEmpty()) {
			return false;
		}

		final RPAction tell = new RPAction();

		tell.put("type", "tell");
		tell.put("target", messageCommand.getLastPlayerTell());
		tell.put("text", remainder);

		ClientSingletonRepository.getClientFramework().send(tell);

		return true;
	}
}
