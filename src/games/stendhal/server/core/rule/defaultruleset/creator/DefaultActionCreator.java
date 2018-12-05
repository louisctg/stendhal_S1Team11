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
import java.lang.reflect.InvocationTargetException;

import games.stendhal.server.core.rule.defaultruleset.DefaultAction;
import games.stendhal.client.actions.*;

/**
 * Create an item class via the default constructor.
 */
public class DefaultActionCreator extends AbstractActionCreator {

	public DefaultActionCreator(DefaultAction defaultAction, final Constructor< ? > construct) {
		super(defaultAction, construct);
	}

	@Override
	protected XMLSlashAction createObject() throws IllegalAccessException,
			InstantiationException, InvocationTargetException {
		return (XMLSlashAction) construct.newInstance(new Object[] {});
	}
}