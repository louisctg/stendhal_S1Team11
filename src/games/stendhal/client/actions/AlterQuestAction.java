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
 * alters the server side state of a quest
 */
public class AlterQuestAction extends XMLSlashAction {

	/**
	 * Alters an entity's attributes.
	 *
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 *
	 * @return <code>true</code> if was handled.
	 */
	public AlterQuestAction() {
		super();
	}
	@Override
	public boolean execute(final String[] params, final String remainder) {
		if ((params == null) || (params.length < getMinimumParameters())) {
			return false;
		}
		final RPAction action = new RPAction();
		action.put("type", "alterquest");
		action.put("target", params[0]);
		action.put("name", params[1]);
		if ((params.length > 2) && (params[2] != null)) {
			action.put("state", params[2]);
		}
		ClientSingletonRepository.getClientFramework().send(action);
		return true;
	}
}
