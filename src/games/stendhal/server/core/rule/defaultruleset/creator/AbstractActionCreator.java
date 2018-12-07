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
package games.stendhal.server.core.rule.defaultruleset.creator;

import java.lang.reflect.Constructor;

import org.apache.log4j.Logger;

import games.stendhal.client.actions.DefaultAction;
import games.stendhal.client.actions.XMLSlashAction;

/**
 * Base item creator (using a constructor).
 */
abstract class AbstractActionCreator extends AbstractCreator<XMLSlashAction>{

	static final Logger logger = Logger.getLogger(AbstractItemCreator.class);

	/**
	 *
	 */
	final DefaultAction defaultAction;

	public AbstractActionCreator(DefaultAction defaultAction, final Constructor< ? > construct) {
		super(construct, "XMLSlashAction");
		this.defaultAction = defaultAction;
	}
}