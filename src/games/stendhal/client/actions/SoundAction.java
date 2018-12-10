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
import games.stendhal.client.gui.chatlog.StandardEventLine;

/**
 * displays a help message about the new commands /mute and /volumne
 */
public class SoundAction extends XMLSlashAction {

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
	public SoundAction() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		j2DClient.get().addEventLine(new StandardEventLine("This command is outdated. Please use \"/volume\" for changing the volume and \"/mute\" for muting all audio"));
		return true;
	}
}
