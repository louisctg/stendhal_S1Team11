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

import games.stendhal.client.actions.DefaultAction;
import games.stendhal.client.actions.XMLSlashAction;

/**
 * Create an item class via the full arguments (<em>name, clazz,
 * subclazz, attributes</em>)
 * constructor.
 */
public class FullActionCreator extends AbstractActionCreator {

	public FullActionCreator(DefaultAction defaultAction, final Constructor< ? > construct) {
		super(defaultAction, construct);
	}

	@Override
	protected XMLSlashAction createObject() throws IllegalAccessException,
			InstantiationException, InvocationTargetException {
		return (XMLSlashAction) construct.newInstance(new Object[] {/*this.defaultAction.getActionName(), this.defaultAction.getType(), this.defaultAction.getActionList(),
				this.defaultAction.getMaximumParameters(), this.defaultAction.getMinimumParameters()*/});
	}
}