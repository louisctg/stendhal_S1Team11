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

import games.stendhal.client.gui.j2DClient;

/**
 * Quit the client.
 */
public class QuitAction extends XMLSlashAction {

	/**
	 * Execute a chat command.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if was handled.
	 */
	public QuitAction() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		j2DClient client = j2DClient.get();
		if (client != null) {
			client.requestQuit();
		} else {
			System.exit(0);
		}

		return true;
	}
}
