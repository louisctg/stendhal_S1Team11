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
 * Writes the sentence that appears on Website.
 */
public class SentenceAction extends XMLSlashAction {

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
	public SentenceAction() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		if (params == null) {
			return false;
		}
		final RPAction add = new RPAction();

		add.put("type", "sentence");
		add.put("value", remainder);

		ClientSingletonRepository.getClientFramework().send(add);

		return true;
	}
}
